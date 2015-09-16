package com.fanqielaile.toms.support.util;

import com.fanqie.util.DateUtil;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by wangdayin on 2015/9/14.
 * 文件工具类
 */
public class FileDealUtil {
    private static Logger logger = LoggerFactory.getLogger(FileDealUtil.class);

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            logger.info("下载" + url + "文件成功");
        } catch (Exception e) {
            logger.error("下载文件出错" + e.getMessage());
        }


    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 压缩文件
     *
     * @param srcfile File[] 需要压缩的文件列表
     * @param zipfile File 压缩后的文件
     */
    public static void ZipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                String str = srcfile[i].getName();
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            logger.info("压缩文件完成");
        } catch (IOException e) {
            logger.error("压缩文件出错" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * zip解压缩
     *
     * @param zipfile File 需要解压缩的文件
     * @param descDir String 解压后的目标目录
     */
    public static void unZipFiles(File zipfile, String descDir) {
        try {
            ZipFile zf = new ZipFile(zipfile);
            out:
            for (Enumeration entries = zf.getEntries(); entries
                    .hasMoreElements(); ) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String zipEntryName = entry.getName();
                if (!zipEntryName.contains(".")) {
                    continue out;
                }
                InputStream in = zf.getInputStream(entry);
                OutputStream out = new FileOutputStream(descDir + zipEntryName);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
            logger.info("zip解压完成");
        } catch (IOException e) {
            logger.error("ZIP解压出错" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean
     */
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    file.delete();
                }
            }
        }
        dir.delete();
    }

    public static void main(String[] args) {
        File file = new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd") + "\\" + DateUtil.format(new Date(), "yyyy-MM-dd") + ".zip");
        file.delete();
//        deleteDir(new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd")));
    }

    /**
     * 获取项目路径
     *
     * @return
     */
    public static String getCurrentPath() {
        return System.getProperty("user.dir");
    }

}
