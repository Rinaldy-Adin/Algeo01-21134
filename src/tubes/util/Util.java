package tubes.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class Util {
    public static int indexOfVal(int[] arr, int val) {
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] == val)
                return i;
        }

        return -1;
    }

    public static int indexOfVal(float[] arr, float val) {
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] == val)
                return i;
        }

        return -1;
    }

    public static float[][] readFromFile(String pathString) {
      // Read data from a txt file and outputs it as a matrix
      // (The txt file must ends with newline character)

      Path file = Path.of(pathString);
      String matrixString = "";
      try {
        matrixString = Files.readString(file);
      } catch (Exception e) {
        System.out.println(e);
      }

      int rows = 0, cols = 0;
      int currentCols = 0;
      for(int i = 0; i < matrixString.length(); i++) {
        if(matrixString.charAt(i) == ' ') {
          currentCols++;
        } else if(matrixString.charAt(i) == '\n') {
          currentCols++;
          rows++;
          if(currentCols > cols) {
            cols = currentCols;
          }
          currentCols = 0;
        }
      }

      float[][] data = new float[rows][cols];
      String dataStr = "";
      int j = 0, k = 0;
      for(int i = 0; i < matrixString.length(); i++) {
        if(matrixString.charAt(i) == ' ') {
          data[j][k] = Float.parseFloat(dataStr);
          k++;
          dataStr = "";
        } else if(matrixString.charAt(i) == '\n') {
          data[j][k] = Float.parseFloat(dataStr);
          j++;
          k = 0;
          dataStr = "";
        } else {
          dataStr += matrixString.charAt(i);
        }
      }

      return data;
    }

    public static void writeToFile(String folderPath, String fileName, String outputString) {
      // Write a string into a new txt file
      // (There must be no file with the same name in the new file location)

      try {
        Path file = Files.createFile(Path.of(folderPath).resolve(fileName));
        Files.writeString(file, outputString);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
}
