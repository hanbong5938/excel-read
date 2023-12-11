package com.hanbong.excel.excel;

import com.hanbong.excel.model.ExcelWriterModel;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultExeclFileWriterTest {
    @Test
    void generate() {
        var temp = new ExcelWriterModel("temp");
        final SXSSFExcelFileWriter<ExcelWriterModel> execlFileWriter =
                new DefaultExeclFileWriter<>(List.of(temp), ExcelWriterModel.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            execlFileWriter.addRows(List.of(temp, temp));
            execlFileWriter.sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            execlFileWriter.sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            execlFileWriter.addColumns(
                    List.of("이름", "성함"), 0, execlFileWriter.sheet.getRow(0).getLastCellNum());
            execlFileWriter.addColumns(
                    List.of("이름2", "성함2"), 1, execlFileWriter.sheet.getRow(1).getLastCellNum());
            execlFileWriter.workbook.write(outputStream);
            saveToFile(outputStream, "/data/yourExcelFile.xlsx");
            assertNotNull(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToFile(ByteArrayOutputStream outputStream, String filePath) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            outputStream.writeTo(fileOut);
        }
    }
}
