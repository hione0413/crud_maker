package org.hionesoft.crudmaker;

import org.hionesoft.crudmaker.utils.crawler.AigisMainStoryCrawler;
import org.junit.Test;

public class EtcTest {

    @Test
    public void crawllingMainStoryTest() throws Exception {
        AigisMainStoryCrawler mainStoryCrawler = new AigisMainStoryCrawler();

        mainStoryCrawler.getChapterListEn();
    }


    @Test
    public void crawllingChar() throws Exception {
//        AigigCharacterCrawler charCrawler = new AigigCharacterCrawler(new PapagoApiUtil());
//
//        charCrawler.getBlackInfo();
    }
}
