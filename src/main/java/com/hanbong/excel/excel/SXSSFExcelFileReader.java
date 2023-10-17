package com.hanbong.excel.excel;

import com.hanbong.excel.support.*;

public abstract class SXSSFExcelFileReader<T> implements ExcelFileReader<T> {
  protected final ExcelWorkBookReaderSupport excelWorkbookReaderSupport;
  protected final ExcelSheetReaderSupport excelSheetReaderSupport;
  protected final ExcelColumReaderSupport<T> excelColumReaderSupport;

  protected SXSSFExcelFileReader(
      final ExcelWorkBookReaderSupport excelWorkbookReaderSupport,
      final ExcelSheetReaderSupport excelSheetReaderSupport,
      final ExcelColumReaderSupport<T> excelColumReaderSupport) {
    this.excelWorkbookReaderSupport = excelWorkbookReaderSupport;
    this.excelSheetReaderSupport = excelSheetReaderSupport;
    this.excelColumReaderSupport = excelColumReaderSupport;
  }

  protected SXSSFExcelFileReader() {
    this(
        new DefaultExcelWorkbookReaderSupport(),
        new DefaultExcelSheetReaderSupport(),
        new DefaultExcelColumnReaderSupport<>());
  }
}
