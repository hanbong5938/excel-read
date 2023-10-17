package com.hanbong.excel.excel;


import com.hanbong.excel.enums.DefaultExcelInfo;
import com.hanbong.excel.enums.ExcelInfo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.List;

public class DefaultExcelFileReader<T> extends SXSSFExcelFileReader<T> {

  @Override
  public List<T> getData(final InputStream inputStream, final Class<T> clazz) {
    return this.getData(inputStream,clazz, DefaultExcelInfo.DEFAULT);
  }

  @Override
  public List<T> getData(final InputStream inputStream, final Class<T> clazz,
                                           final ExcelInfo excelInfo) {
    final Workbook workbook = excelWorkbookReaderSupport.getWorkBook(inputStream);
    final Sheet sheet = excelSheetReaderSupport.getSheet(workbook, excelInfo);
    return excelColumReaderSupport.getColumData(sheet, clazz, excelInfo);
  }
}
