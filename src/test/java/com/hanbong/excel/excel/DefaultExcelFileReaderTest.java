package com.hanbong.excel.excel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hanbong.excel.enums.DefaultExcelInfo;
import com.hanbong.excel.model.ExcelReadModel;
import org.junit.jupiter.api.Test;

class DefaultExcelFileReaderTest {

  private final ExcelFileReader<ExcelReadModel> excelFileReader = new DefaultExcelFileReader<>();

  @Test
  void getData() {
    var inputStream = getClass().getClassLoader().getResourceAsStream("example.xlsx");

    var excel = excelFileReader.getData(inputStream, ExcelReadModel.class);

    assertNotNull(excel);
  }

  @Test
  void getData_withConfig() {
    var inputStream = getClass().getClassLoader().getResourceAsStream("example.xlsx");

    var excel = excelFileReader.getData(inputStream, ExcelReadModel.class, DefaultExcelInfo.DEFAULT);

    assertNotNull(excel);
  }
}
