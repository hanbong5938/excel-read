package com.hanbong.excel.support;

import static com.hanbong.excel.utils.SuperClassReflectionUtils.getConstructor;

import com.hanbong.excel.enums.ExcelInfo;
import com.hanbong.excel.exception.ExcelException;
import com.hanbong.excel.exception.model.ExcelValidationError;
import com.hanbong.excel.model.DefaulExcelReadResult;
import com.hanbong.excel.model.ExcelReadResult;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DefaultExcelColumnReaderSupport<T> extends ExcelColumReaderSupport<T> {

  @Override
  public ExcelReadResult<T> getColumData(
      final Sheet sheet, final Class<T> clazz, final ExcelInfo excelInfo) {
    return getDataList(sheet, clazz, excelInfo);
  }

  private ExcelReadResult<T> getDataList(
      final Sheet sheet, final Class<T> clazz, final ExcelInfo excelInfo) {
    final List<T> dataList = new ArrayList<>();
    final List<ExcelValidationError> localErrors = new ArrayList<>();

    int index = excelInfo.getStartRow();
    Row row;
    while ((row = sheet.getRow(++index)) != null) {
      final T data = getData(row, clazz, excelInfo, localErrors);
      if (data != null) dataList.add(data);
    }

    return new DefaulExcelReadResult<>(dataList, localErrors);
  }

  private T getData(
      final Row row,
      final Class<T> clazz,
      final ExcelInfo excelInfo,
      final List<ExcelValidationError> localErrors) {

    try {
      final Constructor<T> constructor = getConstructor(clazz);
      final int startCell = excelInfo.getStartCell();
      final int totalCells = row.getLastCellNum();
      final Class<?>[] parameterTypes = constructor.getParameterTypes();
      final Object[] values = new Object[constructor.getParameterCount()];

      boolean isLastParamVararg =
          parameterTypes.length > 0 && parameterTypes[parameterTypes.length - 1].isArray();
      fillConstructorValues(row, values, startCell, totalCells, isLastParamVararg);

      return constructor.newInstance(values);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new ExcelException("Error constructing object: " + e.getMessage(), e);
    }
  }

  private void fillConstructorValues(
      final Row row,
      final Object[] values,
      final int startCell,
      final int totalCells,
      final boolean isLastParamVararg) {
    if (isLastParamVararg) {
      final int nonVarargParameterCount = values.length - 1;
      for (int i = 0; i < nonVarargParameterCount; i++) {
        values[i] = getCellValue(row.getCell(i + startCell));
      }
      final List<String> varargs = new ArrayList<>();
      for (int i = nonVarargParameterCount + startCell; i < totalCells; i++) {
        varargs.add((String) getCellValue(row.getCell(i)));
      }
      values[nonVarargParameterCount] = varargs.toArray(new String[0]);
    } else {
      for (int i = 0; i < values.length; i++) {
        values[i] = getCellValue(row.getCell(i + startCell));
      }
    }
  }
}
