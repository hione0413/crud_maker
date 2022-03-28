package org.hionesoft.crudmaker.test;

import java.util.List;

public interface ExService {
    int listCnt(SearchInfo searchInfo, ExDto dto);
    List<ExDto> list(PaggingInfo paggingVo, SearchInfo searchInfo, ExDto dto);
    ExDto view(String pk);
    int insert(ExDto dto);
    int update(String pk, ExDto dto);
    int delete(String pk);
}
