package org.hionesoft.crudmaker.test;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// @RequiredArgsConstructor
@Repository
public class ExDao {
    private final SqlSession sqlSession = null;
    private static final String MAPPER_ID = "";


    public int listCnt(Map<String, Object> params) {
        return sqlSession.selectOne(MAPPER_ID + ".listCnt", params);
    }

    public List<ExDto> list(Map<String, Object> paramMap) {
        return sqlSession.selectList(MAPPER_ID + ".list", paramMap);
    }

    public ExDto view(String pk) {
        return sqlSession.selectOne(MAPPER_ID + ".view", pk);
    }

    public int insert(ExDto dto) {
        return sqlSession.insert(MAPPER_ID + ".insert", dto);
    }

    public int update(ExDto dto) {
        return sqlSession.update(MAPPER_ID + ".update", dto);
    }

    public int delete(String pk) {
        return sqlSession.delete(MAPPER_ID + ".delete", pk);
    }
}
