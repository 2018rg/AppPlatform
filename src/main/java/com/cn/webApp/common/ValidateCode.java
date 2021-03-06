/**
 *
 */
package com.cn.webApp.common;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author ocean
 * date : 2014-4-18 上午09:52:37
 * email : zhangjunfang0505@163.com
 * Copyright :  zhengzhou
 */
public final class ValidateCode {

	public   static void  outputImg(HttpServletRequest request, HttpServletResponse response) throws IOException{

		int width = 60;
		int height = 20;
		char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'h', 'j', 'k', 'm', 'n',
				'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0',
				'2', '3', '4', '5', '6', '7', '8', '9' };

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 设定背景颜色
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);

		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生的验证码
		String strEnsure = "";
		// 4代表4位验证码，如果要生成等多位的验证码 ，则加大数值

		for (int i = 0; i < 4; i++) {
			strEnsure += mapTable[(int) (mapTable.length * Math.random())];
		}
		// 将验证码显示在图像中，如果要生成更多位的验证码，增加drawString语句
		g.setColor(Color.black);
		g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));
		String str = strEnsure.substring(0, 1);
		g.drawString(str, 8, 17);
		str = strEnsure.substring(1, 2);
		g.drawString(str, 20, 15);
		str = strEnsure.substring(2, 3);
		g.drawString(str, 35, 18);
		str = strEnsure.substring(3, 4);
		g.drawString(str, 45, 15);
		// 随机产生10个干扰点

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			g.drawOval(x, y, 1, 1);
		}
		// 释放图形上下文
		g.dispose();
		
	/*	//王鹏修改：将验证码保存到redis里面
		if(StringUtils.isEmpty(CookieUtil.getCookieByName(request, "validateCode"))){//判断是否第一次登陆
			//新逻辑处理登录信息
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String currentDate=df.format(new Date());// new Date()为获取当前系统时间
			
			//得到请求的sessionId
			String sessionId=request.getSession().getId();
			
			String key="validateCode_"+currentDate+"_"+sessionId;
			CookieUtil.addCookie(response, "validateCode", key, 0,false,null);
			
			RedisAPI.set(key, strEnsure);
		}else{
			RedisAPI.set(CookieUtil.getCookieByName(request, "validateCode"), strEnsure);
		}*/
		request.getSession().setAttribute("code", strEnsure);
		
		// 禁止图像缓存。
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		ImageIO.write(image, "jpeg", response.getOutputStream());

	}

}
