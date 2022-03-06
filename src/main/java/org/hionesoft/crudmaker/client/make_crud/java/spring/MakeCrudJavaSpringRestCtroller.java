package org.hionesoft.crudmaker.client.make_crud.java.spring;

import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void makeCrudJavaSpring(HttpServletResponse response){
        String fileName = "test";
        List<File> fileList = new ArrayList<>();

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        
        // 1. SQL DDL 분석 및 변수 세팅
        
        // 2. 파일 생성
        // (1) Mapper xml 생성
        try {
            File mapperXml = makeCrudJavaSpringService.createMapperXml();
            fileList.add(mapperXml);
        } catch (IOException e) {
            // return ResponseEntity.noContent().build();
        }


        // 4.Zip 파일 다운로드
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");

        // 3. Zip 파일 생성
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
