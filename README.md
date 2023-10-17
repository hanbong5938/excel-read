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
