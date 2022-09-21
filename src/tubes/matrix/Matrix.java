package tubes.matrix;

public class Matrix {
    public float[][] data;
    protected int nRows, nCols;

    public Matrix(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;

        this.data = new float[nRows][nCols];
    }

    public Matrix(float[][] data) {
        this.data = data;
        this.nRows = data.length;
        this.nCols = data[0].length;
    }

    public int getNRows() {
        return this.nRows;
    }

    public int getNCols() {
        return this.nCols;
    }

    public void display() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.printf("%.2f ", this.data[i][j]);
            }
            System.out.println("");
        }
    }

    public float[] getRowAsArray(int R1) {
        float[] arr = new float[nCols];

        for (int i = 0; i < nCols; i++) {
            arr[i] = this.data[R1][i];
        }

        return arr;
    }

    public float[] getColAsArray(int C1) {
        float[] arr = new float[nCols];

        for (int i = 0; i < nCols; i++) {
            arr[i] = this.data[i][C1];
        }

        return arr;
    }

    public void swapRow(int R1, int R2) {
        if (R1 >= nRows || R2 >= nRows || R1 == R2) return;

        for (int j = 0; j < nCols; j++) {
            float tmp = data[R1][j];
            data[R1][j] = data[R2][j];
            data[R2][j] = tmp;
        }
    }

    public void swapCol(int C1, int C2) {
        if (C1 >= nCols || C2 >= nCols || C1 == C2) return;

        for (int i = 0; i < nRows; i++) {
            float tmp = data[i][C1];
            data[i][C1] = data[i][C2];
            data[i][C2] = tmp;
        }
    }

    public void removeNegativeZero() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                data[i][j] += 0.0;
            }
        }
    }

    public void multiplyRowByK(int R1, float k) {
        for (int j = 0; j < nCols; j++) {
            data[R1][j] *= k;
        }
    }

    public void multiplyColByK(int C1, float k) {
        for (int i = 0; i < nRows; i++) {
            data[i][C1] *= k;
        }
    }

    public void divideRowByK(int R1, float k) {
        multiplyRowByK(R1, (float)1.0/k);
    }

    public void divideColByK(int C1, float k) {
        multiplyColByK(C1, (float)1.0/k);
    }

    public void subtractRowByArray(int R1, float[] arr) {
        for (int i = 0; i < arr.length && i < nCols; i++) {
            this.data[R1][i] -= arr[i];
        }
    }

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
