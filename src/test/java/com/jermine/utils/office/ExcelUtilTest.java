package com.jermine.utils.office;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.jermine.utils.model.Website;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;

public class ExcelUtilTest {
    @Test
    public void test() {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("“白皮书发布”宣传渠道清单.xlsx"));
        List<Website> all = reader.readAll(Website.class);
        for (Website website : all) {
            System.out.println(website);
        }
    }
}