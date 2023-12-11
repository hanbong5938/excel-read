package com.hanbong.excel.style.color;

import org.apache.poi.ss.usermodel.CellStyle;

public class NoExcelColor implements ExcelColor {

  @Override
  public void apply(final CellStyle cellStyle) {
    // Nothing
  }
}
