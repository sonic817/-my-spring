package demo.service;

import com.amazonaws.AmazonServiceException;
import demo.domain.SetPlace;
import demo.domain.Tag;
import demo.mapper.TestMapper;
import net.halowd.saveImg.SaveImg;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestService {

    @Autowired
    TestMapper testMapper;
    @Autowired
    AwsS3Service awsS3Service;

    public boolean inExcel(String path) throws IOException {
        boolean isSuccess = true;

        try {
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
                List<String> tagList = new ArrayList<>();
                List<String> categoryList = new ArrayList<>();

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
                        //name
                        case 1:
                            setPlace.setTripPlaceName((String) getValueFromCell(cell));
                            break;

                        //add
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

                        //tag
                        case 6:
                        case 7:
                        case 8:
                            tagList.add((String) getValueFromCell(cell));
                            break;

                        //category
                        case 9:
                        case 10:
                            categoryList.add((String) getValueFromCell(cell));
                            break;

                        //text
                        case 11:
                            setPlace.setTripPlaceText((String) getValueFromCell(cell));
                            break;

                        //position
                        case 12:
                            setPlace.setTripPlaceLatitude((String) getValueFromCell(cell));
                            break;
                        case 13:
                            setPlace.setTripPlaceLongitude((String) getValueFromCell(cell));
                            break;
                    }
                }

                setPlace.setUserCodeNo(10406);

                String yyyyMMdd = yyyyMMdd(setPlace.getUserCodeNo());
                setPlace.setTripPlaceCode(yyyyMMdd);

                InsertDB(setPlace);

                setCategory(setPlace, categoryList);
                setTag(setPlace, tagList);
            }

        } catch (IOException e) {
            isSuccess = false;
        }

        return isSuccess;
    }

    public void setCategory(SetPlace place, List<String> categoryList) {
        List<Integer> categoryNoList = new ArrayList<>();
        categoryNoList = testMapper.getCategoryNoList(categoryList);

        for (int i = 0; i < categoryNoList.size(); i++) {
            Integer result = testMapper.insertCategory(place.getTripPlaceNo(), categoryNoList.get(i));
        }
    }

    public void setTag(SetPlace place, List<String> placeTag) {
        if (placeTag != null) {
            if (placeTag.size() > 0) {
                for (int i = 0; i < placeTag.size(); i++) {
                    String tagName = placeTag.get(i);
                    Tag tag = new Tag();
                    tag = testMapper.getTag(tagName);

                    if (tag != null) {
                        Integer resultTripCourseTag = testMapper.insertPlaceTag(place.getTripPlaceNo(), tag.getTagNo());
                    } else {
                        tag = new Tag();
                        tag.setTagName(tagName);
                        Integer resultCount = testMapper.insertTag(tag);
                        Integer resultTripCourseTag = testMapper.insertPlaceTag(place.getTripPlaceNo(), tag.getTagNo());
                    }
                }
            }
        }
    }

    private void InsertDB(SetPlace setPlace) {
        Integer tripPlaceNo = testMapper.insertTripPlace(setPlace);
    }

    private String yyyyMMdd(Integer userCodeNo) {
        String code = "";
        String getCode = "";
        String today = getDate();
        today = today.substring(2);

        getCode = testMapper.getNewCode(userCodeNo, today);

        if (getCode == null || getCode.isEmpty()) {
            code = today + "_" + "0001";
        } else {
            String[] newCodeSplit = getCode.split("_");
            Integer seq = Integer.parseInt(newCodeSplit[1]) + 1;
            code = getCode.replace(newCodeSplit[1], String.format("%04d", seq));
        }

        return code;
    }

    public String getDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String today = sdf.format(date);
        return today;
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

    public JSONObject setImg() throws IOException {
        //폴더만들기
        String folderName = "user/" + "10406" + "/" + "test" + "/" + "20220427" + "/";

        JSONObject jSONOResponse = new JSONObject();

        String imgPath = "https://en.pimg.jp/045/032/301/1/45032301.jpg";

        URL url = new URL(imgPath);
        BufferedImage bi = ImageIO.read(url);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", bos);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

        //저장하기
        try {
            awsS3Service.uploadFiles(bis, folderName);
            jSONOResponse.put("result", "true");
            jSONOResponse.put("message", "성공");
        } catch (AmazonServiceException e) {
            jSONOResponse.put("result", "false");
            jSONOResponse.put("message", e.getMessage());
        }

        return jSONOResponse;
    }
}