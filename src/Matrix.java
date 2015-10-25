import java.io.*;
import java.util.*;

/**
 * Created by Admin on 04.09.2015.
 */
public class Matrix implements Serializable  {
    private int width;
    private int height;
    private double matrix[][];

    public Matrix( int height, int width) throws IllegalArgumentException{
        if(height<1 || width<1) throw new IllegalArgumentException("Null size matrix");
        else {
            this.height = height;
            this.width = width;
            matrix = new double[height][width];
        }
    }

    public int getWidth() {
        width = matrix[0].length;
        return width;
    }

    public int getHeight() {
        height = matrix.length;
        return height;
    }

    public double getElementAt(int row, int column) throws ArrayIndexOutOfBoundsException{
        return matrix[row][column];
    }

    public void editElementAt(int row, int column, double value) throws ArrayIndexOutOfBoundsException
    {
        matrix[row][column] = value;
    }

    public static Matrix SumMatrix(Matrix matrix1, Matrix matrix2) throws Exception {
        if (matrix1.height == matrix2.height && matrix1.width == matrix2.width) {
            Matrix sum = new Matrix(matrix1.height, matrix1.width);
            for (int i = 0; i < matrix1.height; i++) {
                for (int j = 0; j < matrix1.width; j++) {
                    sum.editElementAt(i, j, matrix1.getElementAt(i, j) + matrix2.getElementAt(i, j));
                }
            }
            return sum;
        } else throw new Exception("Wrong size of matrix's");
    }

    public static void writeMatrixToFile(String FileName, Matrix matrix) throws  FileNotFoundException {
        int[] stringlengths = new int[matrix.getWidth()];
        for (int i = 0; i < matrix.getWidth(); i++) {
            for (int j = 0; j < matrix.getHeight(); j++) {
                Double cellval = matrix.getElementAt(j, i);
                if (cellval.toString().length() > stringlengths[i]) {
                    stringlengths[i] = cellval.toString().length();
                }
            }
            stringlengths[i] = stringlengths[i] + 2;
        }

        try (Formatter form = new Formatter(FileName)) {
            for (int i = 0; i < matrix.getHeight(); i++) {
                for (int j = 0; j < matrix.getWidth(); j++) {
                    Double cellval = matrix.getElementAt(i, j);
                    form.format("%" + stringlengths[j] + "s", cellval.toString());
                }
                form.format("%n");
            }
        }


    }

    public static Matrix loadMatrixFromFile(String FileName) throws IOException {
        Matrix mat;
        try (FileReader fr = new FileReader(FileName)) {
            int token;
            LinkedList<LinkedList<Double>> matr = new LinkedList<>();
            LinkedList<Double> Lines = new LinkedList<Double>();
            StreamTokenizer strt = new StreamTokenizer(fr);
            strt.parseNumbers();
            strt.eolIsSignificant(true);
            int maxLength = 0;

            token = strt.nextToken();
            while (token != StreamTokenizer.TT_EOF) {
                while (token != StreamTokenizer.TT_EOL && token != StreamTokenizer.TT_EOF) {
                    if (token == StreamTokenizer.TT_NUMBER) {
                        Lines.add(strt.nval);
                    }
                    token = strt.nextToken();
                }

                matr.add((LinkedList)Lines.clone());
                if (maxLength < Lines.size()) {
                    maxLength = Lines.size();
                }
                Lines.clear();
                token = strt.nextToken();
            }


            Double[][] matrix = new Double[matr.size()][maxLength];
            for (int k = 0; k < matr.size(); k++) {
                for (int n = 0; n < matr.get(k).size(); n++) {
                    matrix[k][n] = matr.get(k).get(n);
                }
                for(int u = matr.get(k).size(); u<maxLength; u++){
                    matrix[k][u] = 0.0;
                }
            }
            mat = new Matrix(matrix.length,matrix[0].length);
            for(int x=0;x<mat.getHeight();x++){
                for (int y=0;y<mat.getWidth();y++){
                    mat.editElementAt(x,y,matrix[x][y]);
                }
            }

        }

        return mat;

    }
}

