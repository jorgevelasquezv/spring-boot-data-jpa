package co.com.jorge.springboot.app.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUploadFileService {

    Resource load(String filename) throws MalformedURLException;

    String copy(MultipartFile file) throws IOException;

    boolean delete(String filename);
}
