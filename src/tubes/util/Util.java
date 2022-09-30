package tubes.util;

import java.io.File;
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
      Path file = Path.of(pathString);
      String matrixString = "";
      try {
        matrixString = Files.readString(file);
      } catch (Exception e) {
        System.out.println(e);
      }

      int rows = 0, cols = 0;
      for(int i = 0; i < matrixString.length(); i++) {
        if(matrixString.charAt(i) == ' ') {
          cols++;
        } else if(matrixString.charAt(i) == '\n') {
          cols++;
          rows++;
        }
      }
      cols++;
      cols /= rows;

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
      try {
        Path file = Files.createFile(Path.of(folderPath).resolve(fileName));
        Files.writeString(file, outputString);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
}
