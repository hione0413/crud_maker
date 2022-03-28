package org.hionesoft.crudmaker.crud_maker;

import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.apache.commons.io.FilenameUtils;
import org.hionesoft.crudmaker.utils.CaseFormatUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

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


    /**
     * @param match: ex) ${DAO_NAME}
     *
     * */
    public String switchMatchToCrudValue(String match, CRUDMakerVo crudMakerInfos) {
        if(!StringUtils.hasText(match)) {
            return "";
        }

        switch (match) {
            case "TABLE_NAME":
                return crudMakerInfos.getTablename();
            case "DTO_NAME":
                return crudMakerInfos.getDtoName();
            case "DAO_NAME":
                return crudMakerInfos.getDaoName();
            case "LOWER_DAO_NAME":
                return CaseFormatUtil.changeUpperCamelToLowerCamel(crudMakerInfos.getDtoName());
            case "SERVICE_NAME":
                return crudMakerInfos.getServiceName();
            case "SERVICE_IMPL_NAME":
                return crudMakerInfos.getServiceImplName();
            case "COLUMNS_VAL":
                StringBuilder sb = new StringBuilder();
                for(ColumnDefinition columnDefinition : crudMakerInfos.getColumnDefinitions()) {
                    sb.append(
                            "    private "
                                    + this.getColumnJavaType(columnDefinition.getColDataType())
                                    + " "
                                    + CaseFormatUtil.changeSnakeToCamelLower(columnDefinition.getColumnName()) + ";"
                                    + "\n"
                    );
                }

                return sb.toString();
            default: return "";
        }
    }

}
