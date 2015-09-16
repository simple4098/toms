package com.toms.test;

import com.fanqielaile.toms.support.util.FileDealUtil;

import java.io.File;

/**
 * Created by wangdayin on 2015/9/14.
 */
public class DownLoadFileTest {


    public static void main(String[] args) {
//        try{
//            FileDealUtil.downLoadFromUrl("http://i6.topit.me/6/5d/45/1131907198420455d6o.jpg",
//                    "百度.jpg", "e:/");
//        }catch (Exception e) {
//            // TODO: handle exception
//        }
        //测试压缩
//        File[] files = new File[]{new File("E:/1.docx"),new File("E:/2.docx")};
//        File file = new File("E:/3.zip");
////        FileDealUtil.ZipFiles(files,file);

//        FileDealUtil.downLoadFromUrl("http://image.fangcang.com/upload/USP/increment_2015-09-15.zip", "2015-09-15.zip", new DownLoadFileTest().getCurrentPath()+"/fc_data/2015-09-15");
//        FileDealUtil.unZipFiles(new File(new DownLoadFileTest().getCurrentPath()+"/fc_data/2015-09-15/2015-09-15.zip"),new DownLoadFileTest().getCurrentPath()+ "/fc_data/2015-09-15/");
        FileDealUtil.deleteDir(new File(new DownLoadFileTest().getCurrentPath() + "/fc_data"));

    }

    private String getCurrentPath() {
        String path = getClass().getResource("../").getFile().toString();
        return System.getProperty("user.dir");
    }
}
