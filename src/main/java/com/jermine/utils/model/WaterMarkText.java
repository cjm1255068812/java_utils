package com.jermine.utils.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.awt.*;

/**
 * @author jermine
 * @version 1.0
 * @classname WaterMarkText
 * @description
 * @date 2021/1/13 15:33
 **/
@Data
@Builder
@AllArgsConstructor
public class WaterMarkText {
    /**
     * 水印文字内容
     */
    private String content;
    /**
     * 字体名称
     */
    private String fontName;
    /**
     * 字体类型
     */
    private int fontStyle;
    /**
     * 字体名称
     */
    private int fontSize;
    /**
     * 字体颜色
     */
    private Color color;
    /**
     * 字体位置
     */
    private String location;

    /**
     * 字体角度
     */
    private Integer degree;

    private Float alpha;

    public WaterMarkText() {
        init();
    }

    public void init() {
        location = "right-bottom";
        fontName = "微软雅黑";
        fontStyle = Font.PLAIN;
        fontSize = 60;
        color = Color.BLACK;
        degree = 0;
        alpha = 0.45f;
    }
}