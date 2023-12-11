package com.hanbong.excel.style.config;

import com.hanbong.excel.style.align.ExcelAlign;
import com.hanbong.excel.style.align.NoExcelAlign;
import com.hanbong.excel.style.border.ExcelBorder;
import com.hanbong.excel.style.border.NoExcelBorder;
import com.hanbong.excel.style.color.DefaultExcelColor;
import com.hanbong.excel.style.color.ExcelColor;
import com.hanbong.excel.style.color.NoExcelColor;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelCellStyleConfig {

  private ExcelAlign excelAlign = new NoExcelAlign();
  private ExcelColor foregroundColor = new NoExcelColor();
  private ExcelBorder excelBorder = new NoExcelBorder();

  public ExcelCellStyleConfig() {
    // Empty
  }

  public ExcelCellStyleConfig excelAlign(ExcelAlign excelAlign) {
    this.excelAlign = excelAlign;
    return this;
  }

  public ExcelCellStyleConfig foregroundColor(int red, int blue, int green) {
    this.foregroundColor = DefaultExcelColor.rgb(red, blue, green);
    return this;
  }

  public ExcelCellStyleConfig excelBorder(ExcelBorder excelBorder) {
    this.excelBorder = excelBorder;
    return this;
  }

  public void configure(CellStyle cellStyle) {
    excelAlign.apply(cellStyle);
    foregroundColor.apply(cellStyle);
    excelBorder.apply(cellStyle);
  }
}
