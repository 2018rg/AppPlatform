package com.cn.webApp.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import org.apache.log4j.Logger;


 

/**
 * <p>
 * Title: 常用的字符串处理函数
 * </p>
 * <p>
 * Description: 这里是一些常用的字符串处理方法，全部静态方法，不用创建实例，直接引用即可
 */

public class StringUtil
{
	//订单状态
	public static String payCreate="1";
	public static String payAppSuccess="2";
	public static String paySuccess="3";
	public static String payCreateBIllSUC="4";
	public static String payBillSUC="5";
	public static String payCreateBillFail="6";
	
	static Logger log=Logger.getLogger(StringUtil.class);
	public static void main(String[] args) {
		String str="00123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456地址          12345678901234567890123456789012345678901234567890123456789012用户名称费用类别2015-2016   77.74";
		System.out.println("长度"+getStrByteCount(str));
		System.out.println("截取字符串:"+splitStr(str, 9, 12));
		System.out.println(leftAddSpace(str, 16));
		System.out.println("发送内容的长度:"+indexLen(leftAddSpace(str, 16)));
		
	}
	
	/**
	 * 获取字节的长度
	 */
	public static int getStrByteCount(String str){
		int num=0;
		try{
			if(!StringUtil.isEmpty(str)){
				num=str.getBytes("GBK").length;
			}
		}catch(Exception e){
			log.error("得到"+str+"字节长度时候出错");
		}
		return num;
	}
	
