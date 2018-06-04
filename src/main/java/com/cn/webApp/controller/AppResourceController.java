package com.cn.webApp.controller;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.ResourcePackageUtils;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.AppResource;
import com.cn.webApp.model.AppResourcePackage;
import com.cn.webApp.service.AppResourcePackageService;
import com.cn.webApp.service.AppResourceService;

/**
 * app资源管理 下午1:51:10
 * 
 * @author rg
 */
@Controller
@RequestMapping(value = "resource")
public class AppResourceController extends BaseController {
	@Resource
	private AppResourceService appresourceservice;
	@Resource
	private AppResourcePackageService appresourcepackageservice;

	Boolean t1 = true;
	Boolean t2 = true;

	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "AppResourceList")
	public ModelAndView serverAppList(ModelMap modelMap) {
		List<AppResourcePackage> list = null;
		try {
			list = appresourcepackageservice.queryAll();
		} catch (Exception e) {
			log.error(e);
		}
		modelMap.put("list", list);
		return new ModelAndView("foundation/AppResourceManage/pagelet/v1.0/AppResourceList", modelMap);
	}

	/**
	 * 列表展示
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "appResourceListGrid")
	public ModelAndView appResourceListGrid(ModelMap modelMap, HttpServletRequest request, AppResource resource,
			String page, String rows) {
		;
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		if (resource.getResourcepackageid() != null && !resource.getResourcepackageid().trim().equals("")) {
			params.put("resourcepackageid", resource.getResourcepackageid());
		}

		// 获取总条数
		long totalCount = appresourceservice.queryAppResourceCount(resource);

		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		// 设置分页对象
		Page pageObject = executePage(request, totalCount, intPage, number);
		pageObject.setJsMethod("reloadAppResourceList");
		// 如排序
		if (pageObject.isSort()) {
			params.put("orderName", pageObject.getSortName());
			params.put("descAsc", pageObject.getSortState());
		} else {
			// 没有进行排序,默认排序方式
			params.put("orderName", "id");
			params.put("descAsc", "asc");
		}
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * number;

		params.put("startIndex", start);
		params.put("endIndex", number);

		// 查询结果集合
		List<AppResource> resourcelist = appresourceservice.queryAppResourcePage(params);
		modelMap.put("total", totalCount);// total键 存放总记录数，必须的
		modelMap.put("rows", resourcelist);
		return new ModelAndView("foundation/AppResourceManage/pagelet/v1.0/AppResourceList_grid", modelMap);
	}

	/**
	 * 上传资源包
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadResourcePackage", method = RequestMethod.POST)
	@ResponseBody
	public Msg uploadResourcePackage(@RequestParam("file") MultipartFile files, HttpServletRequest request) {
		final String sDestPath = "e:/new/";
		final String filePath = sDestPath.replace("/", "\\");
		final MultipartFile file = files;
		final String id = request.getParameter("id");
		Msg msg = new Msg();
		// 线程1 存入本地资源包，遍历所有html页面，存入数据库
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ResourcePackageUtils.Ectract(file.getInputStream(), sDestPath);
					ResourcePackageUtils.getFiles(sDestPath);
					for (String s : ResourcePackageUtils.getFilelist()) {
						String path = s.replace(filePath, "").replace("\\", "/");
						AppResource parameter = new AppResource();
						parameter.setPath(path);
						parameter.setResourcepackageid(id);
						if (appresourceservice.queryBySelective(parameter).size() == 0) {
							AppResource appresource = new AppResource();
							appresource.setId(StringUtil.getUUID());
							appresource.setPath(path);
							appresource.setResourcepackageid(id);
							appresource.setState(1);
							// appresource.setVersion("1.0");
							appresourceservice.insertSelective(appresource);
						}
					}
				} catch (Exception e) {
					t1 = false;
					log.error(e);
				} finally {
					ResourcePackageUtils.getFilelist().clear();
				}
			}
		});
		thread1.start();
		// 线程2 将资源包上传到资源服务器
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "*****";
				// String actionUrl =
				// "http://192.168.157.136:8081/appCloud/user/test";
				try {
					AppResourcePackage appresourcepackage = appresourcepackageservice.selectByPrimaryKey(id);
					String actionUrl = "http://" + appresourcepackage.getServerip() + ":"
							+ appresourcepackage.getServerport() + "/" + appresourcepackage.getApproot() + "/user/test";
					URL url = new URL(actionUrl);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
					/* 设置传送的method=POST */
					con.setRequestMethod("POST");
					/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
					/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con.getOutputStream());
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + file.getName()
							+ "\"" + end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					InputStream fStream = file.getInputStream();
					/* 设置每次写入1024bytes */
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = fStream.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					fStream.close();
					ds.flush();
					/* 取得Response内容 */
					InputStream is = con.getInputStream();
					int ch;
					StringBuffer b = new StringBuffer();
					while ((ch = is.read()) != -1) {
						b.append((char) ch);
					}
					System.out.println("上传成功" + b.toString().trim());
					/* 关闭DataOutputStream */
					ds.close();
				} catch (Exception e) {
					t2 = false;
					System.out.println("上传失败" + e);
					log.error(e);
				}

			}
		});
		thread2.start();
		Boolean flag = true;
		while (flag) {
			if (!thread1.isAlive() && !thread2.isAlive()) {
				flag = false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
		if (t1 && t2) {
			msg.setSuccess(true);
			msg.setMsg("资源包添加成功");
		} else if (t1 && !t2) {
			msg.setSuccess(false);
			msg.setMsg("资源包上传资源服务器失败");
		} else if (t2 && !t1) {
			msg.setSuccess(false);
			msg.setMsg("资源包添加失败");
		} else {
			msg.setSuccess(false);
			msg.setMsg("资源包添加失败,上传资源服务器失败");
		}
		return msg;
	}

	/**
	 * 根据id查询资源包详细
	 * 
	 * @return
	 */
	@RequestMapping(value = "findResourcePackage")
	@ResponseBody
	public AppResourcePackage findResourcePackage(ModelMap modelMap, String id) {
		AppResourcePackage appresourcepackage = new AppResourcePackage();
		try {
			appresourcepackage = appresourcepackageservice.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error(e);
		}
		return appresourcepackage;
	}

	/**
	 * 添加资源包界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addAppResourcePackageUI")
	public String addAppResourcePackageUI(ModelMap modelMap) {
		return "foundation/AppResourceManage/pagelet/v1.0/addResourcePackageUI";
	}

	/**
	 * 
	 * @Description: 新增保存资源包
	 * @return
	 */
	@RequestMapping(value = "addAppResourcePackage", method = RequestMethod.POST)
	@ResponseBody
	public Msg addAppResourcePackage(AppResourcePackage appresourcepackage) {
		Msg msg = new Msg();
		appresourcepackage.setId(StringUtil.getUUID());
		appresourcepackage.setAddtime(StringUtil.getNowDate(null));
		try {
			List<AppResourcePackage> list = appresourcepackageservice
					.queryByName(appresourcepackage.getResourcepackagename());
			if (list.size() == 0) {
				appresourcepackageservice.insertSelective(appresourcepackage);
				log.info("新增资源包" + appresourcepackage.getResourcepackagename());
				msg.setSuccess(true);
				msg.setMsg("新增资源包成功,请刷新页面");
			} else {
				msg.setSuccess(false);
				msg.setMsg("资源包名已存在");
			}
		} catch (Exception e) {
			msg.setSuccess(false);
			msg.setMsg("新增资源包失败");
			log.error(e);
		}

		return msg;
	}

	/**
	 * 修改资源包界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editAppResourcePackageUI", method = RequestMethod.POST)
	public String editAppResourcePackageUI(ModelMap modelMap, HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		AppResourcePackage appresourcepackage = null;
		try {
			appresourcepackage = appresourcepackageservice.selectByPrimaryKey(uuid);
		} catch (Exception e) {
			log.error(e);
		}
		modelMap.put("appresourcepackage", appresourcepackage);
		return "foundation/AppResourceManage/pagelet/v1.0/editResourcePackageUI";
	}

	/**
	 * 
	 * @Description: 保存修改资源包 @param @return @return Msg @throws
	 */

	@RequestMapping(value = "editAppResourcePackage", method = RequestMethod.POST)
	@ResponseBody
	public Msg editAppResourcePackage(HttpServletRequest request, AppResourcePackage appresourcepackage) {
		Msg msg = new Msg();
		try {
			AppResourcePackage oldappresourcepackage = appresourcepackageservice
					.selectByPrimaryKey(appresourcepackage.getId());
			List<AppResourcePackage> list = appresourcepackageservice
					.queryByName(appresourcepackage.getResourcepackagename());
			if (list.size() == 1 && oldappresourcepackage.getResourcepackagename()
					.equals(appresourcepackage.getResourcepackagename()) || list.size() == 0) {
				appresourcepackageservice.updateByPrimaryKeySelective(appresourcepackage);
				log.info("修改资源包" + appresourcepackage.getResourcepackagename());
				msg.setSuccess(true);
				msg.setMsg("修改资源包成功,请刷新页面");
			} else {
				msg.setSuccess(false);
				msg.setMsg("资源包名已存在");
			}
		} catch (Exception e) {
			msg.setSuccess(false);
			msg.setMsg("修改资源包失败");
			log.error(e);
		}
		return msg;
	}

	/**
	 * 修改资源界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editAppResourceUI", method = RequestMethod.POST)
	public String editAppResourceUI(ModelMap modelMap, HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		AppResource appresource = null;
		try {
			appresource = appresourceservice.selectByPrimaryKey(uuid);
		} catch (Exception e) {
			log.error(e);
		}
		modelMap.put("appresource", appresource);
		return "foundation/AppResourceManage/pagelet/v1.0/editResourceUI";
	}

	/**
	 * 
	 * @Description: 保存修改资源包 @param @return @return Msg @throws
	 */

	@RequestMapping(value = "editAppResource", method = RequestMethod.POST)
	@ResponseBody
	public Msg editAppResource(HttpServletRequest request, AppResource appresource) {
		Msg msg = new Msg();
		try {
			AppResource oldappresource = appresourceservice.selectByPrimaryKey(appresource.getId());
			AppResource paramter = new AppResource();
			paramter.setResourcepackageid(appresource.getResourcepackageid());
			paramter.setResourcename(appresource.getResourcename());
			List<AppResource> list = appresourceservice.queryBySelective(paramter);
			if (list.size() == 1 && oldappresource.getResourcename().equals(appresource.getResourcename())
					|| list.size() == 0) {
				appresourceservice.updateByPrimaryKeySelective(appresource);
				log.info("修改资源" + appresource.getResourcename());
				msg.setSuccess(true);
				msg.setMsg("修改资源成功");
			} else {
				msg.setSuccess(false);
				msg.setMsg("资源名已存在");
			}
		} catch (Exception e) {
			msg.setSuccess(false);
			msg.setMsg("修改资源失败");
			log.error(e);
		}
		return msg;
	}

}
