package org.hionesoft.crudmaker.crud_maker;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.hionesoft.crudmaker.utils.CaseFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

@Component
public class CRUDMapperXmlMaker extends CRUDMaker {
    private final Logger Logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @param crudMakerInfos:
     * @columnDefinitions: 칼럼 정보
     */
    public File makeMapperXmlToJavaSpringMybatis(CRUDMakerVo crudMakerInfos) throws IOException {

        String dtoName = crudMakerInfos.getDtoName();

        ClassPathResource bluePrintResource = this.getBluePrintResource("crud_blue_print/mapper_blue_print.xml");
        File blueprint = bluePrintResource.getFile();
        File tempFile = readyCrudTempFile(bluePrintResource);

        Scanner input = new Scanner(blueprint);
        PrintWriter output = new PrintWriter(tempFile);

        // Mapper 생성 Start
        StringBuffer sb = new StringBuffer();
        Matcher matcher = null;
        int textStartPoint = 0;

        while(input.hasNext()) {
            String line = input.nextLine();
            matcher = this.DOLLAR_PATTERN.matcher(line);

            while(matcher.find()) {
                String match = matcher.group(1);
                sb.append(line.substring(textStartPoint, matcher.start()));

                if(StringUtils.hasText(match)) {
                    if(match.equals("TABLE_NAME")) {
                        sb.append(crudMakerInfos.getTablename());
                    } else if (match.equals("DTO_NAME")){
                        sb.append(dtoName);
                    }
                    textStartPoint = matcher.end();
                }
            }

            sb.append(line.substring(textStartPoint));

            // 변환된 내용 입력
            output.println(sb);

            sb.delete(0, sb.length()); // StringBuffer 초기화
            matcher.reset();    // matcher 초기화
            textStartPoint = 0;
        }


        if(input != null) {
            input.close();
        }
        if(output != null) {
            output.close();
        }

        return tempFile;
    }
}
