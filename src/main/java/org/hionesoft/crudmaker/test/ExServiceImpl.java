package org.hionesoft.crudmaker.test;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ExServiceImpl implements ExService {
    private final ExDao exDao;


    public int listCnt(SearchInfo searchInfo, ExDto dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("search", searchInfo);
        params.put("dto", dto);

        return exDao.listCnt(params);
    }

    public List<ExDto> list(PaggingInfo paggingInfo, SearchInfo searchInfo, ExDto dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("pagging", paggingInfo);
        params.put("search", searchInfo);
        params.put("dto", dto);

        return exDao.list(params);
    }

    public ExDto view(String pk) {
        return exDao.view(pk);
    }

    public int insert(ExDto dto) {
        return exDao.insert(dto);
    }

    public int update(String pk, ExDto dto) {
        // dto.setPk(pk);
        return exDao.update(dto);
    }

    public int delete(String pk) {
        return exDao.delete(pk);
    }
}
