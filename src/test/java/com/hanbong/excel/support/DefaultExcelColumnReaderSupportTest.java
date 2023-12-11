package com.hanbong.excel.support;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.hanbong.excel.enums.DefaultExcelInfo;
import com.hanbong.excel.model.ExcelReadModel;
import org.junit.jupiter.api.Test;

class DefaultExcelColumnReaderSupportTest {
    private final ExcelWorkBookReaderSupport workbookReadSupport =
            new DefaultExcelWorkbookReaderSupport();
    private final ExcelSheetReaderSupport sheetReadSupport = new DefaultExcelSheetReaderSupport();
    private final ExcelColumReaderSupport<ExcelReadModel> columnReadSupport =
            new DefaultExcelColumnReaderSupport<>();

    @Test
    void getColumData() {

        var inputStream = getClass().getClassLoader().getResourceAsStream("example.xlsx");

        var workbook = workbookReadSupport.getWorkBook(inputStream);

        var sheet = sheetReadSupport.getSheet(workbook, DefaultExcelInfo.DEFAULT);

        var data = columnReadSupport.getColumData(sheet, ExcelReadModel.class, DefaultExcelInfo.DEFAULT);

        assertFalse(data != null);
    }
}
