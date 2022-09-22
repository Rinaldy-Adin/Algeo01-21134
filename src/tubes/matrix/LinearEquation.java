package tubes.matrix;

public class LinearEquation {
    public static Matrix gaussianElimination(Matrix matrix) {
        int currentRow = 0;
        for (int j = 0; j < matrix.nCols && currentRow < matrix.nRows; j++) {
            for (int i = currentRow + 1; i < matrix.nRows && matrix.data[currentRow][j] == 0; i++) {
                if (matrix.data[i][j] != 0)
                    matrix.swapRow(currentRow, i);
            }


            if (matrix.data[currentRow][j] != 0) {
                matrix.divideRowByK(currentRow, matrix.data[currentRow][j]);

                for (int i = currentRow + 1; i < matrix.nRows; i++) {
                    float[] rowArray = matrix.getRowAsArray(currentRow);
                    for (int k = 0; k < matrix.nCols; k++) {
                        rowArray[k] *= matrix.data[i][j];
                    }

                    matrix.subtractRowByArray(i, rowArray);
                }

                currentRow++;
            }
        }

        matrix.removeNegativeZero();
        return matrix;
    }
}
