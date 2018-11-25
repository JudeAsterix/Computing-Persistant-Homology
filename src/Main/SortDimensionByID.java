/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Comparator;

/**
 *
 * @author kudi
 */
public class SortDimensionByID implements Comparator<DoubleDimension>{

    public int compare(DoubleDimension o1, DoubleDimension o2) {
        return(o1.getID() - o2.getID());
    }
    
}
