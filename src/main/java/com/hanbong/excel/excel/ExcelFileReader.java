package com.hanbong.excel.excel;

import com.hanbong.excel.enums.ExcelInfo;

import java.io.InputStream;
import java.util.List;

public interface ExcelFileReader<T> extends ExcelFile {
    List<T> getData(InputStream inputStream, Class<T> clazz);

    List<T> getData(InputStream inputStream, Class<T> clazz, ExcelInfo excelInfo);
}
