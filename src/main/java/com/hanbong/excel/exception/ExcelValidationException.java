package com.hanbong.excel.exception;

import com.hanbong.excel.exception.error.ExcelValidationErrorType;

public class ExcelValidationException extends ExcelException {
  public ExcelValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ExcelValidationException(final ExcelValidationErrorType type, final String... columns) {
    final StringBuilder column = new StringBuilder("(");
    for (String s : columns) {
      column.append(s).append(",");
    }
    column.setCharAt(columns.length, ')');
    throw new ExcelException(column.toString());
  }
}
