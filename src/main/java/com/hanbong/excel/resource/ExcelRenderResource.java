package com.hanbong.excel.resource;

import com.hanbong.excel.enums.ExcelRenderLocation;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelRenderResource {

  private final PreCalculatedCellStyleMap styleMap;

  private final Map<String, String> excelHeaderNames;
  private final List<String> dataFieldNames;

  public ExcelRenderResource(
      final PreCalculatedCellStyleMap styleMap,
      final Map<String, String> excelHeaderNames,
      final List<String> dataFieldNames) {
    this.styleMap = styleMap;
    this.excelHeaderNames = excelHeaderNames;
    this.dataFieldNames = dataFieldNames;
  }

  public CellStyle getCellStyle(
      final String dataFieldName, final ExcelRenderLocation excelRenderLocation) {
    return styleMap.get(ExcelCellKey.of(dataFieldName, excelRenderLocation));
  }

  public String getExcelHeaderName(final String dataFieldName) {
    return excelHeaderNames.get(dataFieldName);
  }

  public List<String> getDataFieldNames() {
    return dataFieldNames;
  }
}
