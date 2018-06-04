package com.cn.webApp.service.impl;

import ca.ex.BaseException;
import ca.ex.CAException;
import ca.helper.SoftGrantHelper;
import ca.helper.SoftGrantMachineCodeGenerator;
import ca.packet.grant.model.CustomerInfo;
import ca.packet.grant.model.GrantInfo;
import com.cn.webApp.common.DES;
import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.dao.IAppSoureDao;
import com.cn.webApp.dao.IAuthorizeDao;
import com.cn.webApp.dao.IBaseAppModuleDao;
import com.cn.webApp.model.*;
import com.cn.webApp.service.AppModuleService;
import com.cn.webApp.service.AppReditService;
import com.cn.webApp.service.AuthorizeService;
import com.cn.webApp.service.BaseAppModuleService;
import com.mchange.v1.io.InputStreamUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("authorizeservice")
@Transactional
public class AuthorizeServiceImpl implements AuthorizeService {
	Logger log = Logger.getLogger(this.getClass());
	@Resource
	private IAuthorizeDao iauthorizedao;
	@Resource
	private IAppSoureDao iappsouredao;
	@Resource
	private IBaseAppModuleDao ibaseappmoduledao;
	@Resource
	private AppReditService appreditservice;
	@Resource
	private AppModuleService appmoduleservice;
	@Resource
	private BaseAppModuleService baseappmoduleservice;

