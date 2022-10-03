package tubes.matrix;

public class Interpolation {
    // Create a coefficient matrix of the resulting function that is computed
    // using polynomial interpolation method
    public static Matrix polynomialInterpolation(Matrix points) {
        Matrix matrix = new Matrix(points.getNRows(), points.getNRows() + 1);

        for (int i = 0; i < matrix.getNRows(); i++) {
            for (int j = 0; j < matrix.getNCols(); j++) {
                if (j != matrix.getNCols() - 1) {
                    matrix.data[i][j] = Math.pow(points.data[i][0], j);
                } else {
                    matrix.data[i][j] = points.data[i][1];
                }
            }
        }

        double[] res = LinearEquation.gaussJordanElimination(matrix).getColAsArray(matrix.nCols - 1);
        Matrix mat = new Matrix(res.length, 1);
        for (int i = 0; i < res.length; i++) {
            mat.data[i][0] = res[i];
        }

        return mat;
    }

    public static double approximateFunction(Matrix points, double x) {
        // Approximate the value of function at x by taking several points as input
        // and then approximate the function using polynomial interpolation method

        Matrix coefficients = polynomialInterpolation(points);
        double output = 0.0;
        for (int i = 0; i < coefficients.getNRows(); i++) {
            output += coefficients.data[i][0] * Math.pow(x, i);
        }
        return output;
    }
}
