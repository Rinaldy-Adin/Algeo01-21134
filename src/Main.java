import tubes.matrix.Matrix;

public class Main {

    public static void main(String[] args) {
        float[][] data= {{1,0,3, 9}, {1,0,4, 11}, {0,0,-10, -24}};

        Matrix matrix = new Matrix(data);
        matrix.display();
        System.out.println();

        Matrix.gaussianElimination(matrix).display();
    }
}
