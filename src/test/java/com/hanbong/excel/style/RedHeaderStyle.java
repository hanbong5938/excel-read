package com.hanbong.excel.style;

import com.hanbong.excel.style.align.DefaultExcelAlign;
import com.hanbong.excel.style.border.DefaultExcelBorder;
import com.hanbong.excel.style.border.ExcelBorderStyle;
import com.hanbong.excel.style.config.ExcelCellStyleConfig;

public class RedHeaderStyle extends CustomExcelCellStyle {

    @Override
    public void configure(final ExcelCellStyleConfig config) {
        config
                .foregroundColor(255, 0, 0)
                .excelBorder(DefaultExcelBorder.newInstance(ExcelBorderStyle.THIN))
                .excelAlign(DefaultExcelAlign.CENTER_CENTER);
    }
}
