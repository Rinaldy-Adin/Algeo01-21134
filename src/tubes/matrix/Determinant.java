package tubes.matrix;

import tubes.util.Util;

public class Determinant {
    public static float rowReduction(Matrix matrix) {
        float det = 1;

        int currentRow = 0;
        for (int j = 0; j < matrix.nCols && currentRow < matrix.nRows; j++) {
            for (int i = currentRow + 1; i < matrix.nRows && matrix.data[currentRow][j] == 0; i++) {
                if (matrix.data[i][j] != 0) {
                    matrix.swapRow(currentRow, i);
                    det *= -1;
                }
            }


            if (matrix.data[currentRow][j] != 0) {
                matrix.divideRowByK(currentRow, matrix.data[currentRow][j]);
                det /= matrix.data[currentRow][j];

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

        float[] diag = matrix.getDiagonalAsArray();
        if (Util.indexOfVal(diag, 0) != -1) {
            det = 0;
        }

        return det;
    }
}
