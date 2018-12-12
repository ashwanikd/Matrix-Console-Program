package com.company;

public class Matrix {
    int row,col,n;
    double matrix[][];

    //################################################################################################################//

    Matrix(int a,int b){
        row = a;
        col = b;
        if(row > col){
            n = (int)Math.ceil((Math.log((double)row)/Math.log(2.0)));
        }else {
            n = (int)Math.ceil((Math.log((double)col)/Math.log(2.0)));
        }
        n = (int) Math.pow(2.0,(double)n);
        matrix = new double[n][n];
    }

    //################################################################################################################//

    //################################################################################################################//

    boolean set(int i,int j,double value) throws MatrixException{
        if(i>=row || j>=col){
            throw new MatrixException("operation out of matrix bounds");
        }
        matrix[i][j] = value;
        return true;
    }

    //################################################################################################################//

    //################################################################################################################//

    double get(int i,int j){
        return matrix[i][j];
    }

    //################################################################################################################//

    //################################################################################################################//
    // k and l are row and column coordinates
    // sr and sc specify the size of submatrix
    Matrix submatrix(int k,int l,int sr,int sc){
        Matrix m = new Matrix(sr,sc);
        for(int i=0;i<sr;i++){
            for(int j=0;j<sc;j++){
                try {
                    m.set(i, j, matrix[k + i][l + j]);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return m;
    }

    //################################################################################################################//

    //################################################################################################################//

    void printMatrix(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    //################################################################################################################//

    //################################################################################################################//

    class MatrixException extends Exception{
        MatrixException(String message){
            super(message);
        }
    }
}
