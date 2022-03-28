package org.hionesoft.crudmaker.client.make_crud.java.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.hionesoft.crudmaker.crud_maker.CRUDMakerVo;
import org.hionesoft.crudmaker.utils.CaseFormatUtil;
import org.hionesoft.crudmaker.utils.DDLParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Java Spring 기본 Crud 생성기
 * */
@RequiredArgsConstructor
@RestController
@RequestMapping("/makecrud/java/spring")
@Slf4j
public class MakeCrudJavaSpringRestCtroller {
    private final MakeCrudJavaSpringService makeCrudJavaSpringService;

    @PostMapping("/create")
    public void makeCrudJavaSpring(
            @RequestParam(required = false, defaultValue = "oracle") String dbType,
            // @RequestParam String ddl,
            HttpServletResponse response){

        String sql = "";
        sql += "CREATE TABLE TABLE_CUSTOMER ( \n";
        sql += "id int not null, \n";
        sql += "name varchar(20) not null, \n";
        sql += "age int not null, \n";
        sql += "address varchar2(25) not null \n";
        sql += ")";

        String ddl = sql;

        Map<String, File> fileMap = new HashMap<>();

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;

        CRUDMakerVo crudMakerInfos = null;
        // 1. SQL DDL 분석 및 변수 세팅
        try {
            crudMakerInfos = new CRUDMakerVo(sql);
        } catch (JSQLParserException e) {
            String msg = e.getMessage();
            log.error(msg);
            // TODO : Error Throw 처리
        }

        //TODO : 파일 생성하는 부분들 전부 Util 처리 (xml만 DB Type에 따라 분기하면 될 듯)

        // 2. 파일 생성
        // (1) Mapper xml 생성
        try {
            File mapperXml = makeCrudJavaSpringService.createMapperXml(crudMakerInfos);
            fileMap.put(crudMakerInfos.getMapperName(), mapperXml);
        } catch (IOException e) {
            // return ResponseEntity.noContent().build();
        }
        
        // (2) DTO 생성
        try {
            File dtoClassFile = makeCrudJavaSpringService.createDtoClass(crudMakerInfos);
            fileMap.put(crudMakerInfos.getDtoName(), dtoClassFile);
        } catch (IOException e) {
            // return ResponseEntity.noContent().build();
        }
        
        // (3) DAO 생성
        try {
            File daoClassFile = makeCrudJavaSpringService.createDaoClass(crudMakerInfos);
            fileMap.put(crudMakerInfos.getDaoName(), daoClassFile);
        } catch (IOException e) {
            // return ResponseEntity.noContent().build();
        }
        
        // (4) Service 생성
        
        
        // (5) Controller 생성


        // 3.Zip 파일 다운로드 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=" + crudMakerInfos.getTablename() + "_CRUD.zip");

        // 4. Zip 파일 생성
        try {
            zipOut = new ZipOutputStream(response.getOutputStream());

            Set<Map.Entry<String, File>> entrySet = fileMap.entrySet();

            for(Map.Entry<String, File> entry : entrySet) {
                String key = entry.getKey();
                File file = entry.getValue();

                zipOut.putNextEntry(new ZipEntry(key));
                fis = new FileInputStream(file);

                StreamUtils.copy(fis, zipOut);

                fis.close();
                zipOut.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if(fis != null) fis.close(); } catch (IOException e1) {log.error(e1.getMessage());/*ignore*/}
            try { if(zipOut != null) zipOut.closeEntry();} catch (IOException e2) {log.error(e2.getMessage());/*ignore*/}
            try { if(zipOut != null) zipOut.close();} catch (IOException e3) {log.error(e3.getMessage());/*ignore*/}
            try { if(fos != null) fos.close(); } catch (IOException e4) {log.error(e4.getMessage());/*ignore*/}
        }
    }
}
