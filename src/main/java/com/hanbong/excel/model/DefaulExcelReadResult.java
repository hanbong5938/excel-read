package com.hanbong.excel.model;

import com.hanbong.excel.exception.model.ExcelValidationError;
import java.util.List;

public class DefaulExcelReadResult<T> implements ExcelReadResult<T> {
  private List<T> data;
  private List<ExcelValidationError> errors;

  public DefaulExcelReadResult(final List<T> data, final List<ExcelValidationError> errors) {
    this.data = data;
    this.errors = errors;
  }

  public List<T> getData() {
    return data;
  }

  public List<ExcelValidationError> getErrors() {
    return errors;
  }
}
