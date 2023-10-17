package com.hanbong.excel.support;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.hanbong.excel.enums.DefaultExcelInfo;
import org.junit.jupiter.api.Test;

class DefaultExcelSheetReaderSupportTest {

  private final ExcelWorkBookReaderSupport workbookReadSupport =
      new DefaultExcelWorkbookReaderSupport();
  private final ExcelSheetReaderSupport sheetReadSupport = new DefaultExcelSheetReaderSupport();

    @Test
    void getSheet() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("example.xlsx");

        var workbook = workbookReadSupport.getWorkBook(inputStream);

        var sheet = sheetReadSupport.getSheet(workbook, DefaultExcelInfo.DEFAULT);

        assertNotEquals(-1, sheet.getLastRowNum());
    }
}
