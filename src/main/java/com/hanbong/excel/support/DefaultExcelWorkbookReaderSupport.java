package com.hanbong.excel.support;


import com.hanbong.excel.exception.ExcelInputStreamException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DefaultExcelWorkbookReaderSupport extends ExcelWorkBookReaderSupport {
    public Workbook getWorkBook(final InputStream inputStream) {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
      throw new ExcelInputStreamException(e.getMessage(), e.getCause());
        }
    }
}
