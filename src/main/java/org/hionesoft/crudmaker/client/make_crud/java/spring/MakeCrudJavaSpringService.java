package org.hionesoft.crudmaker.client.make_crud.java.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MakeCrudJavaSpringService {
    private final Logger Logger = LoggerFactory.getLogger(this.getClass());



    public File createMapperXml() throws IOException {
        ClassPathResource resource = new ClassPathResource("crud_blue_print/mapper_blue_print.xml");
        return resource.getFile();
    }
}
