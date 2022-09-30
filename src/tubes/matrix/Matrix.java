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
        float[] arr = new float[nRows];

        for (int i = 0; i < nRows; i++) {
            arr[i] = this.data[i][C1];
        }

        return arr;
    }

    public float[] getDiagonalAsArray() {
        float[] arr = new float[nCols];

        for (int i = 0; i < nCols; i++) {
            arr[i] = this.data[i][i];
        }

        return arr;
    }

    public float[] getMatrixAsOneDimensionalArray() {
        float[] arr = new float[nCols * nRows];

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                arr[i * nCols + j] = this.data[i][j];
            }
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

    public void divideRowByK(int R1, float k) {
        multiplyRowByK(R1, (float) 1.0 / k);
    }

    public void subtractRowByArray(int R1, float[] arr) {
        for (int i = 0; i < arr.length && i < nCols; i++) {
            this.data[R1][i] -= arr[i];
        }
    }

    public static Matrix createIdentityMatrix(int n) {
        // Pre-condition : Matrix is a square
        Matrix identity = new Matrix(n, n);
        int i, j;

        for (i = 0; i < identity.nRows; i++) {
            for (j = 0; j < identity.nCols; j++) {
                if (j == i) {
                    identity.data[i][j] = 1;
                } else {
                    identity.data[i][j] = 0;
                }
            }
        }
        return identity;
    }

    public static Matrix transpose(Matrix matrix) {
      // Transpose the input matrix and outputs it as a new matrix

        Matrix transposeMatrix = new Matrix(matrix.getNCols(), matrix.getNRows());
        for (int i = 0; i < transposeMatrix.getNRows(); i++) {
            for (int j = 0; j < transposeMatrix.getNCols(); j++) {
                transposeMatrix.data[i][j] = matrix.data[j][i];
            }
        }
        return transposeMatrix;
    }

    public static Matrix copyMatrix(Matrix matrix) {
        Matrix tempCopy = new Matrix(matrix.nRows, matrix.nCols);
        for (int i = 0; i < matrix.nRows; i++) {
            tempCopy.data[i] = matrix.data[i].clone();
        }
        return tempCopy;
    }

    public static Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        int i, j, k;
        Matrix m = new Matrix(m1.nRows, m2.nCols);

        for (i = 0; i < m1.nRows; i++) {
            for (j = 0; j < m2.nCols; j++) {
                for (k = 0; k < m2.nRows; k++) {
                    m.data[i][j] += m1.data[i][k] * m2.data[k][j];
                }
            }
        }
        return m;
    }

    public static Matrix makeAugmented (Matrix a, Matrix b) {
        // Pre-condition : a.nRows == b.nRows
        Matrix aug = new Matrix(a.nRows, a.nCols+b.nCols);
        int i, j;

        for (i=0; i<aug.nRows; i++) {
            for (j=0; j<aug.nCols; j++) {
                if (j<a.nCols) {
                    aug.data[i][j] = a.data[i][j];
                }
                else {
                    aug.data[i][j] = b.data[i][j-a.nCols];
                }
            }
        }

        return aug;
    }
    
}
