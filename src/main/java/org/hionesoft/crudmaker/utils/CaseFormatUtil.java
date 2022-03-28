package org.hionesoft.crudmaker.utils;

import com.google.common.base.CaseFormat;

public class CaseFormatUtil {

    /** 소문자로 시작하는 카멜케이스 */
    static public String changeSnakeToCamelLower(String str) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }

    /** 대문자로 시작하는 카멜케이스 */
    static public String changeSnakeToCamelUpper(String str) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    }

    static public String changeUpperCamelToLowerCamel(String str) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, str);
    }

    static public String changeCamelToSnake(String str) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, str);
    }
}
