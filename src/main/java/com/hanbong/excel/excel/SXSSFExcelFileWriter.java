package com.hanbong.excel.excel;

import static com.hanbong.excel.utils.SuperClassReflectionUtils.getField;

import com.hanbong.excel.enums.ExcelRenderLocation;
import com.hanbong.excel.exception.ExcelInternalException;
import com.hanbong.excel.resource.DataFormatDecider;
import com.hanbong.excel.resource.DefaultDataFormatDecider;
import com.hanbong.excel.resource.ExcelRenderResource;
import com.hanbong.excel.resource.factory.ExcelRenderResourceFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public abstract class SXSSFExcelFileWriter<T> implements ExcelFileWriter {

  protected SXSSFWorkbook workbook;
  protected Sheet sheet;
  protected ExcelRenderResource resource;

  protected SXSSFExcelFileWriter(final Class<T> type) {
    this(Collections.emptyList(), type, new DefaultDataFormatDecider());
  }

  protected SXSSFExcelFileWriter(final List<T> data, final Class<T> type) {
    this(data, type, new DefaultDataFormatDecider());
  }

  protected SXSSFExcelFileWriter(
      final List<T> data, final Class<T> type, final DataFormatDecider dataFormatDecider) {
    validateData(data);
    this.workbook = new SXSSFWorkbook();
    this.resource =
        ExcelRenderResourceFactory.prepareRenderResource(type, workbook, dataFormatDecider);
    renderExcel(data);
  }

  protected void validateData(List<T> data) {}

  protected abstract void renderExcel(List<T> data);

  protected void renderHeadersWithNewSheet(Sheet sheet, int rowIndex, int columnStartIndex) {
    Row row = sheet.createRow(rowIndex);
    int columnIndex = columnStartIndex;
    for (String dataFieldName : resource.getDataFieldNames()) {
      Cell cell = row.createCell(columnIndex++);
      cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.HEADER));
      cell.setCellValue(resource.getExcelHeaderName(dataFieldName));
    }
  }

  protected void renderBody(final Object data, final int rowIndex, final int columnStartIndex) {
    final Row row = sheet.createRow(rowIndex);
    int columnIndex = columnStartIndex;
    for (String dataFieldName : resource.getDataFieldNames()) {
      final Cell cell = row.createCell(columnIndex++);
      try {
        final Field field = getField(data.getClass(), (dataFieldName));
        cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.BODY));
        final Object cellValue = field.get(data);
        renderCellValue(cell, cellValue);
      } catch (Exception e) {
        throw new ExcelInternalException(e.getMessage(), e);
      }
    }
  }

  protected void renderCellValue(final Cell cell, final Object cellValue) {
    if (cellValue instanceof final Number numberValue) {
      cell.setCellValue(numberValue.doubleValue());
      return;
    }
    cell.setCellValue(cellValue == null ? "" : cellValue.toString());
  }

  public void write(OutputStream stream) throws IOException {
    workbook.write(stream);
    workbook.close();
    workbook.dispose();
    stream.close();
  }

  public abstract void addRows(List<T> data);

  public abstract void addColumns(List<Object> data, int rowIndex, int columnIndex);
}
