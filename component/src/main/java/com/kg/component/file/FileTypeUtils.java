package com.kg.component.file;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 获取文件真实类型 - 工具类
 *
 * @author ziro
 * @date 2022-04-07 18:14:17
 */
public class FileTypeUtils {
    // 常见文件头map
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    static {
        /**
         * 初始化：常见文件头信息
         * 注意：目前的文件头map可能不完整，如果文件类型判断失败，请自行测试，并把新的文件头，放到最后
         */
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");//TIFF (tif)
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp");//16色位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp");//24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp");//256色位图(bmp)
        FILE_TYPE_MAP.put("41433130313500000000", "dwg");//CAD (dwg)
        FILE_TYPE_MAP.put("3c21444f435459504520", "html");//HTML (html)
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm");//HTM (htm)
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css");//css
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js");//js
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");//Rich Text Format (rtf)
        FILE_TYPE_MAP.put("38425053000100000000", "psd");//Photoshop (psd)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");//Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");//MS Access (mdb)
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462d312e", "pdf");//Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb");//rmvb/rm相同
        FILE_TYPE_MAP.put("464c5601050000000900", "flv");//flv与f4v相同
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg");//
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv");//wmv与asf相同
        FILE_TYPE_MAP.put("52494646e27807005741", "wav");//Wave (wav)
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid");//MIDI (mid)
        FILE_TYPE_MAP.put("526172211a07", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");//可执行文件
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");//jsp文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");//MF文件
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");//xml文件
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");//xml文件
        FILE_TYPE_MAP.put("7061636b616765207765", "java");//java文件
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");//bat文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");//gz文件
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");//bat文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");//bat文件
        FILE_TYPE_MAP.put("49545346030000006000", "chm");//bat文件
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");//bat文件
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("6D6F6F76", "mov");//Quicktime (mov)
        FILE_TYPE_MAP.put("FF575043", "wpd");//WordPerfect (wpd)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx");//Outlook Express (dbx)
        FILE_TYPE_MAP.put("2142444E", "pst");//Outlook (pst)
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf");//Quicken (qdf)
        FILE_TYPE_MAP.put("E3828596", "pwl");//Windows Password (pwl)
        FILE_TYPE_MAP.put("2E7261FD", "ram");//Real Audio (ram)

        //================前面是网络上常见的文件头，后边是自己添加的（新文件头继续在后边添加即可）=================
        FILE_TYPE_MAP.put("ffd8ff", "jpg");// JPEG (jpg)
        FILE_TYPE_MAP.put("89504e47", "png");// PNG (png)
        FILE_TYPE_MAP.put("4749463837", "gif");// GIF (gif)
        FILE_TYPE_MAP.put("4749463839", "gif");// GIF (gif)
        FILE_TYPE_MAP.put("00000020667479706", "mp4");
        FILE_TYPE_MAP.put("00000018667479706D70", "mp4");
        // MS Excel 注意：2003版word、msi和excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10", "doc,vsd,xls,wps,ppt");
        // 新版word、excel、ppt等，文件头一样
        FILE_TYPE_MAP.put("504B0304", "docx,xlsx,pptx,zip,jar");
    }

    /**
     * 获取文件类型
     *
     * @param filePath 文件地址
     * @return 文件类型
     */
    public static String getFileType(String filePath) throws IOException {
        return getFileType(new FileInputStream(filePath));
    }

    /**
     * 获取文件类型
     *
     * @param file 文件
     * @return 文件类型
     */
    public static String getFileType(File file) throws IOException {
        return getFileType(new FileInputStream(file));
    }

    /**
     * 获取文件类型
     *
     * @param fis 文件流
     * @return 文件类型
     */
    public static String getFileType(FileInputStream fis) throws IOException {
        byte[] bytes = new byte[10];
        fis.read(bytes, 0, bytes.length);
        return assertFileType(bytes);
    }

    /**
     * 获取文件类型
     *
     * @param fileBytes 文件bytes
     * @return 文件类型
     */
    public static String getFileType(byte[] fileBytes) throws IOException {
        byte[] bytes = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = fileBytes[i];
        }
        return assertFileType(bytes);
    }

    /**
     * （私有）判断文件类型
     * 与文件头FILE_TYPE_MAP比对，返回文件类型
     *
     * @param bytes 文件头bytes
     * @return 文件类型
     */
    private static String assertFileType(byte[] bytes) {
        String fileCode = getFileHeader(bytes);
        String res = null;
        //这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
        Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            if (key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                res = FILE_TYPE_MAP.get(key);
                break;
            }
        }
        return res == null ? null : res.toLowerCase();
    }

    /**
     * （私有）获取文件头
     *
     * @param bytes 文件头bytes
     * @return 返回文件头Hex字符串
     */
    private static String getFileHeader(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 测试文件类型，获取文件头
     */
    public static void main(String[] args) throws Exception {

//        String type = getFileType("E:\\Users\\ziro\\Desktop\\ccxs.xlsx");
//        System.out.println(type);

        File[] ls = FileUtil.ls("E:\\");
        for (int i = 0; i < ls.length; i++) {
            String type2 = ls[i].getName().substring(ls[i].getName().lastIndexOf(".") + 1);
            String type1 = null;
            try {
                FileInputStream fis = new FileInputStream(ls[i]);
                type1 = FileTypeUtils.getFileType(fis);
            } catch (IOException e) {
                continue;
//                e.printStackTrace();
            }
            if (type1 == null) {
                System.out.println("------------------------------------------------------------------------------------------------------" +
                    type2 + "==============" + ls[i].getName());
                continue;
            }
            System.out.print(type2);
//            System.out.print(ls[i].getName());
            System.out.print("               ");
            System.out.print(type1.contains(type2));
            System.out.print("               ");
            System.out.print(type1);
            System.out.print("               ");
            byte[] bytes = new byte[10];
            IoUtil.toStream(ls[i]).read(bytes, 0, bytes.length);
            System.out.print(FileTypeUtils.getFileHeader(bytes));
            System.out.println("");
        }

    }

}
