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

    public static Matrix gaussJordanElimination(Matrix matrix) {
        int i, j, k;

        // Forward phase, turn matrix into Echelon form
        LinearEquation.gaussianElimination(matrix);

        // Pivot = first non-zero element from the left of row
        // i increments reference row
        // j increments row to be operated
        // k traverse through row elements to be added/subtracted with another row

        // Loop through the 2nd row onwards
        for (i=1; i<matrix.nRows; i++) {
            // Find the index of pivot
            int colOfPivot = 0;
            while (matrix.data[i][colOfPivot] == 0 && colOfPivot < matrix.nCols) {
                colOfPivot++;
            }

            // Check if row didn't contain all zeros (pivot is a non-zero)
            if (matrix.data[i][colOfPivot] != 0) {
                // Operate every row above pivot with a non-zero element at the same column as pivot
                for (j=0; j<i && matrix.data[j][colOfPivot] != 0; j++) {
                    float ratio = matrix.data[j][colOfPivot]/matrix.data[i][colOfPivot];
                    for (k=0; k<matrix.nCols; k++) {
                        matrix.data[j][k] -= matrix.data[i][k]*ratio;
                    }
                }
            }
        }

        matrix.removeNegativeZero();
        return matrix;
    }
}
