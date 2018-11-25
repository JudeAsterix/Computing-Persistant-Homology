/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author jandre
 */
public class DoubleDimension 
{
    private final int DIMENSION;
    private double[] coords;
    private final int ID_NUM;
   
    public DoubleDimension()
    {
        DIMENSION = 0;
        coords = null;
        this.ID_NUM = -1;
    }
    
    public DoubleDimension(double[] coords, int ID_NUM)
    {
        DIMENSION = coords.length;
        this.coords = coords;
        this.ID_NUM = ID_NUM;
    }
    
    public boolean setCoords(double[] newCoords, int ID_NUM)
    {
        if(newCoords.length == DIMENSION)
        {
            this.coords = newCoords;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void setCoord(double newCoord, int index)
    {
        coords[index] = newCoord;
    }
    
    public boolean intersectsWith(DoubleDimension coord, double ep)
    {
        boolean ret = true;
        for(int i = 0; i < this.DIMENSION; i++)
        {
            if(Math.abs(coord.getCoordAt(i) - getCoordAt(i)) >= 2 * ep)
            {
                ret = false;
            }
        }
        return ret;
    }
    
    public boolean equals(DoubleDimension dd)
    {
        boolean ret = true;
        for(int i = 0; i < dd.getDimension(); i++)
        {
            if(dd.getCoordAt(i) != this.getCoordAt(i))
            {
                ret = false;
            }
        }
        return ret;
    }
    
    public int getDimension()
    {
        return DIMENSION;
    }
    
    public double[] getCoords()
    {
        return this.coords;
    }
    
    public double getCoordAt(int index)
    {
        return this.coords[index];
    }
    
    public int getID()
    {
        return this.ID_NUM;
    }
    
    public String toString()
    {
        String s = "";
        s += "Object: Double Dimension \n";
        s += "Dimensions: " + DIMENSION + "\n";
        s += "Coordinates: " + stringCoords();
        s += "ID : " + ID_NUM;
        return s;
    }
    
    public String stringCoords()
    {
        String s = "";
        s += "[";
        for(int i = 0; i < DIMENSION; i++)
        {
            s += coords[i];
            if(i + 1 == DIMENSION)
            {
                s += "] \n";
            }
            else
            {
                s += ", ";
            }
        }
        return s;
    }
    
    public void show()
    {
        System.out.println(this.toString());
    }
}