package com.jermine.utils.file.image;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class WaterMarkUtilTest {

    @Test
    public void markTextByLocation() {
        WaterMarkUtil.markTextByLocation("https://pics2.baidu.com/feed/b3b7d0a20cf431ad7ad12a3b649a9aa92fdd98e7.jpeg?token=03df0b574b7413f342ee95b4371829ee&s=bab64f801eb27e945c30d484030050b9",null, "山山 - 十三先生", null, 0, 45, 0, 0, 0.45f, 0, Color.RED);
    }
}