package com.hanbong.excel.style.align;

import org.apache.poi.ss.usermodel.CellStyle;

public class NoExcelAlign implements ExcelAlign {

  @Override
  public void apply(final CellStyle cellStyle) {
    // Empty
  }
}
