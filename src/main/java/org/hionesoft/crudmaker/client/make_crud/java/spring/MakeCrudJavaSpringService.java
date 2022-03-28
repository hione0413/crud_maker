package org.hionesoft.crudmaker.client.make_crud.java.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.hionesoft.crudmaker.crud_maker.CRUDDaoMaker;
import org.hionesoft.crudmaker.crud_maker.CRUDDtoMaker;
import org.hionesoft.crudmaker.crud_maker.CRUDMakerVo;
import org.hionesoft.crudmaker.crud_maker.CRUDMapperXmlMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MakeCrudJavaSpringService {
    private final CRUDMapperXmlMaker crudMapperXmlMaker;
    private final CRUDDtoMaker crudDtoMaker;
    private final CRUDDaoMaker crudDaoMaker;

    public File createMapperXml(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudMapperXmlMaker.makeMapperXmlToJavaSpringMybatis(crudMakerInfos);
    }

    public File createDtoClass(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudDtoMaker.makeDtoToJavaSpringMybatis(crudMakerInfos);
    }

    public File createDaoClass(CRUDMakerVo crudMakerInfos) throws IOException {
        return crudDaoMaker.makeDaoToJavaSpringMybatis(crudMakerInfos);
    }
}
