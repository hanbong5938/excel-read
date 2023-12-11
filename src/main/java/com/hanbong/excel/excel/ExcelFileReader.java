package com.hanbong.excel.excel;

import com.hanbong.excel.enums.ExcelInfo;
import com.hanbong.excel.model.ExcelReadResult;
import java.io.InputStream;

public interface ExcelFileReader<T> extends ExcelFile {
  ExcelReadResult<T> getData(InputStream inputStream, Class<T> clazz);

  ExcelReadResult<T> getData(InputStream inputStream, Class<T> clazz, ExcelInfo excelInfo);
}
