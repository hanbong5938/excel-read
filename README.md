## 엑셀 파일 데이터 읽어 오는 모듈

### DTO 
객체를 생성할때 Java Reflection 을 사용하여 접근하기에 AllArgsConstructor 필요
```java
// poi 에서 NUMERIC 셀을 소수점으로 처리하기에 Double 사용 필요
public record ExcelReadModel(
        String name,
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
