import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tubes.matrix.Matrix;
import tubes.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MatrixTest {
    Matrix counting3x3;
    Matrix counting4x4;
    Matrix identity3x3;
    Matrix identity4x4;

    @BeforeEach
    void setupMatrix() throws IOException {
        float[][] c3 = Util.readFromFile(new File("src/test/cases/general/counting3x3.txt").getCanonicalPath());
        float[][] c4 = Util.readFromFile(new File("src/test/cases/general/counting4x4.txt").getCanonicalPath());
        float[][] i3 = Util.readFromFile(new File("src/test/cases/general/identity3x3.txt").getCanonicalPath());
        float[][] i4 = Util.readFromFile(new File("src/test/cases/general/identity4x4.txt").getCanonicalPath());

        counting3x3 = new Matrix(c3);
        counting4x4 = new Matrix(c4);
        identity3x3 = new Matrix(i3);
        identity4x4 = new Matrix(i4);
    }

    @Test
    void testConstructor() {
        float[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(data);

        assertTrue(Arrays.deepEquals(matrix.data, counting3x3.data));
    }

    @Test
    void testGetNs() {
        assertEquals(3, identity3x3.getNCols());
        assertEquals(4, identity4x4.getNRows());
    }

    @Test
    void testGetAsArray() {
        float[] row = {5, 6, 7, 8};
        float[] col = {2, 5, 8};
        float[] diag = {1, 1, 1, 1};

        assertArrayEquals(row, counting4x4.getRowAsArray(1));
        assertArrayEquals(col, counting3x3.getColAsArray(1));
        assertArrayEquals(diag, identity4x4.getDiagonalAsArray());
    }

    @Test
    void testArrayOperations() {
        float[] oneToThree = {1, 2, 3};
        float[] timesTwo = {2, 4, 6, 8};
        float[] divideTwo = {2.5f, 3, 3.5f, 4};
        float[] subtracted = {6, 6, 6};

        counting3x3.swapRow(0, 1);
        assertArrayEquals(oneToThree, counting3x3.getRowAsArray(1));

        counting4x4.multiplyRowByK(0, 2);
        assertArrayEquals(timesTwo, counting4x4.getRowAsArray(0));

        counting4x4.divideRowByK(1, 2);
        assertArrayEquals(divideTwo, counting4x4.getRowAsArray(1));

        counting3x3.subtractRowByArray(2, oneToThree);
        assertArrayEquals(subtracted, counting3x3.getRowAsArray(2));
    }

    @Test
    void testIdentityMatrix() {
        assertTrue(Arrays.deepEquals(Matrix.createIdentityMatrix(4).data, identity4x4.data));
        assertEquals(4, Matrix.createIdentityMatrix(4).getNRows());
        assertEquals(4, Matrix.createIdentityMatrix(4).getNCols());
    }

    @Test
    void testTranspose() throws IOException {
        float[][] transposeData = Util.readFromFile(new File("src/test/cases/matrixtests/transpose.txt").getCanonicalPath());
        Matrix transposeMatrix = new Matrix(transposeData);

        counting3x3.transpose();
        assertTrue(Arrays.deepEquals(transposeMatrix.data, counting3x3.data));
    }

    @Test
    void testMultiply() throws IOException {
        float[][] multiplierData = Util.readFromFile(new File("src/test/cases/matrixtests/multiplier.txt").getCanonicalPath());
        float[][] resultData = Util.readFromFile(new File("src/test/cases/matrixtests/multiplyresult.txt").getCanonicalPath());

        Matrix multiplier = new Matrix(multiplierData);
        Matrix result = new Matrix(resultData);

        assertTrue(Arrays.deepEquals(Matrix.multiplyMatrix(counting3x3, multiplier).data, result.data));
    }
}
