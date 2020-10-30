package alex.app.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NumberUtils {

    public static String removeArtifacts(String string, List<String> artifactDictionary) {
        for (String artifact : artifactDictionary) {
            string = string.replace(artifact, "");
        }
        return string;
    }

    public static String removeDoubleZeroPrefix(String inputString) {
        return inputString.startsWith("00") ? inputString.substring(2) : inputString;
    }

    public static String clearAllButDigits(String inputString) {
        return inputString.replaceAll("[^0-9]", "");
    }
}
