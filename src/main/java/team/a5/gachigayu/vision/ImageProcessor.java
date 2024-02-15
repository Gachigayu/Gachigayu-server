package team.a5.gachigayu.vision;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
public class ImageProcessor {

    private static final String HOME_DIRECTORY_KEY = "user.home";

    public static String saveImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalName = multipartFile.getOriginalFilename();
        String extension = originalName.substring((originalName.lastIndexOf(".")));

        String uniqueName = UUID.randomUUID().toString();
        String homeDirectory = System.getProperty(HOME_DIRECTORY_KEY) + File.separator;
        String savedPath = homeDirectory + uniqueName + extension;

        try {
            multipartFile.transferTo(new File(savedPath));
        } catch (IOException e) {
            log.error("save image fail", e);
            throw new RuntimeException(e);
        }
        return savedPath;
    }

    public static void removeImage(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.delete(path);
        } catch (NoSuchFileException e) {
            log.error("file not found", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
