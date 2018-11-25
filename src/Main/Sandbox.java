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
        oc.show();
        System.out.println(oc.showIDs());
        System.out.println(oc.showMatrices());
        
    }
    
}
