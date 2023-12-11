package com.hanbong.excel.resource;

import com.hanbong.excel.style.ExcelCellStyle;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class PreCalculatedCellStyleMap {

  private final DataFormatDecider dataFormatDecider;
  private final Map<ExcelCellKey, CellStyle> cellStyleMap = new HashMap<>();

  public PreCalculatedCellStyleMap(final DataFormatDecider dataFormatDecider) {
    this.dataFormatDecider = dataFormatDecider;
  }

  public void put(
      final Class<?> fieldType,
      final ExcelCellKey excelCellKey,
      final ExcelCellStyle excelCellStyle,
      final Workbook workbook) {
    final CellStyle cellStyle = workbook.createCellStyle();
    final DataFormat dataFormat = workbook.createDataFormat();
    cellStyle.setDataFormat(dataFormatDecider.getDataFormat(dataFormat, fieldType));
    excelCellStyle.apply(cellStyle);
    cellStyleMap.put(excelCellKey, cellStyle);
  }

  public CellStyle get(final ExcelCellKey excelCellKey) {
    return cellStyleMap.get(excelCellKey);
  }

  public boolean isEmpty() {
    return cellStyleMap.isEmpty();
  }
}
