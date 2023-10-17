package com.hanbong.excel.support;


import java.util.List;

import com.hanbong.excel.enums.ExcelInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class ExcelColumReaderSupport<T> implements ExcelReaderSupport {
  protected Object getCellValue(final Cell cell) {
    return switch (cell.getCellType()) {
      case _NONE, ERROR, BLANK -> "";
      case BOOLEAN -> cell.getBooleanCellValue();
      case STRING -> cell.getStringCellValue();
      case NUMERIC -> cell.getNumericCellValue();
      case FORMULA -> cell.getCellFormula();
    };
  }

  public abstract List<T> getColumData(Sheet sheet, Class<T> clazz, ExcelInfo excelInfo);
}
