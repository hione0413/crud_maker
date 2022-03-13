package org.hionesoft.crudmaker.client.make_crud.java.spring;

import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.hionesoft.crudmaker.crud_maker.CRUDMapperXmlMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MakeCrudJavaSpringService {
    private final Logger Logger = LoggerFactory.getLogger(this.getClass());

    private final CRUDMapperXmlMaker crudMapperXmlMaker;

    public File createMapperXml(String tablename, List<ColumnDefinition> columnDefinitions) throws IOException {
        return crudMapperXmlMaker.makeMapperXmlToJavaSpringMybatis(tablename, columnDefinitions);
    }
}
