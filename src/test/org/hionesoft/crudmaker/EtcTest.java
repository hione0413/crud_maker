package org.hionesoft.crudmaker;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;
import org.hionesoft.crudmaker.utils.DDLParserUtil;
import org.junit.Test;

public class EtcTest {

    @Test
    public void testDdlParser() {
        String sql = "";
        sql += "CREATE TABLE CUSTOMER ( \n";
        sql += "id int not null, \n";
        sql += "name varchar(20) not null, \n";
        sql += "age int not null, \n";
        sql += "address varchar2(25) not null \n";
        sql += ")";

        try {
            Statement statement = DDLParserUtil.parseDdl(sql);
            DDLParserUtil.getTablenameByDdl(statement);
            DDLParserUtil.getColumninfosByDdl(statement);
        } catch (JSQLParserException e) {
            // e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
        }
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
