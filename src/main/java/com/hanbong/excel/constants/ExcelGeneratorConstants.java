package com.hanbong.excel.constants;

import org.apache.poi.ss.SpreadsheetVersion;

public record ExcelGeneratorConstants() {
  public static final int ROW_START_INDEX = 0;
  public static final int COLUMN_START_INDEX = 0;
  public static final SpreadsheetVersion SUPPLY_EXCEL_VERSION = SpreadsheetVersion.EXCEL2007;
}
