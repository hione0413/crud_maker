package org.hionesoft.crudmaker.utils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class DDLParserUtil {
    private static final Logger Logger = LoggerFactory.getLogger(DDLParserUtil.class);

    public static Statement parseDdl(String ddl) throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        Statement statement = pm.parse(new StringReader(ddl));

        if (statement instanceof CreateTable) {
            return statement;
        }
        return null;
    }


    public static String getTablenameByDdl(Statement statement) {
        String tablename = "";
        if (statement instanceof CreateTable) {
            CreateTable create = (CreateTable) statement;
            tablename = create.getTable().getName();
        }
        Logger.debug("[parseDdl] tableName ::: " + tablename);
        return tablename;
    }


    public static List<ColumnDefinition> getColumninfosByDdl(Statement statement) {
        List<ColumnDefinition> columnNames = new ArrayList<>();

        if (statement instanceof CreateTable) {
            CreateTable create = (CreateTable) statement;
            List<ColumnDefinition> columns = create.getColumnDefinitions();
            for (ColumnDefinition def : columns) {
                Logger.debug("[parseDdl] ColumnDefinition ::: " + def);
                Logger.debug("[parseDdl] name ::: " + def.getColumnName());
                Logger.debug("[parseDdl] type ::: " + def.getColDataType());
                Logger.debug("[parseDdl] specs ::: " + def.getColumnSpecs());
                columnNames.add(def);
            }
        }

        return columnNames;
    }


    public static List<Index> getColumnIndexsByDdl(Statement statement) {
        if (statement instanceof CreateTable) {
            CreateTable create = (CreateTable) statement;
            return create.getIndexes();
        }
        return null;
    }
}
