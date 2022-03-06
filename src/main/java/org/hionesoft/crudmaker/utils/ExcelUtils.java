package org.hionesoft.crudmaker.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ExcelUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /** List<Map<String,Object>를 엑셀 파일 데이터변환해준다. */
    public Workbook createExcel(List<Map<String, Object>> dataList, Map<String, String> headerMap, String sheetName) throws IOException {
        return this.createExcel(dataList, headerMap, sheetName, 0, 0);
    }

    /**
     * @dataList : Excel 에 입력할 실 데이터
     * @headerMap : Excel 첫번째 줄에 입력할 header 정보 ex) { table_column_name : 입력하고_싶은_header_name }
     * @sheetName : Excel Sheet명
     * @rowNum : 데이터가 시작하는 row 순번
     * @startCellNum : 데이터가 시작하는 cell 순번
     * */
    public Workbook createExcel(List<Map<String, Object>> dataList, Map<String, String> headerMap, String sheetName, int rowNum, int startCellNum) throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook();   // 성능 개선 버전
        // workbook = new HSSFWorkbook(); // 엑셀 97 ~ 2003
        // workbook = new XSSFWorkbook(); // 엑셀 2007 버전 이상

        SXSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = null;

        // CHECKER : 사용자 요청에 따라 배경색, 폰트 등도 변경 가능

        // 1. Excel Cell Style 설정 Start
        //  (1) Header Cell 스타일
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);   // 가운데 정렬
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 가운데 정렬

        headerStyle.setBorderLeft(BorderStyle.THIN);    // 테두리 좌
        headerStyle.setBorderRight(BorderStyle.THIN);   // 테두리 우
        headerStyle.setBorderTop(BorderStyle.THIN);     // 테두리 위
        headerStyle.setBorderBottom(BorderStyle.THIN);  // 테두리 아래

        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  // 배경색 적용
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);   // 배경색 적용
        headerStyle.setWrapText(true); // value 가 셀의 폭에 들어가지 않는 경우 자동 개행처리

        // (2) Body Cell 스타일
        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setAlignment(HorizontalAlignment.LEFT);   // 가운데 정렬
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 가운데 정렬

        bodyStyle.setBorderLeft(BorderStyle.THIN);    // 테두리 좌
        bodyStyle.setBorderRight(BorderStyle.THIN);   // 테두리 우
        bodyStyle.setBorderTop(BorderStyle.THIN);     // 테두리 위
        bodyStyle.setBorderBottom(BorderStyle.THIN);  // 테두리 아래
        bodyStyle.setWrapText(true); // 자동 개행처리

        DataFormat format = workbook.createDataFormat();

        // 2. header start : excel의 첫번째 열
        int headerCellNum = startCellNum;
        row =  sheet.createRow(rowNum++);

        for(String key : headerMap.keySet()) {
            Cell cell = row.createCell(headerCellNum);
            cell.setCellValue(headerMap.getOrDefault(key, ""));
            cell.setCellStyle(headerStyle);
            headerCellNum++;
        }

        // 3. excel body start
        for(Map<String, Object> map : dataList) {
            row =  sheet.createRow(rowNum++);
            int cellNum = startCellNum;

            for(String key : headerMap.keySet()) {
                Cell cell = row.createCell(cellNum);
                String value = String.valueOf(map.getOrDefault(key, ""));
                cell.setCellStyle(bodyStyle);
                this.setCellTypeAtValueType(value, cell, format);
                cellNum++;
            }
        }

        // 4. column 사이즈 조정
        sheet.trackAllColumnsForAutoSizing();
        for(int i = 0; i < headerMap.size(); i++) {
            sheet.autoSizeColumn(i + 1);
        }

        return workbook;
    }


    /** Cell에 입력하려는 value 의 객체 타입에 맞춰 cell type 설정 후 value 입력
     *  (1) Type에 맞지 않는 value가 올 경우 에러
     *  (2) Cell Type이 String 으로 고정이면 format 등 다양한 기능을 사용할 수 없음
     * */
    public void setCellTypeAtValueType(String value, Cell cell, DataFormat format) {
        if(StringUtils.isNumeric(value)) {
            cell.setCellType(CellType.NUMERIC);
            cell.getCellStyle().setDataFormat(format.getFormat("#,##0"));   // 1000 자릿수마다 콤마
            cell.setCellValue(Double.parseDouble(value));

        } else {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(value);

        }
    }


    /** Excel workbook 객체 -> 파일 생성 */
    public void createExcelFile(Workbook workbook, String filePath, String fileName) throws IOException {
        //파일 이름에 유효하지 않은 문자열이 있는 지 확인
        String fileFullName = fileName + ".xlsx";

        if(fileFullName.contains("..")) {
            // throw new FileStorageException( "[ " + fileName + " ] 의 파일명에 부적합한 문자(ex: '..')가 포함되어 있습니다. ");
        }

        String fileFullPath = filePath + File.separator + fileFullName;
        FileOutputStream fos = new FileOutputStream(fileFullPath);
        workbook.write(fos);
        workbook.close();
    }


    /** Excel workbook 객체 -> response */
    public void setExcelToResponse(Workbook workbook, HttpServletResponse response, String fileName) throws IOException {
        // 컨텐츠 타입과 파일명 지정
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        String outputFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", outputFileName));
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
