package org.hionesoft.crudmaker.crud_maker;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.hionesoft.crudmaker.utils.CaseFormatUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;

@Component
public class CRUDDaoMaker extends CRUDMaker {

    /**
     * @param crudMakerInfos:
     * @columnDefinitions: 칼럼 정보
     */
    public File makeDaoToJavaSpringMybatis(CRUDMakerVo crudMakerInfos) throws IOException {

        String dtoName = crudMakerInfos.getDtoName();

        ClassPathResource bluePrintResource = this.getBluePrintResource("crud_blue_print/dao_blue_print.java");
        File blueprint = bluePrintResource.getFile();
        File tempFile = readyCrudTempFile(bluePrintResource);

        Scanner input = new Scanner(blueprint);
        PrintWriter output = new PrintWriter(tempFile);

        // 생성 Start
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
                    if(match.equals("DAO_NAME")) {
                        sb.append(crudMakerInfos.getDaoName());

                    } else if (match.equals("DTO_NAME")){
                        sb.append(dtoName);

                    } else if (match.equals("TABLE_NAME")){
                        sb.append(crudMakerInfos.getTablename());

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
