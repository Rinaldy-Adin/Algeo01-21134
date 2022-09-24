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

    public float[] getDiagonalAsArray() {
        float[] arr = new float[nCols];

        for (int i = 0; i < nCols; i++) {
            arr[i] = this.data[i][i];
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
        multiplyRowByK(R1, (float)1.0/k);
    }

    public void subtractRowByArray(int R1, float[] arr) {
        for (int i = 0; i < arr.length && i < nCols; i++) {
            this.data[R1][i] -= arr[i];
        }
    }

    public void transpose() {
      Matrix temp = new Matrix(nRows, nCols);
      for(int i = 0; i < nRows; i++) {
        for(int j = 0; j < nCols; j++) {
          temp.data[i][j] = data[i][j];
        }
      }

      for(int i = 0; i < nRows; i++) {
        for(int j = 0; j < nCols; j++) {
          data[i][j] = temp.data[j][i];
        }
      }
    }
}
