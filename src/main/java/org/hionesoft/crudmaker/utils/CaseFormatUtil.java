package org.hionesoft.crudmaker.utils;

import com.google.common.base.CaseFormat;

public class CaseFormatUtil {

    /** 소문자로 시작하는 카멜케이스 */
    static public String changeSnakeToCamelLower(String tablename) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tablename);
    }

    /** 대문자로 시작하는 카멜케이스 */
    static public String changeSnakeToCamelUpper(String tablename) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tablename);
    }

    static public String changeCamelToSnake(String tablename) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, tablename);
    }
}
