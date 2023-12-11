package com.hanbong.excel.resource;

import com.hanbong.excel.enums.ExcelRenderLocation;
import java.util.Objects;

public final class ExcelCellKey {

  private final String dataFieldName;
  private final ExcelRenderLocation excelRenderLocation;

  private ExcelCellKey(final String dataFieldName, final ExcelRenderLocation excelRenderLocation) {
    this.dataFieldName = dataFieldName;
    this.excelRenderLocation = excelRenderLocation;
  }

  public static ExcelCellKey of(
      final String fieldName, final ExcelRenderLocation excelRenderLocation) {
    if (excelRenderLocation == null) {
      throw new AssertionError();
    }
    return new ExcelCellKey(fieldName, excelRenderLocation);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final ExcelCellKey that = (ExcelCellKey) o;
    return Objects.equals(dataFieldName, that.dataFieldName)
        && excelRenderLocation == that.excelRenderLocation;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataFieldName, excelRenderLocation);
  }
}
