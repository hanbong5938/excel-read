package com.hanbong.excel.style;

import com.hanbong.excel.style.config.ExcelCellStyleConfig;
import org.apache.poi.ss.usermodel.CellStyle;

public abstract class CustomExcelCellStyle implements ExcelCellStyle {

  private final ExcelCellStyleConfig config = new ExcelCellStyleConfig();

  protected CustomExcelCellStyle() {
    configure(config);
  }

  public abstract void configure(final ExcelCellStyleConfig config);

  @Override
  public void apply(final CellStyle cellStyle) {
    config.configure(cellStyle);
  }
}
