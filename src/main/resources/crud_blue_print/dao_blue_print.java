

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ${DAO_NAME} {
    private final SqlSession sqlSession;
    private static final String MAPPER_ID = "${TABLE_NAME}";


    public int listCnt(Map<String, Object> params) {
        return sqlSession.selectOne(MAPPER_ID + ".listCnt", params);
    }

    public List<${DTO_NAME}> list(Map<String, Object> paramMap) {
        return sqlSession.selectList(MAPPER_ID + ".list", paramMap);
    }

    public ${DTO_NAME} view(String pk) {
        return sqlSession.selectOne(MAPPER_ID + ".view", pk);
    }

    public int insert(${DTO_NAME} dto) {
        return sqlSession.insert(MAPPER_ID + ".insert", dto);
    }

    public int update(${DTO_NAME} dto) {
        return sqlSession.update(MAPPER_ID + ".update", dto);
    }

    public int delete(String pk) {
        return sqlSession.delete(MAPPER_ID + ".delete", pk);
    }
}
