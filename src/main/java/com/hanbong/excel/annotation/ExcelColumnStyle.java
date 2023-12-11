package com.hanbong.excel.annotation;

import com.hanbong.excel.style.ExcelCellStyle;

public @interface ExcelColumnStyle {

  Class<? extends ExcelCellStyle> excelCellStyleClass();

  String enumName() default "";
}
