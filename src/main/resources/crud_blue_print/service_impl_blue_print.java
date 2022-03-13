
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ExServiceImpl implements ExService {
    private final ExDao exDao;

    public List<ExDto> list(PaggingVo paggingVo, ExDto dto) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pagging", paggingVo);
        paramMap.put("dto", dto);

        return exDao.list(paramMap);
    }

    public ExDto view(String pk) {
        return exDao.view(pk);
    }

    public int insert(ExDto dto) {
        return exDao.insert(dto);
    }

    public int update(String pk, ExDto dto) {
        return exDao.update(pk, dto);
    }

    public int delete(String pk) {
        return exDao.delete(pk);
    }
}