	DateFormat invalidDateFromat = new SimpleDateFormat("yyyyMMdd");

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int insert(Authorize record) {
		return iauthorizedao.insert(record);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int deleteByPrimaryKey(String id) {
		return iauthorizedao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int insertSelective(Authorize record) {
		return iauthorizedao.insertSelective(record);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Authorize selectByPrimaryKey(String id) {
		return iauthorizedao.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int updateByPrimaryKeySelective(Authorize record) {
		return iauthorizedao.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int updateByPrimaryKey(Authorize record) {
		return iauthorizedao.updateByPrimaryKey(record);
	}

	/**
	 * 应用授权
	 */
	@Override
	public void appGrant(InputStream in, String machineCode,HttpServletRequest request) {
		try {
			String city_pre = "05";
			SoftGrantHelper helper = new SoftGrantHelper();
			List<AppSoure> appsourelist = iappsouredao.findAll();
			if (null == appsourelist || appsourelist.size() <= 0) {
				throw new BaseException("系统标识对照表为空没有初始数据");
			}
			// 上传文件
			helper.uploadFile(InputStreamUtils.getBytes(in), machineCode);

			CustomerInfo customerInfo = helper.getCustomerInfo(machineCode);
			System.out.println("==customerInfo==" + customerInfo);
			// 获得APP count
			int appCount = helper.getGrantAppCount(machineCode);
			// 总模块个数，用于日志记录
			int moduleC = 0;
			/**
			 * app信息获得
			 */
			List<GrantInfo> appInfos = helper.getGrantAppInfo(appCount, machineCode);
			for (GrantInfo info : appInfos) {
				Authorize authorize = new Authorize();
				//authorize.setId(StringUtil.getUUID());
				authorize.setAuthId(city_pre + info.getSysId() + "00");
				authorize.setAuthType("app");
				authorize.setStartTime(new BigDecimal(System.currentTimeMillis()));
				authorize.setEndTime(new BigDecimal(invalidDateFromat.parse(info.getInvalidDt()).getTime()));

				AppRedit app = new AppRedit();
				app.setAppid(authorize.getAuthId());
				app.setSyscode(info.getCharacterCode());
				app.setAccreditedcode(info.getRegCode());
				for (AppSoure appsoure : appsourelist) {
					if (authorize.getAuthId().equals(appsoure.getAppcode())) {
						app.setAppname(appsoure.getAppname());
						app.setAppver(appsoure.getAppver());
						app.setAppdescription(appsoure.getDescription());
					}
				}
				app.setApplimitnum(info.getCustCountLimit());
				app.setReditdt(new Date());
				app.setOpdt(new Date());
				app.setApplimitdate(new Date(authorize.getEndTime().longValue()));
				app.setReditasn(customerInfo.getSerialNo());
				app.setApptype("0");
				app.setSign("0");
				saveOrUpdate(authorize, app, null);
				// 删除对应关系表
				ibaseappmoduledao.deleteByAppId(app.getAppid());
				// 模块数据获得
				int moduleCount = helper.getGrantModuleCount(machineCode, info.getSysId());
				List<GrantInfo> modules = helper.getGrantModuleInfo(info.getSysId(), moduleCount, machineCode);
				for (GrantInfo module : modules) {
					Authorize auth = new Authorize();
					auth.setAuthId(city_pre + info.getSysId() + module.getSysId());
					auth.setAuthType("module");
					auth.setStartTime(new BigDecimal(System.currentTimeMillis()));
					auth.setEndTime(new BigDecimal(invalidDateFromat.parse(module.getInvalidDt()).getTime()));

					AppModule _module = new AppModule();
					_module.setAppid(app.getAppid());
					_module.setModuleid(auth.getAuthId());
					_module.setModulecode(info.getCharacterCode());// module.getCharacterCode());
					_module.setReditedcode(module.getRegCode());
					for (AppSoure appsoure : appsourelist) {
						if (auth.getAuthId().equals(appsoure.getAppcode())) {
							_module.setModulename(appsoure.getAppname());
							_module.setDescription(appsoure.getDescription());
						}
					}
					_module.setLimitnum(module.getCustCountLimit());
					_module.setLimitdt(new Date(auth.getEndTime().longValue()));
					_module.setReditdt(new Date());
					_module.setReditasn(customerInfo.getSerialNo());
					_module.setApptype("0");
					_module.setOpdt(new Date());
					_module.setSortid(new Double(0));
					_module.setSyscode("0");
					saveOrUpdate(auth, null, _module);
					moduleC++;
				}
			}
			StringBuilder logContent = new StringBuilder(" 注册了文件信息【");
			logContent.append("应用包个数：" + appCount);
			logContent.append("，应用模块个数：" + moduleC+"】");
			// 业务日志操作暂未实现
			// BusinessLog.write(EnumLogType.PLATFORM_REGISTER,
			// Constant.REGISTER, logContent.toString()+"】");
			User loginUser =HttpUtils.getUser(request);
			if(loginUser==null){
				loginUser=new User();
			}
			log.info("用户名："+loginUser.getEmpcode()+" 客户代码"+loginUser.getCustomerunitcode()+logContent.toString());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof CAException || e instanceof BaseException) {
				throw new BaseException(e.getMessage(), e);
			} else {
				throw new BaseException("应用授权失败", e);
			}
		}

	}

	@Override
	public void saveOrUpdate(Authorize paramT, AppRedit _app, AppModule _module) {
		Authorize parameter = new Authorize();
		parameter.setAuthId(paramT.getAuthId());
		parameter.setAuthType(paramT.getAuthType());
		Authorize auth = selectBySelective(parameter);
		if (auth != null) {
			auth.setAuthId(paramT.getAuthId());
			auth.setAuthType(paramT.getAuthType());
			auth.setStartTime(paramT.getStartTime());
			auth.setEndTime(paramT.getEndTime());
			paramT = auth;
		}else{
			paramT.setId(StringUtil.getUUID());
		}
		String crc = generateCRC(paramT);
		paramT.setValidateCode(crc);
		saveOrUpdate(paramT);

		if (paramT.getAuthType().equals("app")) {
			// 更新对应Base_app_redit表中相关信息
			AppRedit app = appreditservice.findByAppId(paramT.getAuthId());
			if (app != null) {
				app.setApplimitnum(_app.getApplimitnum());
				app.setAppver(_app.getAppver());
				app.setOpdt(new Date());
				app.setApplimitdate(new Date(paramT.getEndTime().longValue()));
				app.setAppname(_app.getAppname());
				app.setAppdescription(_app.getAppdescription());
				appreditservice.updateByPrimaryKeySelective(app);
			} else {
				_app.setId(StringUtil.getUUID());
				appreditservice.insert(_app);
			}
		} else if (paramT.getAuthType().equals("module")) {
			// 更新对应base_app_module表中相关信息
			AppModule module = appmoduleservice.selectByPrimaryKey(paramT.getAuthId());
			if (module != null) {
				module.setLimitnum(_module.getLimitnum());
				module.setModulename(_module.getModulename());
				module.setOpdt(new Date());
				module.setLimitdt(new Date(paramT.getEndTime().longValue()));
				module.setApptype("0");
				module.setDescription(_module.getDescription());
				appmoduleservice.updateByPrimaryKey(module);
			} else {
				_module.setId(StringUtil.getUUID());
				appmoduleservice.insert(_module);
			}
			// 更新对应base_appmodule表中相关信息
			BaseAppModule moduleandrelation=new BaseAppModule();
			moduleandrelation.setId(StringUtil.getUUID());
			moduleandrelation.setAppid(_module.getAppid());
			moduleandrelation.setModuleid(_module.getModuleid());
			moduleandrelation.setSortid(0);
			baseappmoduleservice.insert(moduleandrelation);
			
		}
	}

	/**
	 * 获得所有软件授权，或根据CRC校验码，将被篡改的数据之间过滤掉
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Authorize> getAll() {
		List<Authorize> authorizes = iauthorizedao.getAll();
		Iterator<Authorize> iterator = authorizes.iterator();
		while (iterator.hasNext()) {
			Authorize baseAuthorize = (Authorize) iterator.next();
			if (!validateCRC(baseAuthorize)) {
				authorizes.remove(baseAuthorize);
			}
		}

		return authorizes;
	}

	/**
	 * 验证校验码，跟数据是否匹配
	 * 
	 * @param authorize
	 * @return
	 */
	private boolean validateCRC(Authorize authorize) {
		if (authorize.getAuthId().startsWith("0501")) {// 产品平台应用包，不做授权验证
			return true;
		}
		String crc = generateCRC(authorize);
		if (crc.equals(authorize.getValidateCode())) {
			return true;
		}
		return false;
	}

	/**
	 * 根据数据字段，生成CRC校验码
	 * 
	 * @param authorize
	 * @return
	 */
	private String generateCRC(Authorize authorize) {
		String str = authorize.getAuthId() + authorize.getAuthType();
		String machineCode = SoftGrantMachineCodeGenerator.generateMachineCode();
		/**
		 * 使用机器特征码，对数据进行加密
		 */
		try {
			return new String(DES.encrypt(str.getBytes(), machineCode.getBytes()),"utf-8");
		} catch (Exception e) {
			log.error(e);
			throw new BaseException("生成Des校验码错误", e);
		}
	}

	@Override
	public Authorize selectBySelective(Authorize record) {
		List<Authorize> list = iauthorizedao.selectBySelective(record);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void saveOrUpdate(Authorize paramT) {
		Authorize authorize=iauthorizedao.selectByPrimaryKey(paramT.getId());
		if (authorize != null) {
			iauthorizedao.updateByPrimaryKey(paramT);
		}else{
			iauthorizedao.insertSelective(paramT);
		}

	}

}
