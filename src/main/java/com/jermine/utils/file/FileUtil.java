package com.jermine.utils.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author jermine
 * @version 1.0
 * @classname FileUtil
 * @description
 * @date 2021/1/13 16:13
 **/
public class FileUtil {

    public static File saveFileFromLink(String link, String fileName, String savePath) {
        try {
            URL url = new URL(link);
            File dir = new File(savePath);
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
                if (!flag) {
                    return null;
                }
            }
            // 获取源文件名称
            if (StringUtils.isEmpty(fileName)) {
                fileName = getFileNameFromLink(link);
            }
            File file = new File(savePath + fileName);
            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 4];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
            inputStream.close();
            outputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从url获取文件扩展名
     * @param link
     * @return
     */
    public static String getExtensionFromLink(String link) throws Exception {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();;
        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        String contentType = HttpURLConnection.guessContentTypeFromStream(bis);
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = mimeTypes.forName(contentType);
        connection.disconnect();
        bis.close();
        return mimeType.getExtension();
    }

    /**
     * 通过url获取文件名
     * @param link
     * @return
     */
    public static String getFileNameFromLink(String link) throws Exception{
        String fileName = null;
        URL url = new URL(link);
        System.out.println(url.getFile());
        boolean flag = false;
        URLConnection connection = url.openConnection();
        if (connection == null) {
            return null;
        }
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        if (headerFields == null) {
            return null;
        }
        Set<String> keySet = headerFields.keySet();
        for (String key : keySet) {
            List<String> values = headerFields.get(key);
            for (String value : values) {
                String result = null;
                result = new String(value.getBytes("ISO-8859-1"), "GBK");
                int index = result.indexOf("filename");
                if (index >= 0) {
                    result = result.substring(index + "filename".length());
                    fileName = result.substring(result.indexOf("=") + 1);
                    flag = true;
                }
            }
            if (flag) {
                break;
            }
        }
        if (!StringUtils.isEmpty(fileName)) {
            return fileName;
        }
        String extension = getExtensionFromLink(link);
        int index = link.lastIndexOf("/");
        if (index == -1) {
            index = link.lastIndexOf("\\");
        }
        fileName = link.substring(index);
        if (fileName.endsWith(extension)) {
            return fileName;
        } else {
            // 随机文件名 - 拿不到
            String uuid = UUID.randomUUID().toString();
            fileName = uuid + extension;
        }
        return fileName;
    }
}