package org.hionesoft.crudmaker.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class JsonFileReader {
    public JsonObject readJsonFile(String jsonFilePath, String jsonFileName) throws FileNotFoundException, Exception {
        File check = new File(jsonFilePath + File.separator + jsonFileName);

        if(check.exists()) {
            return (JsonObject) JsonParser.parseReader(new FileReader(jsonFilePath));
        }

        return null;
    }


    public JsonObject readJsonFile(File jsonFile) throws FileNotFoundException, Exception {
        if(jsonFile.exists()) {
            return (JsonObject) JsonParser.parseReader(new FileReader(jsonFile));
        }

        return null;
    }


//    public List<JsonObject> readJsonFilesAtPath(String jsonFilesPath) {
//        File dir = new File(jsonFilesPath);
//
//        File[] files = dir.listFiles();
//
//        for (File file : files) {
//
//        }
//    }
}
