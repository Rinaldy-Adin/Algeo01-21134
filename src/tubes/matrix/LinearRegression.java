package tubes.matrix;

public class LinearRegression {
    // n : Total of regressors
    // m : Total of samples
    // A : n*m matrix of sum of product of regressors (xi)
    // H : matrix of sum of product of regressors (xi) and dependent variables (y)
    // aug : augmented matrix of a|h

    public static Matrix getMtxA (Matrix x) {
        // x : Matrix of regressors 
        // [ [X1_1, X1_2, ... X1_m]
        //   [X2_1, X2_2, ... X2_m]
        //    ...   ...   ...  ...
        //   [Xn_1, Xn_2, ... Xn_m] ]

        Matrix a = new Matrix(x.nRows+1, x.nRows+1);
        int i, j, k;

        // Assign value to Matrix a
        for (i=0; i<a.nRows; i++) {
            for (j=0; j<a.nCols; j++) {
                // First element m
                if (i==0 && j==0) {
                    a.data[0][0] = x.nCols;
                }

                // First row : Sigma(Xi)
                // First column : Sigma(Xj)
                else if (i == 0 || j == 0) {
                    double sum = 0;
                    for (k=0; k<x.nCols; k++) {
                        if (i == 0) {
                            sum += x.data[j-1][k];
                        }
                        else if (j == 0) {
                            sum += x.data[i-1][k];
                        }
                    }
                    a.data[i][j] = sum;
                }

                // {Xij | i>0, j>0} : Sigma(Xik*Xjk)
                else {
                    double sumOfProduct = 0;
                    for (k=0; k<x.nCols; k++) {
                        sumOfProduct += x.data[i-1][k]*x.data[j-1][k];
                    }
                    a.data[i][j] = sumOfProduct;
                }
            }
        }
        
        return a;
    }

    public static Matrix getMtxH (Matrix x, Matrix y) {
        // x : Matrix of regressors 
        // [ [X1_1, X1_2, ... X1_m]
        //   [X2_1, X2_2, ... X2_m]
        //    ...   ...   ...  ...
        //   [Xn_1, Xn_2, ... Xn_m] ]

        // y : Column matrix of dependent variables
        // [ [Y1]
        //   [Y2]
        //   ...
        //   [Ym] ]

        Matrix h = new Matrix(x.nRows+1, 1);
        int i, j;

        // Initialize first element to be Sigma(y)
        double sumY = 0;
        for (j=0; j<y.nRows; j++) {
            sumY += y.data[j][0];
        }
        h.data[0][0] = sumY;

        // Sum of Product retrieved by Matrix Multiplication
        Matrix sumOfProduct = Matrix.multiplyMatrix(x, y);
        for (i=1; i<h.nRows; i++) {
            h.data[i][0] = sumOfProduct.data[i-1][0];
        }

        return h;
    }

    public static Matrix multipleLinearReg (Matrix x, Matrix y) {
        // Solve for b with Gauss-Jordan Elimination
        Matrix aug = new Matrix (Matrix.makeAugmented(LinearRegression.getMtxA(x), LinearRegression.getMtxH(x, y)).data.clone());
        return LinearEquation.gaussJordanElimination(aug);
    }

    public static void writeMLREquation (Matrix x, Matrix y) {
        Matrix aug = LinearRegression.multipleLinearReg(x, y);
        double[] solution = aug.getColAsArray(aug.nCols-1);
        System.out.print("Y = ");
        for (int i=0; i<solution.length; i++) {
            if (solution[i] != 0) {
                if (i == 0) {
                    System.out.printf("%.2f", (solution[i]));
                }
                else {
                    System.out.printf("%.2f", Math.abs(solution[i]));
                    System.out.print(" X" + (i));
                }
                
                if (i!=solution.length-1) {
                    if (solution[i+1] > 0) {
                        System.out.print(" + ");
                    }
                    else if (solution[i+1] < 0) {
                        System.out.print(" - ");
                    }
                }
            }
            else if (solution[i] == 0 && i != 0 && i != solution.length-1) {
                if (solution[i+1] > 0) {
                    System.out.print(" + ");
                }
                else if (solution[i+1] < 0) {
                    System.out.print(" - ");
                }
            }
        }
        System.out.println();
    }

    public static double approxMLR (Matrix x, Matrix y, Matrix k) {
        // k : Matrix of x values that are to be approximated
        Matrix aug = LinearRegression.multipleLinearReg(x, y);
        double[] solution = aug.getColAsArray(aug.nCols-1);
        double sumY = solution[0];
        for (int i=1; i<solution.length; i++) {
            sumY += solution[i]*k.data[0][i-1];
        }
        return sumY;
    }
}
