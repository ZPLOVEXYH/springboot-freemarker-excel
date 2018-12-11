package com.yuanjun.mybatis;

import com.yuanjun.mybatis.util.ExcelSheet;
import com.yuanjun.mybatis.util.ExcelUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {

	@Test
	public void contextLoads() {
	}

	/**
	 * excel 新建
	 *
	 * @throws IOException
	 */
	@Test
	public void excelTest() throws IOException {
		ExcelUtil excel = new ExcelUtil();
		ExcelSheet sheet = excel.createSheet();
		sheet.row(0).cell(0).cellValue("快递单号");
		sheet.row(1).cell(0).cellValue("232323333");
		sheet.row(2).cell(0).cellValue("232323333");
		sheet.row(3).cell(0).cellValue("232323333");
		sheet.row(4).cell(0).cellValue("232323333");
		excel.saveExcel("d://test1.xls");
	}
}
