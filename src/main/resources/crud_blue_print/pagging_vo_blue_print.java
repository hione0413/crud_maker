

import lombok.Getter;
import lombok.Setter;

@Getter
public class PaggingVo {

    @Setter
    private int currentPageNo;
    @Setter
    private int recordCountPerPage = 10; // 10 is default
    @Setter
    private int pageSize = 10;
    @Setter
    private int totalRecordCount;


    /**
     * Not Required Fields
     *  아래 필드들은 Required Fields 값을 바탕으로 계산해서 정해지는 필드 값이다
     *  totalPageCount: 페이지 개수
     *  firstPageNoOnPageList: 페이지 리스트의 첫 페이지 번호
     *  lastPageNoOnPageList: 페이지 리스트의 마지막 페이지 번호
     *  firstRecordIndex: 페이징 SQL 의 조건절에 사용되는 시작 rownum
     *  lastRecordIndex: .페이징 SQL 의 조건절에 사용되는 마지막 rownum
     * */
    private int totalPageCount;
    private int firstPageNoOnPageList;
    private int lastPageNoOnPageList;
    private int firstRecordIndex;
    private int lastRecordIndex;

    public int getTotalPageCount() {
        totalPageCount = ((getTotalRecordCount() - 1) / getRecordCountPerPage()) + 1;
        return totalPageCount;
    }

    public int getFirstPageNo() {
        return 1;
    }

    public int getlastPageNo() {
        return getTotalPageCount();
    }

    public int getLastPageNoOnPageList() {
        lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;
        if (lastPageNoOnPageList > getTotalPageCount()) {
            lastPageNoOnPageList = getTotalPageCount();
        }
        return lastPageNoOnPageList;
    }

    public int getFirstRecordIndex() {
        firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
        return firstRecordIndex;
    }

    public int getLastRecordIndex() {
        lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
        return lastRecordIndex;
    }
}