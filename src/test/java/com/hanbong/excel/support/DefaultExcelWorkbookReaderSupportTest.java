package com.hanbong.excel.support;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DefaultExcelWorkbookReaderSupportTest {

  private final ExcelWorkBookReaderSupport excelWorkbookReaderSupport =
      new DefaultExcelWorkbookReaderSupport();

    @Test
    void getFile() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("example.xlsx");

    var workbook = excelWorkbookReaderSupport.getWorkBook(inputStream);

        assertNotNull(workbook);
    }
}
