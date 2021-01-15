package com.jermine.utils.file.image;

import com.jermine.utils.file.FileUtil;
import com.jermine.utils.file.text.FileTextUtil;
import com.jermine.utils.model.WaterMarkText;
import com.jermine.utils.text.TextUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

/**
 * @author jermine
 * @version 1.0
 * @classname WaterMarkUtil
 * @description
 * @date 2021/1/13 14:38
 **/
public class WaterMarkUtil {
    public static final String savePath = "D:/image/watermark/";

    /**
     * @param sourceImgPath 输入图片路径 - 输入图片的路径
     * @param targetImgPath 输出图片路径 - 保存图片的位置
     * @param content       文字内容
     * @param fontName      字体名称
     * @param fontStyle     字体风格
     * @param fontSize      字体大小
     * @param x             偏移量
     * @param y             偏移量
     * @param alpha         透明度
     * @param degree        角度
     * @param color         文字颜色
     * @return void
     * @author jermine
     * @description // 该方法是在图片指定位置加文字水印
     * @date 2021/1/13 14:57
     **/
    public static void markTextByLocation(String sourceImgPath,
                                          String targetImgPath,
                                          String content,
                                          String fontName,
                                          int fontStyle,
                                          int fontSize,
                                          int x,
                                          int y,
                                          float alpha,
                                          int degree,
                                          Color color) {
        try {
            if (StringUtils.isEmpty(targetImgPath)) {
                targetImgPath = savePath;
            }
            // 指定默认字体名称，风格，大小，透明度，颜色，位置
            WaterMarkText waterMarkText = new WaterMarkText();
            waterMarkText = WaterMarkText.builder()
                    .color(color == null ? waterMarkText.getColor() : color)
                    .fontSize(fontSize == 0 ? waterMarkText.getFontSize() : fontSize)
                    .fontStyle(fontStyle == 0 ? waterMarkText.getFontStyle() : fontStyle)
                    .content(StringUtils.isEmpty(content) ? "" : content)
                    .fontName(StringUtils.isEmpty(fontName) ? waterMarkText.getFontName() : fontName)
                    .alpha(new BigDecimal(alpha).equals(new BigDecimal(0)) ? waterMarkText.getAlpha() : alpha)
                    .build();
            // 判断本地路径还是网络路径
            boolean isRemote = FileTextUtil.isRemotePath(sourceImgPath);
            File file = null;
            if (isRemote) {
                file = FileUtil.saveFileFromLink(sourceImgPath, null, targetImgPath);
            } else {
                file = new File(sourceImgPath);
            }
            Image src = ImageIO.read(file);
            int height = src.getHeight(null);
            int width = src.getWidth(null);
            if (x > width || y > height) {
                throw new RuntimeException("输入坐标非法，请检查是否在合理范围之内！the value of 'x' or 'y' is illegal, check it and make sure they are effective!");
            }
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            g.setColor(color);
            Font font = new Font(waterMarkText.getFontName(), waterMarkText.getFontStyle(), waterMarkText.getFontSize());
            int textHeight = TextUtil.calTextHeight(font);
            y += textHeight;
            g.setFont(font);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.rotate(Math.toRadians(degree), x, y);
            g.drawString(waterMarkText.getContent(), x, y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(file.getPath());
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}