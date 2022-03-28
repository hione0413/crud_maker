package org.hionesoft.crudmaker.client.make_crud.java.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hionesoft.crudmaker.crud_maker.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MakeCrudJavaSpringService {
    private final CRUDMapperXmlMaker crudMapperXmlMaker;
    private final CRUDDtoMaker crudDtoMaker;
    private final CRUDDaoMaker crudDaoMaker;
    private final CRUDServiceMaker crudServiceMaker;
    private final CRUDControllerMaker crudControllerMaker;

    public File createMapperXml(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudMapperXmlMaker.makeMapperXmlToJavaSpringMybatis(crudMakerInfos);
    }

    public File createDtoClass(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudDtoMaker.makeDtoToJavaSpringMybatis(crudMakerInfos);
    }

    public File createDaoClass(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudDaoMaker.makeDaoToJavaSpringMybatis(crudMakerInfos);
    }

    public File createServiceInterface(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudServiceMaker.makeServiceInterface(crudMakerInfos);
    }

    public File createServiceImplClass(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudServiceMaker.makeServiceImplClass(crudMakerInfos);
    }

    public File createRestControllerClass(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudControllerMaker.makeRestControllerClass(crudMakerInfos);
    }
}
