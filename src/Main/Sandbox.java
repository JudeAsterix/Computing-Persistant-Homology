/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

public class Sandbox {
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
        ChainComplex oc = new ChainComplex("test.txt");
        double[][] mat1 = oc.getFunctionMatrixAtIndex(0);
        double[][] mat2 = oc.getFunctionMatrixAtIndex(1);
        double[][] trans1 = MatrixReducer.findColumnEchelonForm(mat2);
        MatrixReducer.printArray(trans1);
    }
    
}
