package tubes.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {
    public static int indexOfVal(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val)
                return i;
        }

        return -1;
    }

    public static int indexOfVal(double[] arr, double val) {
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] == val)
                return i;
        }

        return -1;
    }

    private static double parseElmt(String str) {
      // Read data from a txt file and outputs it as a matrix
      // (The txt file must ends with newline character)
        if (str.contains("/")) {
            String[] rat = str.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(str);
        }
    }

    public static double[][] readFromFile(String pathString) {
        Path file = Path.of(pathString);
        String matrixString = "";
        try {
            matrixString = Files.readString(file);
        } catch (Exception e) {
            System.out.println(e);
        }

        int rows = 0, cols = 0;
        for (int i = 0; i < matrixString.length(); i++) {
            if (matrixString.charAt(i) == ' ') {
                cols++;
            } else if (matrixString.charAt(i) == '\n') {
                cols++;
                rows++;
            }
        }
        cols++;
        cols /= rows;

        double[][] data = new double[rows][cols];
        String dataStr = "";
        int j = 0, k = 0;
        for (int i = 0; i < matrixString.length(); i++) {
            if (matrixString.charAt(i) == ' ') {
                data[j][k] = parseElmt(dataStr);
                k++;
                dataStr = "";
            } else if (matrixString.charAt(i) == '\n') {
                data[j][k] = parseElmt(dataStr);
                j++;
                k = 0;
                dataStr = "";
            } else {
                dataStr += matrixString.charAt(i);
            }
        }

        return data;
    }

    public static void writeToFile(String fileName, String outputString) {
      // Write a string into a new txt file
      // (There must be no file with the same name in the new file location)
        try {
            String filePath = new File("").getAbsolutePath();
            filePath += "/output";
            Path file = Files.createFile(Path.of(filePath).resolve(fileName));
            Files.writeString(file, outputString);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
