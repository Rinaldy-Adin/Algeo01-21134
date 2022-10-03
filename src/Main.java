import tubes.matrix.*;

public class Main {
    public static void main(String[] args) {
        double[][] data = {{1, 0, 3, 9}, {1, 0, 4, 11}, {0, 0, -10, -24}, {2, 7, 6, 9}};

        Matrix matrix = new Matrix(data);
        matrix.display();
        System.out.println();

        Inverse.gaussJordanInverse(matrix).display();
    }
}
