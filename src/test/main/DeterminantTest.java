import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tubes.matrix.BicubicInterpolation;
import tubes.matrix.Determinant;
import tubes.matrix.LinearEquation;
import tubes.matrix.Matrix;
import tubes.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DeterminantTest {
    @Test
    void testDeterminant() {
        try {
            double[][] sample = Util.readFromFile(new File("src/test/cases/determinant/determinant1.txt").getCanonicalPath());
            Matrix matrix = new Matrix(sample);

            assertEquals(31.375, Determinant.rowReduction(matrix), 1e-3);
            assertEquals(31.375, Determinant.cofactor(matrix), 1e-3);

            sample = Util.readFromFile(new File("src/test/cases/determinant/determinant2.txt").getCanonicalPath());
            matrix = new Matrix(sample);

            assertEquals(-4, Determinant.rowReduction(matrix), 1e-3);
            assertEquals(-4, Determinant.cofactor(matrix), 1e-3);
        } catch (IOException e) {
            fail(e);
        }
    }
}
