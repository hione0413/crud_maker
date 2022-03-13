package org.hionesoft.crudmaker.crud_maker;

import lombok.Data;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.Index;
import org.hionesoft.crudmaker.utils.CaseFormatUtil;
import org.hionesoft.crudmaker.utils.DDLParserUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CRUDMakerVo {
    private String tablename;
    private List<ColumnDefinition> columnDefinitions;
    private List<Index> tableIndexs;
    private String mapperName;
    private String dtoName;
    private String daoName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    private String dbType;

    private Set<String> pks;


    public CRUDMakerVo(String ddlQuery) throws JSQLParserException {
        Statement statement = DDLParserUtil.parseDdl(ddlQuery);
        this.tablename = DDLParserUtil.getTablenameByDdl(statement);
        this.columnDefinitions = DDLParserUtil.getColumninfosByDdl(statement);
        this.tableIndexs = DDLParserUtil.getColumnIndexsByDdl(statement);

        this.mapperName = tablename + "_SQL.xml";
        this.dtoName = CaseFormatUtil.changeSnakeToCamelUpper(tablename) + "DTO";
        this.daoName = CaseFormatUtil.changeSnakeToCamelUpper(tablename) + "DAO";
        this.serviceName = CaseFormatUtil.changeSnakeToCamelUpper(tablename) + "Service";
        this.serviceImplName = CaseFormatUtil.changeSnakeToCamelUpper(tablename) + "ServiceImpl";
        this.controllerName = CaseFormatUtil.changeSnakeToCamelUpper(tablename) + "RestController";


        // PK SET 생성
        Set<String> tempSet = new HashSet<>();

        for(Index index : tableIndexs) {
            if("PRIMARY KEY".equals(index.getType())) {
                for(Index.ColumnParams pkColumnName : index.getColumns()) {
                    tempSet.add(pkColumnName.columnName);
                }
            }
        }

        for(ColumnDefinition column : columnDefinitions) {
            List<String> specs = column.getColumnSpecs();

            if(specs.contains("PRIMARY") || specs.contains("primary")) {
                if(specs.contains("KEY") || specs.contains("key")) {
                    tempSet.add(column.getColumnName());
                }
            }
        }

        this.pks = tempSet;
    }
}
