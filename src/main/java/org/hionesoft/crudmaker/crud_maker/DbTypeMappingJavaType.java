package org.hionesoft.crudmaker.crud_maker;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DbTypeMappingJavaType {
    private static Map<String, List<String>> mapping = new HashMap<>();

    private static DbTypeMappingJavaType instance;

    // Singleton pattern
    public static DbTypeMappingJavaType getInstance() {
        if(instance == null) {
            synchronized (DbTypeMappingJavaType.class) {
                if(instance == null) {
                    instance = new DbTypeMappingJavaType();
                }
            }
        }

        return instance;
    }

    private DbTypeMappingJavaType() {
        List<String> stringTyleList = new ArrayList<>();
        List<String> bigDecimalTyleList = new ArrayList<>();
        List<String> booleanTyleList = new ArrayList<>();
        List<String> byteTyleList = new ArrayList<>();
        List<String> characterTyleList = new ArrayList<>();
        List<String> shortTyleList = new ArrayList<>();
        List<String> integerTyleList = new ArrayList<>();
        List<String> longTyleList = new ArrayList<>();
        List<String> floatTyleList = new ArrayList<>();
        List<String> doubleTyleList = new ArrayList<>();
        List<String> byteArrayTyleList = new ArrayList<>();
        List<String> dateTyleList = new ArrayList<>();
        List<String> timeTyleList = new ArrayList<>();
        List<String> timestampTyleList = new ArrayList<>();
        List<String> objectTyleList = new ArrayList<>();

        // String
        stringTyleList.add("VARCHAR");
        stringTyleList.add("VARCHAR2");

        // BigDecimal
        bigDecimalTyleList.add("NUMERIC");

        // Boolean
        booleanTyleList.add("BIT");
        booleanTyleList.add("BOOLEAN");
        booleanTyleList.add("TINYINT(1)");
        booleanTyleList.add("CHAR FOR BIT DATA");

        // Byte
        byteTyleList.add("TINYINT");
        byteTyleList.add("SMALLINT");

        // Character
        characterTyleList.add("CHAR");

        // Short
        shortTyleList.add("SMALLINT");

        // Integer
        integerTyleList.add("INTEGER");
        integerTyleList.add("INT");

        // Long
        longTyleList.add("BIGINT");
        longTyleList.add("NUMERIC");

        // Float
        doubleTyleList.add("FLOAT");

        // Double

        doubleTyleList.add("DOUBLE");
        doubleTyleList.add("DOUBLE PRECISION");

        // byte[]
        byteArrayTyleList.add("LONGVARBINARY");

        // java.sql.Date
        dateTyleList.add("DATE");

        // java.sql.Time
        timeTyleList.add("TIME");

        // java.sql.Timestamp
        timestampTyleList.add("TIMESTAMP");

        // Object
        objectTyleList.add("JAVA OBJECT");

        mapping.put("String", stringTyleList);
        mapping.put("BigInteger", bigDecimalTyleList);
        mapping.put("Boolean", booleanTyleList);
        mapping.put("Byte", byteTyleList);
        mapping.put("Character", characterTyleList);
        mapping.put("Short", shortTyleList);
        mapping.put("Integer", integerTyleList);
        mapping.put("Long", longTyleList);
        mapping.put("Float", floatTyleList);
        mapping.put("Double", doubleTyleList);
        mapping.put("Byte", byteArrayTyleList);
        mapping.put("Date", dateTyleList);
        mapping.put("Time", timeTyleList);
        mapping.put("Timestamp", timestampTyleList);
        mapping.put("Object", objectTyleList);
    }


    public String findJavaTypeByDbType(String dbTypename) {
        for(Map.Entry<String, List<String>> entry : mapping.entrySet()) {
            for(String checkedDbTypename : entry.getValue()) {
                if(checkedDbTypename.equals(dbTypename.toUpperCase())) {
                    return entry.getKey();
                }
            }

        }

        return "String";
    }
}
