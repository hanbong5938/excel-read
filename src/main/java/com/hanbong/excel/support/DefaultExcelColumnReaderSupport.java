package com.hanbong.excel.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.IntStream;

import com.hanbong.excel.enums.ExcelInfo;
import com.hanbong.excel.exception.ExcelException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import static com.hanbong.excel.utils.SuperClassReflectionUtils.getConstructor;

public class DefaultExcelColumnReaderSupport<T> extends ExcelColumReaderSupport<T> {

  @Override
  public List<T> getColumData(final Sheet sheet, final Class<T> clazz, final ExcelInfo excelInfo) {
    return getDataList(sheet, clazz, excelInfo);
  }

  private List<T> getDataList(final Sheet sheet, final Class<T> clazz, final ExcelInfo excelInfo) {
    final List<T> dataList = new ArrayList<>();
    int index = excelInfo.getStartRow();
    Row row;
    while ((row = sheet.getRow(++index)) != null) {
      dataList.add(getData(row, clazz, excelInfo));
    }
    return dataList;
  }

  private T getData(final Row row, final Class<T> clazz, final ExcelInfo excelInfo) {
    try {
      final Constructor<T> constructor = getConstructor(clazz);
      return constructor.newInstance(
          IntStream.range(excelInfo.getStartCell(), row.getLastCellNum())
              .mapToObj(i -> getCellValue(row.getCell(i)))
              .toArray());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new ExcelException(e.getMessage(), e);
    }
  }
}
