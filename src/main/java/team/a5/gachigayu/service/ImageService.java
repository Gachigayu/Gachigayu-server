package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public abstract class ImageService {

    abstract String uploadImage(MultipartFile multipartFile);

    public void validateImage(MultipartFile multipartFile) {
        if (!isImageFile(multipartFile.getContentType())) {
            log.info("multipartFile.getContentType() = {}", multipartFile.getContentType());
            throw new IllegalArgumentException("invalid image file");
        }
    }

    private boolean isImageFile(final String contentType) {
        return contentType != null &&
                (contentType.startsWith(MediaType.IMAGE_JPEG_VALUE) || contentType.startsWith(MediaType.IMAGE_PNG_VALUE));
    }
}
