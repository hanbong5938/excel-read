# 엑셀 파일 데이터 읽어 오거나 엑셀 출력하는 라이브러리

## 데이터 읽어오는 경우

### DTO 
객체를 생성할때 Java Reflection 을 사용하여 접근하기에 AllArgsConstructor 필요
```java
// poi 에서 NUMERIC 셀을 소수점으로 처리하기에 Double 사용 필요
// 컬럼에 대한 밸리데이션이 필요한 경우 아래와 같이 어노테이션으로 처리 가능하다.
public record ExcelReadModel(
         @ExcelValidation(regex = "^[a-zA-Z]+$", nullAble = false, enableBlank = false) String name,
        String phone,
        String mail,
        Double num){
}
```


기본적으로 `DefaultExcelFileReader` 를 사용하나 커스텀시 `SXSSFExcelFileReader` 를 상속하여 사용
```java
public class DefaultExcelFileReader<T> extends SXSSFExcelFileReader<T> {

  @Override
  public List<T> getData(final InputStream inputStream, final Class<T> clazz) {
    return this.getData(inputStream,clazz, DefaultExcelInfo.DEFAULT);
  }

  @Override
  public List<T> getData(final InputStream inputStream, final Class<T> clazz,
                                           final ExcelInfo excelInfo) {
    final Workbook workbook = excelWorkbookReaderSupport.getWorkBook(inputStream);
    final Sheet sheet = excelSheetReaderSupport.getSheet(workbook, excelInfo);
    return excelColumReaderSupport.getColumData(sheet, clazz, excelInfo);
  }
}
```


아래 처럼 `ExcelInfo` 를 구현하여 객체를 사용하면 row나 column 의 지삭 위치 혹은 sheet 이름 지정 가능

sheet 이름 null 인 경우 1번째 시트 선택

```java
public enum DefaultExcelInfo implements ExcelInfo{

    DEFAULT(null, ROW_START_INDEX + 1, COLUMN_START_INDEX);

    private final String name;

    private final int startRow;

    private final int startCell;

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

```

### 사용 방법

InputStream 을 불러와서 아래와 같이 사용하면 `List<T>` 를 리턴

기본적으로 `DefaultExcelInfo.DEFAULT` 의 설정을 사용하도록 되어있다.

```java
class DefaultExcelFileReaderTest {

    private final ExcelFileReader<ExcelReadModel> excelFileReader = new DefaultExcelFileReader<>();

    @Test
    void getData() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("example.xlsx");

        var excel = excelFileReader.getData(inputStream, ExcelReadModel.class);

        assertNotNull(excel);
    }

    @Test
    void getData_withConfig() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("example.xlsx");

        var excel = excelFileReader.getData(inputStream, ExcelReadModel.class, DefaultExcelInfo.DEFAULT);

        assertNotNull(excel);
    }
}
```

## 데이터 출력하는 경우

### DTO
```java
@HeaderStyle(style = @ExcelColumnStyle(excelCellStyleClass = RedHeaderStyle.class))
public class ExcelWriterDto {
  @ExcelColumn(headerName = "하이")
  public String hi;

  @ExcelColumn(headerName = "헤이", headerStyle = @ExcelColumnStyle(excelCellStyleClass = RedHeaderStyle.class))
  public String hey;

  public ExcelWriterDto(final String hey) {
    this.hi = "hi";
    this.hey = hey;
  }
}
```

```java
// 스타일 적용시
public class RedHeaderStyle extends CustomExcelCellStyle {

  @Override
  public void configure(final ExcelCellStyleConfig config) {
    config
        .foregroundColor(255, 0, 0)
        .excelBorder(DefaultExcelBorder.newInstance(ExcelBorderStyle.THIN))
        .excelAlign(DefaultExcelAlign.CENTER_CENTER);
  }
}
```

### 사용 방법

```java
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

```
