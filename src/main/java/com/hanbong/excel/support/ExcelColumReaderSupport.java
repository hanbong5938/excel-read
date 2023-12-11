package com.hanbong.excel.support;

import com.hanbong.excel.enums.ExcelInfo;
import com.hanbong.excel.model.ExcelReadResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class ExcelColumReaderSupport<T> implements ExcelReaderSupport {
  protected Object getCellValue(final Cell cell) {
    return switch (cell.getCellType()) {
      case _NONE, ERROR, BLANK -> "";
      case BOOLEAN -> cell.getBooleanCellValue();
      case STRING -> cell.getStringCellValue();
      case NUMERIC -> getNumericCellValue(cell);
      case FORMULA -> cell.getCellFormula();
    };
  }

  private String getNumericCellValue(final Cell cell) {
    return String.valueOf(cell.getNumericCellValue());
  }

  public abstract ExcelReadResult<T> getColumData(Sheet sheet, Class<T> clazz, ExcelInfo excelInfo);
}
