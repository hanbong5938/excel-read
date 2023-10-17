package com.hanbong.excel.support;

import com.hanbong.excel.enums.ExcelInfo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DefaultExcelSheetReaderSupport extends ExcelSheetReaderSupport {
    public Sheet getSheet(final Workbook workbook, final ExcelInfo excelInfo) {
        if (excelInfo == null  || excelInfo.getSheetName() == null) {
            return getFirstSheet(workbook);
        }
        final Sheet sheetByName = workbook.getSheet(excelInfo.getSheetName());
        return sheetByName != null ? sheetByName : getFirstSheet(workbook);
    }

}
