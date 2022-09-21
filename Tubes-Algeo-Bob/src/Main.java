import matrix.Matrix;

public class Main {

    public static void main(String[] args) {
        float[][] data= {{1,2,3, 9}, {1,3,4, 11}, {0,-6,-10, -24}};

        Matrix matrix = new Matrix(data);
        matrix.display();
        System.out.println();

        Matrix.gaussianElimination(matrix).display();
    }
}
