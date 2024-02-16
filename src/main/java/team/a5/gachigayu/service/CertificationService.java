package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.repository.UserRepository;
import team.a5.gachigayu.util.TourGuideCertificationVerifier;
import team.a5.gachigayu.vision.ImageProcessor;
import team.a5.gachigayu.vision.ImageVisioningService;

@Slf4j
@Transactional
@Service
public class CertificationService {

    private final ImageVisioningService imageVisioningService;
    private final UserRepository userRepository;

    public CertificationService(ImageVisioningService imageVisioningService, UserRepository userRepository) {
        this.imageVisioningService = imageVisioningService;
        this.userRepository = userRepository;
    }

    public boolean verifyCertification(MultipartFile certificationImage) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        String certificationImagePath = ImageProcessor.saveImage(certificationImage);
        String detectedText = imageVisioningService.detectText(certificationImagePath);
        log.info("detectedText = {}", detectedText);
        ImageProcessor.removeImage(certificationImagePath);

        boolean isValid = TourGuideCertificationVerifier
                .isValidCertification(detectedText, authenticatedUser.getName());
        if (isValid) {
            authenticatedUser.verify();
            userRepository.save(authenticatedUser);
        }
        return isValid;
    }
}
