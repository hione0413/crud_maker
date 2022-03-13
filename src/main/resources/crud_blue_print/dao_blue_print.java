

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ExDao {
    private final SqlSession sqlSession;
    private static final String MAPPER_ID = "";

    public List<ExDto> list() {
        return sqlSession.selectList(MAPPER_ID + ".list");
    }

    public List<ExDto> list(Map paramMap) {
        return sqlSession.selectList(MAPPER_ID + ".list", paramMap);
    }

    public ExDto view(String pk) {
        return sqlSession.selectOne(MAPPER_ID + ".view", pk);
    }

    public int insert(ExDto dto) {
        return sqlSession.insert(MAPPER_ID + ".insert", dto);
    }

    public int update(String pk, ExDto dto) {
        return sqlSession.update(MAPPER_ID + ".update", dto);
    }

    public int delete(String pk) {
        return sqlSession.delete(MAPPER_ID + ".delete", pk);
    }
}
