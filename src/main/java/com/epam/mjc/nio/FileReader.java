package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String fileContent = null;
        try {
            fileContent = readFileContent(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String , String> dataMap = parseData(fileContent);
        return createProfile(dataMap);
    }

    private String readFileContent(Path filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(filePath);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    private Map<String, String> parseData(String fileContent) {
        Map<String, String> dataMap = new HashMap<>();
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] keyValue = line.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                dataMap.put(key, value);
            }
        }
        return dataMap;
    }

    private Profile createProfile(Map<String, String> dataMap) {
        Profile profile = new Profile();
        profile.setName(dataMap.get("Name"));
        profile.setAge(Integer.parseInt(dataMap.get("Age")));
        profile.setEmail(dataMap.get("Email"));
        profile.setPhone(Long.valueOf(dataMap.get("Phone")));
        return profile;
    }
}
