package com.jermine.utils.file.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jermine
 * @version 1.0
 * @classname FileTextUtil
 * @description
 * @date 2021/1/13 16:07
 **/
public class FileTextUtil {

    public static boolean isRemotePath(String path) {
        String regex = "^http.*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }
}