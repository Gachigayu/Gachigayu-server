package team.a5.gachigayu.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.a5.gachigayu.service.CertificationService;

@RequestMapping("/api/certifications")
@RestController
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping
    public void registerUserInfo(
            @RequestPart(name = "certificationImage") MultipartFile certificationImage,
            HttpServletResponse response
    ) {
        boolean isValidCertification = certificationService.verifyCertification(certificationImage);
        if (!isValidCertification) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
