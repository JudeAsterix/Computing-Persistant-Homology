/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.ArrayList;

/**
 *
 * @author kudi
 */
public class DoublePolygon {
    private DoubleDimension[] points;
    private final int POINTDIMENSION;
    private final int NUMPOINTS;
    
    DoublePolygon()
    {
        this.points = null;
        this.NUMPOINTS = 0;
        this.POINTDIMENSION = 0;
    }
    
    DoublePolygon(DoubleDimension[] points)
    {
        this.points = points;
        this.NUMPOINTS = points.length;
        if(this.NUMPOINTS == 0)
        {
            this.POINTDIMENSION = 0;
        }
        else
        {
            this.POINTDIMENSION = points[0].getDimension();
        }
    }
    
    DoublePolygon(ArrayList<DoubleDimension> points)
    {
        this.points = new DoubleDimension[points.size()];
        for(int i = 0; i < this.points.length; i++)
        {
            this.points[i] = points.get(i);
        }
        this.NUMPOINTS = this.points.length;
        if(this.NUMPOINTS == 0)
        {
            this.POINTDIMENSION = 0;
        }
        else
        {
            this.POINTDIMENSION = this.points[0].getDimension();
        }
    }
    
    public int getDimensions()
    {
        return this.POINTDIMENSION;
    }
    
    public int getNumOfPoints()
    {
        return this.NUMPOINTS;
    }
    
    public DoubleDimension[] getPoints()
    {
        return this.points;
    }
    
    public DoubleDimension getPointAt(int index)
    {
        return this.points[index];
    }
    
    public String toString()
    {
        String s = "";
        s += "Object: Double Polygon \n";
        s += "Number of Points: " + this.NUMPOINTS + "\n";
        s += "Dimension of Points: " + this.POINTDIMENSION + "\n";
        for(int i = 0; i < points.length; i++)
        {
            s += points[i].stringCoords();
        }
        s += "\n";
        s += "IDs : [";
        for(int i = 0; i < points.length; i++)
        {
            s += points[i].getID();
            
            if(i + 1 != points.length)
            {
                s += ", ";
            }
            else
            {
                s += "]";
            }
        }
        return s;
    }
    
    public String showIDs()
    {
        String s = "";
        s += "IDs : [";
        for(int i = 0; i < points.length; i++)
        {
            s += points[i].getID();
            
            if(i + 1 != points.length)
            {
                s += ", ";
            }
            else
            {
                s += "]";
            }
        }
        return s;
    }
    
    public int numberOfCommonPoints(DoublePolygon dp)
    {
        int ret = 0;
        for(int i = 0; i < this.getNumOfPoints(); i++)
        {
            for(int j = 0; j < dp.getNumOfPoints(); j++)
            {
                if(this.getPointAt(i) == dp.getPointAt(j))
                {
                    ret++;
                }
            }
        }
        
        return ret;
    }
    
    public DoublePolygon minus(DoublePolygon dp)
    {
        DoublePolygon likes = this.mutexAnd(dp);
        ArrayList<DoubleDimension> likePoints = likes.getPointsInArrayList();
        ArrayList<DoubleDimension> points1 = this.getPointsInArrayList();
        ArrayList<DoubleDimension> points2 = dp.getPointsInArrayList();
        points1.removeAll(likePoints);
        points2.removeAll(likePoints);
        points1.addAll(points2);
        return new DoublePolygon(points1);
    }
    
    public DoublePolygon mutexAnd(DoublePolygon dp)
    {
        ArrayList<DoubleDimension> points = new ArrayList<>();
        for(int i = 0; i < this.getNumOfPoints(); i++)
        {
            for(int j = 0; j < dp.getNumOfPoints(); j++)
            {
                if(this.getPointAt(i) == dp.getPointAt(j))
                {
                    points.add(this.getPointAt(i));
                }
            }
        }
        return(new DoublePolygon(points));
    }
    
    public DoublePolygon deepCopy()
    {
        return new DoublePolygon(points);
    }
    
    public ArrayList<DoubleDimension> getPointsInArrayList()
    {
        ArrayList<DoubleDimension> ret = new ArrayList<>();
        for(DoubleDimension point: points)
        {
            ret.add(point);
        }
        return ret;
    }
    
    public void sort()
    {
        ArrayList<DoubleDimension> temp = getPointsInArrayList();
        temp.sort(new SortDimensionByID());
        for(int i = 0; i < temp.size(); i++)
        {
            points[i] = temp.get(i);
        }
    }
    
    public void show()
    {
        System.out.println(toString());
    }
}
