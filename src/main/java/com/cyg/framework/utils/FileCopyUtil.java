package com.cyg.framework.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;

/**
 * @Description: 提供基于对文件及目录的复制功能
 * @author 王道兵
 */
public class FileCopyUtil {

	private static final int BUFFER_SIZE = 1024;

	/**
	 * 基于文件的复制功能
	 * 
	 * @param srcFile
	 *            需要复制的文件源目录
	 * @param destFile
	 *            需要复制的文件目的地目录
	 * @throws IOException
	 */
	public static void copyFile(String srcPath, String destPath)
			throws IOException {
		copyDir(srcPath, destPath, null);
	}

	/**
	 * 基于目录的复制功能
	 * 
	 * @param srcDir
	 * @param destDir
	 * @throws IOException
	 */
	public static void copyDir(String srcDirPath, String destDirPath)
			throws IOException {
		copyDir(srcDirPath, destDirPath, null);
	}

	/**
	 * 清除某个目录
	 * 
	 * @param srcDirPath
	 * @throws IOException
	 * @throws KoalaException
	 */
	public static void clearDir(String srcDirPath) throws IOException {
		File srcDir = new File(srcDirPath);
		try {
			FileUtils.deleteDirectory(srcDir);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 基于目录的复制功能
	 * 
	 * @param srcDir
	 * @param destDir
	 */
	public static void copyDir(String srcDirPath, String destDirPath,
			FileFilter filter) throws IOException {

		File destDir = new File(destDirPath);
		File srcDir = new File(srcDirPath);
		if (destDirPath.endsWith("/"))
			new File(destDirPath).mkdirs();
		try {
			if (srcDir.exists()) {
				if (!srcDir.exists()) {
					return;
				}
				if (srcDir.isFile()) {
					FileUtils.copyFile(srcDir, destDir);
				} else {
					if (filter == null) {
						FileUtils.copyDirectory(srcDir, destDir);
					} else {
						FileUtils.copyDirectory(srcDir, destDir, filter);
					}
				}
			}
			Enumeration<?> urls = Thread.currentThread()
					.getContextClassLoader().getResources(srcDirPath);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();

				JarURLConnection conn = null;
				if ("jar".equals(url.getProtocol())) {
					JarFile jarFile = null;
					conn = (JarURLConnection) url.openConnection();
					jarFile = conn.getJarFile();
					for (Enumeration<?> entries = jarFile.entries(); entries
							.hasMoreElements();) {
						JarEntry entry = (JarEntry) entries.nextElement();
						String entryName = entry.getName();
						if (entryName.startsWith(srcDirPath)
								&& entryName.contains(".")) {
							InputStream in = null;
							OutputStream out = null;
							try {
								in = Thread.currentThread()
										.getContextClassLoader()
										.getResourceAsStream(entryName);
								File descFile = new File(destDir
										+ "/"
										+ entryName.substring(srcDirPath
												.length()));
								File parentFile = descFile.getParentFile();
								if (!parentFile.exists()) {
									parentFile.mkdirs();
								}
								if (!descFile.exists()) {
									descFile.createNewFile();
								}
								out = new FileOutputStream(descFile);
								byte[] buffer = new byte[BUFFER_SIZE];
								int len = 0;
								while ((len = in.read(buffer)) != -1) {
									out.write(buffer, 0, len);
								}
							} finally {
								out.close();
								in.close();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}
}
