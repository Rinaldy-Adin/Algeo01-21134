package tubes.matrix;

import tubes.util.Util;

public class Determinant {
    public static double rowReduction(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);
        double det = 1;

        int currentRow = 0;
        for (int j = 0; j < matrix.nCols && currentRow < matrix.nRows; j++) {
            for (int i = currentRow + 1; i < matrix.nRows && matrix.data[currentRow][j] == 0; i++) {
                if (matrix.data[i][j] != 0) {
                    matrix.swapRow(currentRow, i);
                    det *= -1;
                }
            }


            if (matrix.data[currentRow][j] != 0) {
                det *= matrix.data[currentRow][j];
                matrix.divideRowByK(currentRow, matrix.data[currentRow][j]);

                for (int i = currentRow + 1; i < matrix.nRows; i++) {
                    double[] rowArray = matrix.getRowAsArray(currentRow);
                    for (int k = 0; k < matrix.nCols; k++) {
                        rowArray[k] *= matrix.data[i][j];
                    }

                    matrix.subtractRowByArray(i, rowArray);
                }

                currentRow++;
            }
        }

        double[] diag = matrix.getDiagonalAsArray();
        if (Util.indexOfVal(diag, 0) != -1) {
            det = 0;
        }

        return det;
    }

    public static double cofactor(Matrix matrixIn) {
        Matrix matrix = Matrix.copyMatrix(matrixIn);

        if (matrix.getNCols() == 2 && matrix.getNRows() == 2) {
            return matrix.data[0][0] * matrix.data[1][1] - matrix.data[0][1] * matrix.data[1][0];
        } else {
            double determinant = 0.0;
            for (int k = 0; k < matrix.getNRows(); k++) {
                Matrix minor = new Matrix(matrix.getNRows() - 1, matrix.getNCols() - 1);

                for (int i = 0; i < k; i++) {
                    for (int j = 1; j < matrix.getNCols(); j++) {
                        minor.data[i][j - 1] = matrix.data[i][j];
                    }
                }
                for (int i = k + 1; i < matrix.getNRows(); i++) {
                    for (int j = 1; j < matrix.getNCols(); j++) {
                        minor.data[i - 1][j - 1] = matrix.data[i][j];
                    }
                }

                if (k % 2 == 0) {
                    determinant += matrix.data[k][0] * cofactor(minor);
                } else {
                    determinant += -1.0 * matrix.data[k][0] * cofactor(minor);
                }
            }

            return determinant;
        }
    }
}
