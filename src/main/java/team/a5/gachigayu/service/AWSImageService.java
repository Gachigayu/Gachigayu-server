package team.a5.gachigayu.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.a5.gachigayu.config.AWSProperties;

import java.io.IOException;
import java.util.UUID;

@Service
public class AWSImageService extends ImageService {

    private final AmazonS3 amazonS3;
    private final AWSProperties awsProperties;

    public AWSImageService(AmazonS3 amazonS3, AWSProperties awsProperties) {
        this.amazonS3 = amazonS3;
        this.awsProperties = awsProperties;
    }
    
    @Override
    public String uploadImage(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }
        validateImage(multipartFile);

        String fileName = generateUniqueFileName(multipartFile);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(new PutObjectRequest(
                    awsProperties.getBucketName(), fileName, multipartFile.getInputStream(), metadata
            )/*.withCannedAcl(CannedAccessControlList.PublicRead)*/);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return amazonS3.getUrl(awsProperties.getBucketName(), fileName)
                .toString();
    }

    private String generateUniqueFileName(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + fileExtension;
    }
}
