import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tubes.matrix.BicubicInterpolation;
import tubes.matrix.LinearEquation;
import tubes.matrix.Matrix;
import tubes.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BicubicIntepolationTest {
    @Test
    void testBicubic() {
        try {
            double[][] sample = Util.readFromFile(new File("src/test/cases/bicubicinterpolation/bicubic1.txt").getCanonicalPath());
            Matrix matrix = new Matrix(sample);
            double x, y, expected;

            x = 0;
            y = 0;
            expected = 161.0;
            assertEquals(expected, BicubicInterpolation.interpolate(matrix, x, y), 1);

            x = 0;
            y = 1;
            expected = 101.0;
            assertEquals(expected, BicubicInterpolation.interpolate(matrix, x, y), 1);

            x = 1;
            y = 0;
            expected = 72.0;
            assertEquals(expected, BicubicInterpolation.interpolate(matrix, x, y), 1);

            x = 1;
            y = 1;
            expected = 42.0;
            assertEquals(expected, BicubicInterpolation.interpolate(matrix, x, y), 1);

            x = 0.5;
            y = 0.5;
            expected = 97.75;
            assertEquals(expected, BicubicInterpolation.interpolate(matrix, x, y), 1e-2);

            x = 0.25;
            y = 0.75;
            expected = 105.527588;
            assertEquals(expected, BicubicInterpolation.interpolate(matrix, x, y), 1e-6);

            x = 0.1;
            y = 0.9;
            expected = 104.231940;
            assertEquals(expected, BicubicInterpolation.interpolate(matrix, x, y), 1e-6);
        } catch (IOException e) {
            fail(e);
        }
    }
}
