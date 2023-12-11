package com.hanbong.excel.model;


import com.hanbong.excel.annotation.ExcelColumn;

public class ExcelWriterModel {
  @ExcelColumn(headerName = "하이")
  public String hi;

  @ExcelColumn(headerName = "헤이")
  public String hey;

  public ExcelWriterModel(final String hey) {
    this.hi = "hi";
    this.hey = hey;
  }
}
