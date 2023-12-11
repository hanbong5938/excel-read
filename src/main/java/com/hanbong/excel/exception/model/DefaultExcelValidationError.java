package com.hanbong.excel.exception.model;

import org.apache.poi.ss.util.CellReference;

public class DefaultExcelValidationError implements ExcelValidationError {
  private final int row;
  private final int column;
  private final String message;

  public DefaultExcelValidationError(final int row, final int column, final String message) {
    this.row = row;
    this.column = column;
    this.message = message;
  }

  @Override
  public String toString() {
    return message + "(" + row + CellReference.convertNumToColString(column) + ")";
  }
}
