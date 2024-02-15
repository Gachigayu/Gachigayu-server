package team.a5.gachigayu.vision;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ImageVisioningService {

    public String detectText(String filePath) {
        List<AnnotateImageRequest> requests = new ArrayList<>();
        ByteString imageBytes;
        try {
            imageBytes = ByteString.readFrom(new FileInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image image = Image.newBuilder().setContent(imageBytes).build();
        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = batchResponse.getResponsesList();

            StringBuilder detectedText = new StringBuilder();
            for (AnnotateImageResponse response : responses) {
                if (response.hasError()) {
                    log.error("Error: {}%n", response.getError().getMessage());
                    return "";
                }

                for (EntityAnnotation annotation : response.getTextAnnotationsList()) {
                    String text = annotation.getDescription();
                    detectedText.append(text);
                }
            }
            log.info("detectedText = {}", detectedText);
            return detectedText.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
