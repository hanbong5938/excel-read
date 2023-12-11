package com.hanbong.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

public final class DefaultExcelBorder implements ExcelBorder {

  private final ExcelBorderStyle borderStyle;

  public DefaultExcelBorder(final ExcelBorderStyle borderStyle) {
    this.borderStyle = borderStyle;
  }

  public static DefaultExcelBorder newInstance(final ExcelBorderStyle borderStyle) {
    return new DefaultExcelBorder(borderStyle);
  }

  @Override
  public void applyTop(final CellStyle cellStyle) {
    cellStyle.setBorderTop(borderStyle.getStyle());
  }

  @Override
  public void applyRight(final CellStyle cellStyle) {
    cellStyle.setBorderRight(borderStyle.getStyle());
  }

  @Override
  public void applyBottom(final CellStyle cellStyle) {
    cellStyle.setBorderBottom(borderStyle.getStyle());
  }

  @Override
  public void applyLeft(final CellStyle cellStyle) {
    cellStyle.setBorderLeft(borderStyle.getStyle());
  }

  public void apply(final CellStyle cellStyle) {
    applyTop(cellStyle);
    applyBottom(cellStyle);
    applyLeft(cellStyle);
    applyRight(cellStyle);
  }
}
