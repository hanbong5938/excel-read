package com.hanbong.excel.style.color;

import com.hanbong.excel.exception.UnSupportedExcelTypeException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class DefaultExcelColor implements ExcelColor {
  private static final int MIN_RGB = 0;
  private static final int MAX_RGB = 255;

  private final byte red;
  private final byte green;
  private final byte blue;

  private DefaultExcelColor(final byte red, final byte green, final byte blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public static DefaultExcelColor rgb(final int red, final int green, final int blue) {
    if (red < MIN_RGB
        || red > MAX_RGB
        || green < MIN_RGB
        || green > MAX_RGB
        || blue < MIN_RGB
        || blue > MAX_RGB) {
      throw new IllegalArgumentException(String.format("Wrong RGB(%s %s %s)", red, green, blue));
    }
    return new DefaultExcelColor((byte) red, (byte) green, (byte) blue);
  }

  @Override
  public void apply(final CellStyle cellStyle) {
    try {
      XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
      xssfCellStyle.setFillForegroundColor(
          new XSSFColor(new byte[] {red, green, blue}, new DefaultIndexedColorMap()));
    } catch (Exception e) {
      throw new UnSupportedExcelTypeException(
          String.format("Excel Type %s is not supported now", cellStyle.getClass()));
    }
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  }
}
