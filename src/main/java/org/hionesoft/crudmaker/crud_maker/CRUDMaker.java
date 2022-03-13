package org.hionesoft.crudmaker.crud_maker;

import net.sf.jsqlparser.statement.create.table.ColDataType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class CRUDMaker {
    public final Pattern DOLLAR_PATTERN = Pattern.compile("\\$\\{(.+?)\\}");

    /** Temp 파일 생성 */
    public File readyCrudTempFile(ClassPathResource resource) throws IOException {
        File originFile = resource.getFile();
        String ext = FilenameUtils.getExtension(resource.getFilename());

        return File.createTempFile("temp_", "." + ext);
    }


    /** 청사진 가져오기 */
    public ClassPathResource getBluePrintResource(String bluePrintPath) {
        ClassPathResource resource = new ClassPathResource(bluePrintPath);
        return resource;
    }


    public String getColumnJavaType(ColDataType colDataType) {
        String dbTypename = colDataType.getDataType();
        System.out.println("dbTypename:::" + dbTypename);
        return DbTypeMappingJavaType.getInstance().findJavaTypeByDbType(dbTypename);
    }
}
