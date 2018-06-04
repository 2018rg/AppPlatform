package com.cn.webApp.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ResourcePackageUtils {
	private static ArrayList<String> filelist = new ArrayList<String>();

	/**
	 * 解压缩
	 * 
	 * @param sZipPathFile
	 *            要解压的文件
	 * @param sDestPath
	 *            解压到某文件夹
	 * @return
	 */
	public static void Ectract(InputStream sZipPathFile, String sDestPath) {
		try {
			// 先指定压缩档的位置和档名，建立FileInputStream对象
		//	FileInputStream fins = (FileInputStream) sZipPathFile;
			// 将fins传入ZipInputStream中
			Charset gbk = Charset.forName("GBK");
			ZipInputStream zins = new ZipInputStream(sZipPathFile, gbk);
			ZipEntry ze = null;
			byte[] ch = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				File zfile = new File(sDestPath +"/"+ ze.getName());
				File fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
					zins.closeEntry();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.close();
				}
			}
			sZipPathFile.close();
			zins.close();
		} catch (Exception e) {
			System.err.println("Extract error:" + e.getMessage());
		}
	}

	public static ArrayList<String> getFilelist() {
		return filelist;
	}

	public static void setFilelist(ArrayList<String> filelist) {
		ResourcePackageUtils.filelist = filelist;
	}

	/**
	 * 遍历文件夹中所有的文件
	 * 
	 * @param filePath
	 */
	public static void getFiles(String filePath) {
		File root = new File(filePath);
		if (!root.exists()) {
			System.out.println("路径不存在");
			return;
		}
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				/*
				 * 递归调用
				 */
				getFiles(file.getAbsolutePath());
			} else {
				String path=file.getAbsolutePath();
				if(path.endsWith(".html")||path.endsWith(".jsp")){
				filelist.add(path);
				}
			}
		}
	}

}
