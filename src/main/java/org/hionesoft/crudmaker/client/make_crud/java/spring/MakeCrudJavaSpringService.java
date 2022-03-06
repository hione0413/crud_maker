package org.hionesoft.crudmaker.client.make_crud.java.spring;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MakeCrudJavaSpringService {


    public File createMapperXml() throws IOException {
        ClassPathResource resource = new ClassPathResource("crud_blue_print/mapper_blue_print.xml");
        return resource.getFile();
    }
}
