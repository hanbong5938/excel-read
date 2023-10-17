package com.hanbong.excel.support;


import com.hanbong.excel.enums.ExcelInfo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class ExcelSheetReaderSupport implements ExcelReaderSupport {
  protected Sheet getFirstSheet(final Workbook workbook) {
    return workbook.getSheetAt(0);
  }

  public abstract Sheet getSheet(final Workbook workbook, final ExcelInfo excelInfo);
}
