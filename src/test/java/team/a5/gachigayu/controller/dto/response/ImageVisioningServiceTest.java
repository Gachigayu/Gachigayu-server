package team.a5.gachigayu.controller.dto.response;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageVisioningServiceTest {

    @Autowired
    private ImageVisioningService imageVisioningService;

    @Test
    void test() {
        imageVisioningService.test();
    }
}