	/**
	 * null转换成空
	 * @param str
	 * @return
	 */
	public static String changeNull(String str){
		if(isEmpty(str)){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	 * 按字节截取字符串（GBK编码）
	 * start从1开始
	 * @param targetString
	 * @param end
	 * @return
	 * @throws Exception
	 */
	 public static String getSubStringBybyte(String targetString,int start, int end) {
//		 String temp="";
//		 try{
//			 if (targetString.getBytes("GBK").length < end) {
//		            throw new Exception("超过长度");
//		        }
//		        temp = targetString;
//		        for (int i = 0; i < targetString.length(); i++) {
//		            if (temp.getBytes("GBK").length <= end) {
//		                break;
//		            }
//		            temp = temp.substring(start-1, temp.length() - 1);
//		        }
//		        return temp;
//		 }catch(Exception e){
//			 log.error("截取字符串："+targetString+"出错。开始："+start+"  ,结束："+end);
//			 return temp;
//		 }
		 byte[] resbyte = null ;
		 start=start-1;
		 int length=end-start;
		 String resCode="";
		 byte[] newData = new byte[length];
		 try {
			 resbyte = targetString.getBytes("GBK");
			 System.arraycopy(resbyte, start, newData,0, length);
			 resCode = new String(newData,"GBK");
		  } catch (UnsupportedEncodingException e) {
			  log.error("截取字符串："+targetString+"出错。开始："+start+"  ,结束："+end,e);
		  }
		 return resCode;
	 }
	 
	 /**
	  * 左填充字符串，空格补齐
	  */
	public static String leftAddSpace(String str,int num){
		String resultStr="";
		String space="";
		int length=0;
		try {
			length=getStrByteCount(str);
			if(length<=num){
				for(int i=0;i<(num-length);i++){
					space+=" ";
				}
				resultStr=space+str;
				//System.out.println("补齐后字符串长度为："+getStrByteCount(resultStr));
			}else{
				log.error("补齐字符串："+str+"出错。");
			}
			
		} catch (Exception e) {
			log.error("补齐字符串："+str+"异常。"+e);
		}
		return resultStr;
	}
	
	/**
	  * 左填充字符串，0补齐
	  */
	public static String leftAddZero(String str,int num){
		String resultStr="";
		String space="";
		int length=0;
		try {
			length=getStrByteCount(str);
			if(length<=num){
				for(int i=0;i<(num-length);i++){
					space+="0";
				}
				resultStr=space+str;
				//System.out.println("补齐后字符串长度为："+getStrByteCount(resultStr));
			}else{
				log.error("补齐字符串："+str+"出错。");
			}
			
		} catch (Exception e) {
			log.error("补齐字符串："+str+"异常。"+e);
		}
		return resultStr;
	}
	
	/**
	  * 右填充字符串，空格补齐
	  */
	public static String rightAddSpace(String str,int num){
		String resultStr="";
		String space="";
		int length=0;
		try {
			length=getStrByteCount(str);
			if(length<=num){
				for(int i=0;i<(num-length);i++){
					space+=" ";
				}
				resultStr=str+space;
				//System.out.println("补齐后字符串长度为："+getStrByteCount(resultStr));
			}else{
				log.error("补齐字符串："+str+"出错。");
			}
			
		} catch (Exception e) {
			log.error("补齐字符串："+str+"异常。"+e);
		}
		return resultStr;
	}
	
	/**
	 * 前四位，发送内容的长度
	 */
	public static String indexLen(String str){
		int strLength=getStrByteCount(str);
		int countLength=getStrByteCount(strLength+"");
		String space="";
		String reslutStr="";
		if(countLength<4){
			for(int i=0;i<(4-countLength);i++){
				space+="0";
			}
			reslutStr=space+strLength;
		}
		return reslutStr;
	}
	
	
	/**
	 * 分割字节，first从1开始
	 */
	public static String splitStr(String str,int first,int end){
//		String result=str.substring(first-1,end);//按字符截取
		String result=getSubStringBybyte(str, first, end);//按字节截取
		return result;
	}
	
	
	/**
	 * 获取文件配置输出流
	 * @return
	 */
	public static Properties getProperties(){
		Properties systemProp = new Properties();
		InputStream isInputStream = null;
		try {
			isInputStream = StringUtil.class.getResourceAsStream("/system.properties");
			systemProp.load(isInputStream);
			return systemProp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return systemProp;
		
	}

	/**
	 * 获取热力socketip地址
	 * @param respId
	 * @return
	 */
	public static String getReliServiceIp(){
		String tempStr =  getProperties().getProperty("reliServiceIp");
		return StringUtil.isEmpty(tempStr) ? "" : tempStr.trim();
	}
	
	/**
	 * 获取热力socket 端口号
	 * @param respId
	 * @return
	 */
	public static int getReliServicePort(){
		String tempStr =  getProperties().getProperty("reliServicePort");
		String port=StringUtil.isEmpty(tempStr) ? "" : tempStr.trim();
		return Integer.parseInt(port);
	}
	
	/**
	 * 获取热力socket 超时时间
	 * @param respId
	 * @return
	 */
	public static int getReliServiceTimeOut(){
		String tempStr =  getProperties().getProperty("reliServiceTimeOut");
		String timeOut=StringUtil.isEmpty(tempStr) ? "" : tempStr.trim();
		return Integer.parseInt(timeOut);
	}
	
	/**
	 * 获取银行核心的socketip地址
	 * @param respId
	 * @return
	 */
	public static String getBankServiceIp(){
		String tempStr =  getProperties().getProperty("bankServiceIp");
		return StringUtil.isEmpty(tempStr) ? "" : tempStr.trim();
	}
	
	/**
	 * 获取银行核心的socket 端口号
	 * @param respId
	 * @return
	 */
	public static int getBankServicePort(){
		String tempStr =  getProperties().getProperty("bankServicePort");
		String port=StringUtil.isEmpty(tempStr) ? "" : tempStr.trim();
		return Integer.parseInt(port);
	}
	
	/**
	 * 获取银行核心socket 超时时间
	 * @param respId
	 * @return
	 */
	public static int getBankServiceTimeOut(){
		String tempStr =  getProperties().getProperty("bankServiceTimeOut");
		String timeOut=StringUtil.isEmpty(tempStr) ? "" : tempStr.trim();
		return Integer.parseInt(timeOut);
	}
	
	/**
	 * 根据key获取配置
	 * @param respId
	 * @return
	 */
	public static String getPropertiesBykey(String key){
		String tempStr =  getProperties().getProperty(key);
		String result=StringUtil.isEmpty(tempStr) ? "" : tempStr.trim();
		return result;
	}
	
	/**
	 * 返回信息对应的内容
	 */
	public static String respIdToStr(String respId){
		String resultStr="";
		if(respId.equals("00")){
			resultStr="成功";
		}
		else if(respId.equals("01")){
			resultStr="访问地址不合法";
		}
		else if(respId.equals("02")){
			resultStr="请求信息包有误";
		}
		else if(respId.equals("03")){
			resultStr="交易码错误";
		}
		else if(respId.equals("04")){
			resultStr="无效用户编号";
		}
		else if(respId.equals("05")){
			resultStr="不允许银行查询";
		}
		else if(respId.equals("06")){
			resultStr="不允许银行交费";
		}
		else if(respId.equals("07")){
			resultStr="用户欠费合计为0，不能交费";
		}
		else if(respId.equals("08")){
			resultStr="抹帐没有查询到原始流水号信息";
		}
		else if(respId.equals("09")){
			resultStr="对帐文件不存在";
		}
		else if(respId.equals("11")){
			resultStr="热电公司服务处理错误（系统或者数据库出现问题），事务处理失败";
		}
		else if(respId.equals("17")){
			resultStr="往年有欠费 ";
		}
		else if(respId.equals("20")){
			resultStr="交费金额不足";
		}
		else if(respId.equals("21")){
			resultStr="交费金额超余";
		}
		return resultStr;
	}
	
	/**
	 * 返回信息对应的内容
	 */
	public static String getCode(String respCode){
		String resultStr="";
		if(respCode.equals("00")){
			resultStr="0000";
		}
		else if(respCode.equals("01")){
			resultStr="RE03";
		}
		else if(respCode.equals("02")){
			resultStr="PE02";
		}
		else if(respCode.equals("03")){
			resultStr="PE02";
		}
		else if(respCode.equals("04")){
			resultStr="RE01";
		}
		else if(respCode.equals("05")){
			resultStr="RE04";
		}
		else if(respCode.equals("06")){
			resultStr="RE05";
		}
		else if(respCode.equals("07")){
			resultStr="RE06";
		}
		else if(respCode.equals("08")){
			resultStr="RE08";
		}
		else if(respCode.equals("09")){
			resultStr="RE09";
		}
		else if(respCode.equals("11")){
			resultStr="RE11";
		}
		else if(respCode.equals("17")){
			resultStr="RE12 ";
		}
		else if(respCode.equals("20")){
			resultStr="RE13";
		}
		else if(respCode.equals("21")){
			resultStr="RE14";
		}
		else if(respCode.equals("22")){
			resultStr="RE15";
		}
		return resultStr;
	}
	
	/**
	 * 获取返回交易码
	 */
	public static String getRespId(String resultMsg){
		return StringUtil.splitStr(resultMsg, 1, 2);
	}
	
	/**
	 * [判断字符串是否为null为""]
	 * 
	 * @param str
	 * @return boolean true:为null或""
	 */

	public static boolean isEmpty(String str){
		return null == str || "".equals(str.trim());
	}
	
		 
	/**
	 * 根据开始截取位置取得指定长度的数据
	 * @param xmlMsg --报文数据
	 * @param starsite --开始截取的位置
	 * @param length --截取的数据位数
	 * @return
	 */
	public static String getSubStr(String xmlMsg,int starsite,int length){
		byte[] resbyte = null ;
		String resCode="";
		byte[] newData = new byte[length];
			try {
				resbyte = xmlMsg.getBytes("GBK");
				System.arraycopy(resbyte, starsite, newData,0, length);
				resCode = new String(newData,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 
		return resCode;
	}
	
	public static ArrayList<String> readFile(File filePath,String encoding) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		InputStream in = null;
		BufferedReader br = null;
		String line = null;
		try {
			in = new FileInputStream(filePath);
			br = new BufferedReader(new InputStreamReader(in, encoding));
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			//logger.error("读文件[" + filePath + "]文件不存在：", e);
			throw new Exception("读文件[" + filePath + "]文件不存在：" + e);
		} catch (UnsupportedEncodingException e) {
			//logger.error("读文件[" + filePath + "]编码不正确：", e);
			throw new Exception("读文件[" + filePath + "]编码不正确：" + e);
		} catch (IOException e) {
			//logger.error("读文件[" + filePath + "]IO异常：", e);
			throw new Exception("读文件[" + filePath + "]IO异常：" + e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					//logger.error("读文件[" + filePath + "]关闭IO异常：", e);
					throw new Exception("读文件[" + filePath + "]关闭IO异常：" + e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					//logger.error("读文件[" + filePath + "]关闭IO异常：", e);
					throw new Exception("读文件[" + filePath + "]关闭IO异常：" + e);
				}
			}
		}
		return list;
	}
	
	/**
	 * 取得uuid
	 * @return
	 */
	public static String getUUID (){
		String uuidStr = UUID.randomUUID().toString();
		return uuidStr.substring(0,8)+uuidStr.substring(9,13)+uuidStr.substring(14,18)+uuidStr.substring(19,23)+uuidStr.substring(24); 
	}
	
	/**
	 * 将日期date转换成指定格式的日期字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateReverseString(Date date,String formatStr){
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	public static String dateId(){
		return dateReverseString(new Date(),"YYYYMMddHH")+Math.random()*100;
	}
	/**
	 * 将日期字符串转换成指定格式的日期date
	 * @param source
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException 
	 */
	public static Date stringReverseDate(String source,String formatStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.parse(source);
	}
	
	/**
	 * 根据日期偏移量计算出当前日期的偏差值
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("static-access")
	public static String offSetDate(String formatStr) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DAY_OF_MONTH, Integer.parseInt(StringUtil.getPropertiesBykey("dateoffset")));
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String newDateStr = sdf.format(cal.getTime());
//		Date newDate = stringReverseDate(newDateStr, formatStr);
		return newDateStr;
	}
	/**
	 * 
	 * @Description: 以指定格式获取当前时间 --可以放到公共方法
	 * @param format
	 *            格式 如：//"yyyy-MM-dd HH:mm:ss"
	 * @param  format
	 *  
	 * @return Date
	 * @throws
	 */
	public static Date getNowDate(String format) {
		Date date = new Date();
		if ("".equals(format) || format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sbf = new SimpleDateFormat(format);
			try {
				date = sbf.parse(sbf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return date;
	}
}
