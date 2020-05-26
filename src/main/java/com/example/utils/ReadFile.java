package com.example.utils;

import com.example.model.Usuario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ReadFile {

    public static void copyFile(String directory, String fileName, String replaceName){
        File originFile = new File(directory, fileName);
        File destinyFile = new File(directory, replaceName);

        if(originFile.exists()){
            try {
                Files.copy(Paths.get(originFile.getAbsolutePath()), Paths.get(destinyFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
