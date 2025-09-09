package vn.hoidanit.jobhunter.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void createDirectory(String folder) throws URISyntaxException;

    String store(MultipartFile file, String folder) throws URISyntaxException, IOException;
}
