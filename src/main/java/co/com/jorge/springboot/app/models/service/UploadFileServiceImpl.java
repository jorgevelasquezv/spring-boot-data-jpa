package co.com.jorge.springboot.app.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {


    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String UPLOADS_FOLDER = "uploads";

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path pathPhoto = getPath(filename);
        log.info("PathPhoto: " + pathPhoto);

        Resource resource = null;
        resource = new UrlResource(pathPhoto.toUri());
        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("Error no se puede cargar la imagen: " + pathPhoto.toString());
        }

        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Path rootPath = getPath(uniqueFilename);

        log.info("rootPath: " + rootPath);
        Files.copy(file.getInputStream(), rootPath);

        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();

        if (file.exists() && file.canRead()){
            if (file.delete()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectories(Paths.get(UPLOADS_FOLDER));
    }

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
