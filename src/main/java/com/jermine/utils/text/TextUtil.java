package com.jermine.utils.text;

import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * @author jermine
 * @version 1.0
 * @classname TextUtil
 * @description
 * @date 2021/1/13 16:01
 **/
public class TextUtil {

    /**
     * @author jermine
     * @description // 计算文字宽度
     * @date 2021/1/13 16:02
     * @param font
     * @param content
     * @return int
     **/
    public static int calTextWidth(Font font, String content) {
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        return fontMetrics.stringWidth(content);
    }

    public static int calTextHeight(Font font) {
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        return fontMetrics.getHeight();
    }
}