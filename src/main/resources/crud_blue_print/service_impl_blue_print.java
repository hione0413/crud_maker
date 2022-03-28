

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ${SERVICE_IMPL_NAME} implements ${SERVICE_NAME} {
    private final ${DAO_NAME} ${LOWER_DAO_NAME};


    public int listCnt(SearchInfo searchInfo, ${DTO_NAME} dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("search", searchInfo);
        params.put("dto", dto);

        return ${LOWER_DAO_NAME}.listCnt(params);
    }

    public List<${DTO_NAME}> list(PaggingInfo paggingInfo, SearchInfo searchInfo, ${DTO_NAME} dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("pagging", paggingInfo);
        params.put("search", searchInfo);
        params.put("dto", dto);

        return ${LOWER_DAO_NAME}.list(params);
    }

    public ${DTO_NAME} view(String pk) {
        return ${LOWER_DAO_NAME}.view(pk);
    }

    public int insert(${DTO_NAME} dto) {
        return ${LOWER_DAO_NAME}.insert(dto);
    }

    public int update(String pk, ${DTO_NAME} dto) {
        // TODO: dto.setPk(pk);
        return ${LOWER_DAO_NAME}.update(dto);
    }

    public int delete(String pk) {
        return ${LOWER_DAO_NAME}.delete(pk);
    }
}
