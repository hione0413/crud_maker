package org.hionesoft.crudmaker;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import org.apache.commons.io.FileUtils;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.hionesoft.crudmaker.utils.DDLParserUtil;
import org.hionesoft.crudmaker.crud_maker.CRUDMapperXmlMaker;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EtcTest {

    @Test
    public void testDdlParser() {
        String sql = "";
        sql += "CREATE TABLE TBL_CUSTOMER ( \n";
        sql += "id int not null PRIMATY KEY, \n";
        sql += "name varchar(20) not null, \n";
        sql += "age int not null, \n";
        sql += "address varchar2(25) not null \n";
        sql += ", CONSTRAINT test PRIMARY KEY (id, name) \n";
        sql += ")";

        try {
            Statement statement = DDLParserUtil.parseDdl(sql);
//            DDLParserUtil.getTablenameByDdl(statement);
//            DDLParserUtil.getColumninfosByDdl(statement);

            if (statement instanceof CreateTable) {
                CreateTable create = (CreateTable) statement;


                List<Index> indexs = create.getIndexes(); // index 에 pk 정보가 담겨있다
                List<String> options = create.getCreateOptionsStrings();
            }

        } catch (JSQLParserException e) {
            // e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
        }
    }


    @Test
    public void testMapperXmlMake() throws IOException {
        String sql = "";
        sql += "CREATE TABLE TABLE_CUSTOMER ( \n";
        sql += "id int not null, \n";
        sql += "name varchar(20) not null, \n";
        sql += "age int not null, \n";
        sql += "address varchar2(25) not null \n";
        sql += ")";

        String tablename = "";
        List<ColumnDefinition> columnDefinitions = null;

        try {
            Statement statement = DDLParserUtil.parseDdl(sql);
            tablename = DDLParserUtil.getTablenameByDdl(statement);
            columnDefinitions = DDLParserUtil.getColumninfosByDdl(statement);
        } catch (JSQLParserException e) {
            // e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
        }

        CRUDMapperXmlMaker maker = new CRUDMapperXmlMaker();
        // File targetFile = maker.makeMapperXmlToJavaSpringMybatis(tablename, columnDefinitions);

        // FileUtils.copyFile(targetFile, new File("C:\\workspace\\filetest\\test.xml"));
    }



//    @Test
//    public void crawllingMainStoryTest() throws Exception {
////        AigisMainStoryCrawler mainStoryCrawler = new AigisMainStoryCrawler();
////
////        mainStoryCrawler.getChapterListEn();
//    }
//
//
//    @Test
//    public void crawllingChar() throws Exception {
////        AigigCharacterCrawler charCrawler = new AigigCharacterCrawler(new PapagoApiUtil());
////
////        charCrawler.getBlackInfo();
//    }
}
