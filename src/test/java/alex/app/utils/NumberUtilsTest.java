package alex.app.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class NumberUtilsTest {

    @Test
    public void removeArtifacts() {
        List<String> artifactStrings = new ArrayList<>();
        artifactStrings.add("something");
        artifactStrings.add("[label1]");
        artifactStrings.add("!@!%!%#");
        String result = NumberUtils.removeArtifacts("something, This, [label1], is, an, !@!%!%#, important, strings", artifactStrings);
        assertEquals(", This, , is, an, , important, strings", result);
    }

    @Test
    public void removeDoubleZeroPrefix() {
        String expectedResult = "12345678900";
        String randomString = "0012345678900";
        String result = NumberUtils.removeDoubleZeroPrefix(randomString);
        assertEquals(expectedResult, result);
    }

    @Test
    public void clearAllButDigits() {
        String expectedResult = "123456789";
        String randomString = "123+4%!@Fqa/5-=+6]7{}8'9";
        String result = NumberUtils.clearAllButDigits(randomString);
        assertEquals(expectedResult, result);
    }
}