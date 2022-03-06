package org.hionesoft.crudmaker.client.make_crud.java.spring;

import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Java Spring 기본 Crud 생성기
 * */
@RequiredArgsConstructor
@RestController
@RequestMapping("/makecrud/java/spring")
public class MakeCrudJavaSpringRestCtroller {
    private final Logger Logger = LoggerFactory.getLogger(this.getClass());
    private final MakeCrudJavaSpringService makeCrudJavaSpringService;

    @PostMapping("/create")
    public void makeCrudJavaSpring(
            @RequestParam(required = false, defaultValue = "oracle") String dbType,
            @RequestParam String ddl,
            HttpServletResponse response){
        String fileName = "test";
        List<File> fileList = new ArrayList<>();

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;

        String tablename = "";
        List<ColumnDefinition> columnDefinitions = null;
        
        // 1. SQL DDL 분석 및 변수 세팅
        try {
            Statement statement = DDLParserUtil.parseDdl(ddl);
            tablename = DDLParserUtil.getTablenameByDdl(statement);
            columnDefinitions = DDLParserUtil.getColumninfosByDdl(statement);
        } catch (JSQLParserException e) {
            String msg = e.getMessage();
            Logger.error(msg);
            // TODO : Error Throw 처리
        }
        
        //TODO : 파일 생성하는 부분들 전부 Util 처리 (xml만 DB Type에 따라 분기하면 될 듯)

        // 2. 파일 생성
        // (1) Mapper xml 생성
        try {
            File mapperXml = makeCrudJavaSpringService.createMapperXml();
            fileList.add(mapperXml);
        } catch (IOException e) {
            // return ResponseEntity.noContent().build();
        }
        
        // (2) DTO 생성
        
        
        // (3) DAO 생성
        
        
        // (4) Service 생성
        
        
        // (5) Controller 생성


        // 3.Zip 파일 다운로드 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");

        // 4. Zip 파일 생성
        try {
            zipOut = new ZipOutputStream(response.getOutputStream());

            for(File file : fileList) {
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                fis = new FileInputStream(file);

                StreamUtils.copy(fis, zipOut);

                fis.close();
                zipOut.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if(fis != null) fis.close(); } catch (IOException e1) {Logger.error(e1.getMessage());/*ignore*/}
            try { if(zipOut != null) zipOut.closeEntry();} catch (IOException e2) {Logger.error(e2.getMessage());/*ignore*/}
            try { if(zipOut != null) zipOut.close();} catch (IOException e3) {Logger.error(e3.getMessage());/*ignore*/}
            try { if(fos != null) fos.close(); } catch (IOException e4) {Logger.error(e4.getMessage());/*ignore*/}
        }
    }
}
