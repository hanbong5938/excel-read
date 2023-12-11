package com.hanbong.excel.excel;

import com.hanbong.excel.support.DefaultExcelColumnReaderSupport;
import com.hanbong.excel.support.DefaultExcelSheetReaderSupport;
import com.hanbong.excel.support.DefaultExcelWorkbookReaderSupport;
import com.hanbong.excel.support.ExcelColumReaderSupport;
import com.hanbong.excel.support.ExcelSheetReaderSupport;
import com.hanbong.excel.support.ExcelWorkBookReaderSupport;

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
