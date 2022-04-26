package demo.service;

import demo.domain.SetPlace;
import demo.mapper.TestMapper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TestService {

    @Autowired
    TestMapper testMapper;

    public boolean inExcel(String path) throws IOException {
        boolean isSuccess = true;

        try {
            List<SetPlace> setPlaceList = new ArrayList<>();

            //xlsx 형식
            //String filePath = "C:/Users/hsyle/Downloads/aa.xlsx";
            InputStream inputStream = new FileInputStream(path);

            //엑셀 로드
            Workbook workbook = WorkbookFactory.create(inputStream);
            //시트 로드 0, 첫번째 시트 로드
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowItr = sheet.iterator();

            //행만큼 반복
            while (rowItr.hasNext()) {
                SetPlace setPlace = new SetPlace();

                Row row = rowItr.next();
                //첫번재 행과 두번째 행 pass
                if (row.getRowNum() == 0) {
                    continue;
                }
                if (row.getRowNum() == 1) {
                    continue;
                }

                Iterator<Cell> cellItr = row.cellIterator();

                while (cellItr.hasNext()) {
                    Cell cell = cellItr.next();
                    int index = cell.getColumnIndex();
                    switch (index) {
                        case 1:
                            setPlace.setTripPlaceName((String) getValueFromCell(cell));
                            break;
                        case 2:
                            setPlace.setTripPlaceAdd0((String) getValueFromCell(cell));
                            break;
                        case 3:
                            setPlace.setTripPlaceAdd1((String) getValueFromCell(cell));
                            break;
                        case 4:
                            setPlace.setTripPlaceAdd2((String) getValueFromCell(cell));
                            break;
                        case 5:
                            setPlace.setTripPlaceAdd3((String) getValueFromCell(cell));
                            break;
                        case 11:
                            setPlace.setTripPlaceText((String) getValueFromCell(cell));
                            break;
                        case 12:
                            setPlace.setTripPlaceLatitude((String) getValueFromCell(cell));
                            break;
                        case 13:
                            setPlace.setTripPlaceLongitude((String) getValueFromCell(cell));
                            break;
                    }
                }
                setPlaceList.add(setPlace);
            }

            System.out.println(setPlaceList);

            List<String> tagList = new ArrayList<>();
            List<String> categoryList = new ArrayList<>();

        //카테고리 저장하기
        //tripService.setCategory(setPlace, tagList);
        //태그 저장하기
        //tripService.setTag(setPlace, categoryList);
        } catch (IOException e) {
            isSuccess = false;
        }

        return isSuccess;
    }

    private Object getValueFromCell(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                DataFormatter formatter = new DataFormatter();
                return formatter.formatCellValue(cell);
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}