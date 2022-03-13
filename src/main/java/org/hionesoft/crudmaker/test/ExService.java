package org.hionesoft.crudmaker.test;

import java.util.List;

public interface ExService {
    List<ExDto> list(PaggingVo paggingVo, ExDto dto);
    ExDto view(String pk);
    int insert(ExDto dto);
    int update(String pk, ExDto dto);
    int delete(String pk);
}
