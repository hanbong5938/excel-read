package com.hanbong.excel.enums;

import static com.hanbong.excel.constants.ExcelGeneratorConstants.COLUMN_START_INDEX;
import static com.hanbong.excel.constants.ExcelGeneratorConstants.ROW_START_INDEX;

public enum DefaultExcelInfo implements ExcelInfo{

    DEFAULT(null, ROW_START_INDEX + 1, COLUMN_START_INDEX);

    private String name;

    private int startRow;

    private int startCell;

    DefaultExcelInfo(final String name, final int startRow, final int startCell) {
        this.name = name;
        this.startRow = startRow;
        this.startCell = startCell;
    }

    @Override
    public String getSheetName() {
        return this.name;
    }

    @Override
    public int getStartRow() {
        return this.startRow;
    }

    @Override
    public int getStartCell() {
        return this.startCell;
    }
}
