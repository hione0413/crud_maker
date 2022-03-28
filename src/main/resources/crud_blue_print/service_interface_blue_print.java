

import java.util.List;

public interface ${SERVICE_NAME} {
    int listCnt(SearchInfo searchInfo, ${DTO_NAME} dto);
    List<${DTO_NAME}> list(PaggingInfo paggingVo, SearchInfo searchInfo, ${DTO_NAME} dto);
    ExDto view(String pk);
    int insert(${DTO_NAME} dto);
    int update(String pk, ${DTO_NAME} dto);
    int delete(String pk);
}