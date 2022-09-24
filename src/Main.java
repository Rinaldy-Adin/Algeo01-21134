import tubes.matrix.*;

public class Main {
    public static void main(String[] args) {
        float[][] data = {{1,2,3}, {2,5,3}, {1,0,8}};
        float[][] data2 = {{5}, {3}, {1}};

        Matrix coef = new Matrix(data);
        Matrix res = new Matrix(data2);
        coef.display();
        System.out.println();
        res.display();
        System.out.println();
        System.out.println("Inverse of coef: ");
        Inverse.gaussJordanInverse(coef).display();
        System.out.println();
        System.out.println("Multiply coef with res: ");
        Matrix.multiplyMatrix(coef, res).display();
        System.out.println();
        System.out.println("Multiply inverse coef with res: ");
        LinearEquation.solveLinearWithInverse(coef, res).display();
    }
}
