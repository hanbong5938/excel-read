package com.hanbong.excel.resource.factory;


import com.hanbong.excel.annotation.BodyStyle;
import com.hanbong.excel.annotation.ExcelColumn;
import com.hanbong.excel.annotation.ExcelColumnStyle;
import com.hanbong.excel.annotation.HeaderStyle;
import com.hanbong.excel.enums.ExcelRenderLocation;
import com.hanbong.excel.exception.InvalidExcelCellStyleException;
import com.hanbong.excel.exception.NoExcelColumnAnnotationsException;
import com.hanbong.excel.resource.DataFormatDecider;
import com.hanbong.excel.resource.ExcelCellKey;
import com.hanbong.excel.resource.ExcelRenderResource;
import com.hanbong.excel.resource.PreCalculatedCellStyleMap;
import com.hanbong.excel.style.ExcelCellStyle;
import com.hanbong.excel.style.NoExcelCellStyle;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Workbook;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.hanbong.excel.utils.SuperClassReflectionUtils.getAllFields;
import static com.hanbong.excel.utils.SuperClassReflectionUtils.getAnnotation;

public final class ExcelRenderResourceFactory {

    private ExcelRenderResourceFactory() {
    }

    public static ExcelRenderResource prepareRenderResource(
            final Class<?> type, final Workbook workbook, final DataFormatDecider dataFormatDecider) {
        final PreCalculatedCellStyleMap styleMap = new PreCalculatedCellStyleMap(dataFormatDecider);
        final Map<String, String> headerNamesMap = new LinkedHashMap<>();
        final List<String> fieldNames = new ArrayList<>();

        final ExcelColumnStyle classDefinedHeaderStyle = getHeaderExcelColumnStyle(type);
        final ExcelColumnStyle classDefinedBodyStyle = getBodyExcelColumnStyle(type);

        for (final Field field : getAllFields(type)) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                final ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                styleMap.put(
                        String.class,
                        ExcelCellKey.of(field.getName(), ExcelRenderLocation.HEADER),
                        getCellStyle(
                                decideAppliedStyleAnnotation(classDefinedHeaderStyle, annotation.headerStyle())),
                        workbook);
                final Class<?> fieldType = field.getType();
                styleMap.put(
                        fieldType,
                        ExcelCellKey.of(field.getName(), ExcelRenderLocation.BODY),
                        getCellStyle(
                                decideAppliedStyleAnnotation(classDefinedBodyStyle, annotation.bodyStyle())),
                        workbook);
                fieldNames.add(field.getName());
                headerNamesMap.put(field.getName(), annotation.headerName());
            }
        }

        if (styleMap.isEmpty()) {
            throw new NoExcelColumnAnnotationsException(
                    String.format("Class %s has not @ExcelColumn at all", type));
        }
        return new ExcelRenderResource(styleMap, headerNamesMap, fieldNames);
    }

    private static ExcelColumnStyle getHeaderExcelColumnStyle(Class<?> clazz) {
        final Annotation annotation = getAnnotation(clazz, HeaderStyle.class);
        if (annotation == null) {
            return null;
        }
        return ((HeaderStyle) annotation).style();
    }

    private static ExcelColumnStyle getBodyExcelColumnStyle(Class<?> clazz) {
        final Annotation annotation = getAnnotation(clazz, BodyStyle.class);
        if (annotation == null) {
            return null;
        }
        return ((BodyStyle) annotation).style();
    }

    private static ExcelColumnStyle decideAppliedStyleAnnotation(
            final ExcelColumnStyle classAnnotation, ExcelColumnStyle fieldAnnotation) {
        if (fieldAnnotation.excelCellStyleClass().equals(NoExcelCellStyle.class)
                && classAnnotation != null) {
            return classAnnotation;
        }
        return fieldAnnotation;
    }

    private static ExcelCellStyle getCellStyle(final ExcelColumnStyle excelColumnStyle) {
        final Class<? extends ExcelCellStyle> excelCellStyleClass =
                excelColumnStyle.excelCellStyleClass();
        if (excelCellStyleClass.isEnum()) {
            return findExcelCellStyle(excelCellStyleClass, excelColumnStyle.enumName());
        }

        try {
            return excelCellStyleClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException
                 | InvocationTargetException e) {
            throw new InvalidExcelCellStyleException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static ExcelCellStyle findExcelCellStyle(
            final Class<?> excelCellStyles, final String enumName) {
        try {
            return (ExcelCellStyle) Enum.valueOf((Class<? extends Enum>) excelCellStyles, enumName);
        } catch (NullPointerException e) {
            throw new InvalidExcelCellStyleException("enumName must not be null", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidExcelCellStyleException(
                    String.format("Enum %s does not name %s", excelCellStyles.getName(), enumName), e);
        }
    }
}
