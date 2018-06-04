package com.cn.webApp.cache.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cn.webApp.cache.Cache;
import com.cn.webApp.common.SerializeUtil;
import com.cn.webApp.propertiesConfig.DBProperties;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

public class Redis implements Cache {
	private static Redis redis = new Redis();

	private static ShardedJedisPool pool;

	Logger log = Logger.getLogger(this.getClass());

	private static List<RedisServerInfo> getServers() {
		String ser = "59.110.240.114:6379";
		//String ser=DBProperties.getProperty("RedisServerIP");
		String[] server = ser.split(SERVER_URL_SPLITOR);
		List<RedisServerInfo> servers = new ArrayList<RedisServerInfo>();
		for (int i = 0; i < server.length; i++) {
			String[] url = server[i].split("\\:");
			String ip = url[0];
			String port = url.length > 1 ? url[1] : null;
			String passsword = url.length > 2 ? url[2] : null;
			if (ip != null && port != null) {
				ip = ip.trim();
				port = port.trim();
				if (ip.length() > 0 && port.length() > 0) {
					RedisServerInfo info = new RedisServerInfo();
					info.ip = ip;
					info.port = Integer.parseInt(port);
					info.password = passsword;
					servers.add(info);
				}
			}
		}
		return servers;
	}

	static {
		List<RedisServerInfo> servers = getServers();
		// 生成多机连接信息列表
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (int i = 0; i < servers.size(); i++) {
			RedisServerInfo info = servers.get(i);
			JedisShardInfo jsi = new JedisShardInfo(info.ip, info.port);
			if (info.password != null) {
				jsi.setPassword(info.password);
			}
			shards.add(jsi);
		}
		// 生成连接池配置信息
		JedisPoolConfig config = new JedisPoolConfig();
		config.setTestOnBorrow(true);
		config.setMaxTotal(500);
		config.setMaxIdle(200);
		config.setMaxWaitMillis(1 * 1000);
		// 在应用初始化的时候生成连接池
		pool = new ShardedJedisPool(config, shards);
	}

	protected Redis() {
	}

	public static Redis getInstance() {
		return redis;
	}

	public ShardedJedis getClient() {
		return pool.getResource();
	}

	@Override
	public void set(String key, Object value) {
		ShardedJedis client = null;
		try {
			client = getClient();
			byte[] bkey = SerializeUtil.serialize(key);
			byte[] bvalue = SerializeUtil.serialize(value);
			client.set(bkey, bvalue);
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
	}

	@Override
	public void set(String key, Object value, Date expiry) {
		ShardedJedis client = null;
		try {
			client = getClient();
			byte[] bkey = SerializeUtil.serialize(key);
			byte[] bvalue = SerializeUtil.serialize(value);
			client.set(bkey, bvalue);
			client.expireAt(bkey, expiry.getTime() / 1000L);
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
	}

	@Override
	public void set(String key, Object value, int seconds) {
		ShardedJedis client = null;
		try {
			client = getClient();
			byte[] bkey = SerializeUtil.serialize(key);
			byte[] bvalue = SerializeUtil.serialize(value);
			client.set(bkey, bvalue);
			client.expire(bkey, seconds);
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
	}

	@Override
	public void delete(String key) {
		ShardedJedis client = null;
		try {
			client = getClient();
			byte[] bkey = SerializeUtil.serialize(key);
			client.del(bkey);
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
	}

	@Override
	public Object get(String key) {
		Object value = null;
		ShardedJedis client = null;
		try {
			client = getClient();
			byte[] bkey = SerializeUtil.serialize(key);
			byte[] bvalue = client.get(bkey);
			if (bvalue != null) {
				value = SerializeUtil.unserialize(bvalue);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
		return value;
	}

	@Override
	public Object get(String key, long wait) {
		Object value = null;
		ShardedJedis client = null;
		try {
			client = getClient();
			long locktime = locktime(key);
			if (locktime > 0) {
				if (wait >= 0) {// 超时退出等待解锁
					long start = System.currentTimeMillis();
					while (System.currentTimeMillis() - start < wait) {
						if (locktime > 0) {
							Thread.sleep(Cache.THREAD_SLEEP);
							locktime = locktime(key);
						} else {
							byte[] bkey = SerializeUtil.serialize(key);
							byte[] bvalue = client.get(bkey);
							if (bvalue != null)
								value = SerializeUtil.unserialize(bvalue);
							break;
						}
					}
				} else {// 一直等待解锁
					while (true) {
						if (locktime > 0) {
							Thread.sleep(Cache.THREAD_SLEEP);
							locktime = locktime(key);
						} else {
							byte[] bkey = SerializeUtil.serialize(key);
							byte[] bvalue = client.get(bkey);
							if (bvalue != null)
								value = SerializeUtil.unserialize(bvalue);
							break;
						}
					}
				}
			} else {
				byte[] bkey = SerializeUtil.serialize(key);
				byte[] bvalue = client.get(bkey);
				if (bvalue != null)
					value = SerializeUtil.unserialize(bvalue);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
		return value;
	}

	@Override
	public void addLock(String key) {
		ShardedJedis client = null;
		try {
			client = getClient();
			String lockey = getLockey(key);
			byte[] bkey = SerializeUtil.serialize(lockey);
			byte[] bvalue = SerializeUtil.serialize(System.currentTimeMillis());
			client.set(bkey, bvalue);
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
	}

	@Override
	public void removeLock(String key) {
		ShardedJedis client = null;
		try {
			client = getClient();
			String lockey = getLockey(key);
			byte[] bkey = SerializeUtil.serialize(lockey);
			client.del(bkey);
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
	}

	@Override
	public long locktime(String key) {
		long locktime = 0;
		ShardedJedis client = null;
		try {
			client = getClient();
			String lockey = getLockey(key);
			byte[] bkey = SerializeUtil.serialize(lockey);
			byte[] bvalue = client.get(bkey);
			if (bvalue != null)
				locktime = (Long) SerializeUtil.unserialize(bvalue);
		} catch (Exception e) {
			log.error(e);
		} finally {
			pool.returnResourceObject(client);
		}
		return locktime;
	}

	public static String getLockey(String key) {
		return Cache.LOCK_PREFIX + Cache.KEY_LOCK_SPLITOR + key;
	}

}

class RedisServerInfo {
	String ip;
	int port;
	String password;
}
