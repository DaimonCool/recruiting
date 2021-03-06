package com.daimon.recruiting.candidate.service;

import com.daimon.recruiting.candidate.exception.FileException;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    public String uploadFile(String folder, String fileName, byte[] content) {
        var path = folder + "/" + fileName;
        try {
            return Files.write(Paths.get(path), content).toAbsolutePath().toString();
        } catch (Exception e) {
            throw new FileException();
        }
    }

    public void deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (Exception e) {
            throw new FileException();
        }
    }

    public byte[] readFile(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            throw new FileException();
        }
    }

}
