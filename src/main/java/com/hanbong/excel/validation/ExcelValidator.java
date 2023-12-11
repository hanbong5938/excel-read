package com.hanbong.excel.validation;

import com.hanbong.excel.enums.ExcelInfo;
import com.hanbong.excel.exception.model.ExcelValidationError;
import java.lang.reflect.Field;
import java.util.List;

public interface ExcelValidator {
  void execute(
      final Field field,
      final Object value,
      final ExcelInfo excelInfo,
      final int rowIndex,
      final int colIndex,
      final List<ExcelValidationError> localErrors);
}
