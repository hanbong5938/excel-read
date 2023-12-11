package com.hanbong.excel.excel;

import static com.hanbong.excel.constants.ExcelGeneratorConstants.COLUMN_START_INDEX;
import static com.hanbong.excel.constants.ExcelGeneratorConstants.ROW_START_INDEX;

import com.hanbong.excel.constants.ExcelGeneratorConstants;
import com.hanbong.excel.resource.DataFormatDecider;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

public class DefaultExeclFileWriter<T> extends SXSSFExcelFileWriter<T> {
  private int currentRowIndex = ROW_START_INDEX;

  public DefaultExeclFileWriter(Class<T> type) {
    super(type);
  }

  public DefaultExeclFileWriter(List<T> data, Class<T> type) {
    super(data, type);
  }

  public DefaultExeclFileWriter(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider) {
    super(data, type, dataFormatDecider);
  }

  @Override
  protected void validateData(List<T> data) {
    int maxRows = ExcelGeneratorConstants.SUPPLY_EXCEL_VERSION.getMaxRows();
    if (data.size() > maxRows) {
      throw new IllegalArgumentException(
          String.format("This concrete ExcelFile does not support over %s rows", maxRows));
    }
  }

  @Override
  public void renderExcel(List<T> data) {
    sheet = workbook.createSheet();
    renderHeadersWithNewSheet(sheet, currentRowIndex++, COLUMN_START_INDEX);

    if (data.isEmpty()) {
      return;
    }

    for (Object renderedData : data) {
      renderBody(renderedData, currentRowIndex++, COLUMN_START_INDEX);
    }
  }

  @Override
  public void addRows(List<T> data) {
    data.forEach(a -> renderBody(a, ++currentRowIndex, COLUMN_START_INDEX));
  }

  @Override
  public void addColumns(List<Object> data, int rowIndex, int columnIndex) {
    final Row row = sheet.getRow(rowIndex);
    for (Object object : data) {
      final Cell cell = row.createCell(columnIndex++);
      renderCellValue(cell, object);
    }
  }

  public void addColumns(List<Object> data, int rowIndex) {
    final Row row = sheet.getRow(rowIndex);
    int lastCellNum = row.getLastCellNum();
    for (Object object : data) {
      final Cell cell = row.createCell(lastCellNum++);
      renderCellValue(cell, object);
    }
  }

  public void mergeRegion(int firstRow, int lastRow, int firstCol, int lastCol) {
    this.sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
  }
}
