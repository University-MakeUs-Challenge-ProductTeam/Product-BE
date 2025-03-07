package umc.product.global.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.dto.excel.ExcelMember;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static umc.product.global.common.exception.code.status.GlobalErrorStatus._FAILED_READ_FILE;

@Component
public class ExcelFileUtil {
    public List<ExcelMember> convertToExcelMember(MultipartFile excel, List<University> universityList) {
        Sheet sheet = getSheet(excel);

        List<Row> rows = new ArrayList<>();
        sheet.iterator().forEachRemaining(rows::add); // 모든 Row를 리스트로 변환
        rows.remove(0); // 헤더 제거

        Map<String, University> universityMap = universityList.stream()
                .collect(Collectors.toMap(University::getName, university -> university));

        return rows.stream()
                .map(row -> toExcelMember(row, universityMap)) // 병렬 변환
                .collect(Collectors.toList()); // 결과 리스트로 변환
    }


    private Sheet getSheet(MultipartFile excel) {
        try (InputStream inputStream = excel.getInputStream()){
            Workbook workbook = new XSSFWorkbook(inputStream);
            return workbook.getSheetAt(0);
        }catch (Exception e) {
            throw new RestApiException(_FAILED_READ_FILE);
        }
    }

    private ExcelMember toExcelMember(Row row, Map<String, University> universityMap) {
        String universityPosition = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null;
        String centralPosition = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null;

        String role = determineRole(centralPosition, universityPosition);

        return ExcelMember.builder()
                .university(universityMap.get(row.getCell(0).getStringCellValue()))
                .nickName(row.getCell(1).getStringCellValue())
                .name(row.getCell(2).getStringCellValue())
                .role(Role.valueOf(role))
                .centralPosition(centralPosition)
                .universityPosition(universityPosition)
                .build();
    }

    private String determineRole(String centralPosition, String universityPosition) {
        if (centralPosition == null && universityPosition == null) {
            return "CHALLENGER";
        }

        if (centralPosition != null && (centralPosition.equals("총괄") || centralPosition.equals("부총괄"))) {
            return "ADMIN";
        }

        if (centralPosition != null) {
            return "CENTRAL_ADMIN";
        }

        if (universityPosition != null) {
            return "UNIVERSITY_STAFF";
        }

        return "CHALLENGER";
    }

}
