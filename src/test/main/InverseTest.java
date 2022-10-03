import org.junit.jupiter.api.Test;
import tubes.matrix.Determinant;
import tubes.matrix.Inverse;
import tubes.matrix.LinearEquation;
import tubes.matrix.Matrix;
import tubes.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class InverseTest {
    void assertData(double[][] expected, double[][] actual) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                assertEquals(expected[i][j], actual[i][j], 1e-5);
            }
        }
    }

    @Test
    void testInverse() {
        try {
            double[][] sample = Util.readFromFile(new File("src/test/cases/inverse/inverse1.txt").getCanonicalPath());
            double[][] expected = Util.readFromFile(new File("src/test/cases/inverse/expected1.txt").getCanonicalPath());
            Matrix matrix = new Matrix(sample);

            assertData(expected, Inverse.gaussJordanInverse(matrix).data);
            assertData(expected, Inverse.adjoinMethod(matrix).data);

            sample = Util.readFromFile(new File("src/test/cases/inverse/inverse2.txt").getCanonicalPath());
            expected = Util.readFromFile(new File("src/test/cases/inverse/expected2.txt").getCanonicalPath());
            matrix = new Matrix(sample);

            assertData(expected, Inverse.gaussJordanInverse(matrix).data);
            assertData(expected, Inverse.adjoinMethod(matrix).data);
        } catch (IOException e) {
            fail(e);
        }
    }
}
