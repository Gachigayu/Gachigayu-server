package team.a5.gachigayu.util;

import java.util.List;

public class TourGuideCertificationVerifier {

    private static final List<String> REQUIRED_WORDS = List.of("문화관광", "해설사", "수료");

    public static boolean isValidCertification(String detectedText, String name) {
        return REQUIRED_WORDS.stream().allMatch(detectedText::contains)
                && detectedText.contains(name);
    }
}
