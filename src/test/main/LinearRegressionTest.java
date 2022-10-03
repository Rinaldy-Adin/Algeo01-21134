import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tubes.matrix.LinearRegression;
import tubes.matrix.Matrix;
import tubes.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LinearRegressionTest {

    @Test
    void testApprox1() {
        try {
            double[][] samplex = Util.readFromFile(new File("src/test/cases/linearregression/linreg1x.txt").getCanonicalPath());
            double[][] sampley = Util.readFromFile(new File("src/test/cases/linearregression/linreg1y.txt").getCanonicalPath());
            double[][] samplek = {{50, 76, 29.3}};
            Matrix x = Matrix.transpose(new Matrix(samplex));
            Matrix y = new Matrix(sampley);
            Matrix k = new Matrix(samplek);

            double expected = 0.94106;
            assertEquals(expected, LinearRegression.approxMLR(x, y, k), 1e-2);
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void testEquation1() {
        try {
            double[][] samplex = Util.readFromFile(new File("src/test/cases/linearregression/linreg1x.txt").getCanonicalPath());
            double[][] sampley = Util.readFromFile(new File("src/test/cases/linearregression/linreg1y.txt").getCanonicalPath());
            Matrix x = Matrix.transpose(new Matrix(samplex));
            Matrix y = new Matrix(sampley);

            String expected = "f(X1, X2, X3) = -3.508 - 0.003 X1 + 0.001 X2 + 0.154 X3";
            assertEquals(expected, LinearRegression.writeMLREquation(x, y));
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void testEquation2() {
        try {
            double[][] samplex = Util.readFromFile(new File("src/test/cases/linearregression/linreg2x.txt").getCanonicalPath());
            double[][] sampley = Util.readFromFile(new File("src/test/cases/linearregression/linreg2y.txt").getCanonicalPath());
            Matrix x = Matrix.transpose(new Matrix(samplex));
            Matrix y = new Matrix(sampley);

            String expected = "f(X1, X2) = 3.919 + 2.491 X1 - 0.466 X2";
            assertEquals(expected, LinearRegression.writeMLREquation(x, y));
        } catch (IOException e) {
            fail(e);
        }
    }
}
