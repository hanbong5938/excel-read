package com.hanbong.excel.support;


import java.io.InputStream;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class ExcelWorkBookReaderSupport implements ExcelReaderSupport {
  public abstract Workbook getWorkBook(final InputStream inputStream);
}
