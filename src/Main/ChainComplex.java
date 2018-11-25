package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChainComplex {
    private final int NUM_OF_DATAPOINTS;
    private final int POINT_DIMENSION;
    private final double EPSILON;
    private DoubleDimension[] datapoints;
    private boolean[][] intersects;
    private ArrayList<ArrayList<DoublePolygon>> simplices = new ArrayList<>();
    private ArrayList<double[][]> functionMatrix = new ArrayList<>();
    
    public ChainComplex()
    {
        this.NUM_OF_DATAPOINTS = 0;
        this.POINT_DIMENSION = 0;
        this.EPSILON = 0;
        this.datapoints = null;
        intersects = new boolean[datapoints.length][datapoints.length];
        getGroups();
    }
    
    public ChainComplex(String st) throws FileNotFoundException
    {
        URL url = getClass().getResource(st);
        FileReader f = new FileReader(url.getPath());
        Scanner scan = new Scanner(f);
        this.EPSILON = Double.parseDouble(scan.nextLine());
        this.NUM_OF_DATAPOINTS = Integer.parseInt(scan.nextLine());
        this.POINT_DIMENSION = Integer.parseInt(scan.nextLine());
        datapoints = new DoubleDimension[NUM_OF_DATAPOINTS];
        intersects = new boolean[datapoints.length][datapoints.length];
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        for(int i = 0; i < NUM_OF_DATAPOINTS; i++)
        {
            String s = scan.nextLine();
            String[] sp = s.split(" ");
            double[] nums = new double[sp.length];
            for(int j = 0; j < nums.length; j++)
            {
                try {
                    nums[j] = nf.parse(sp[j]).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(ChainComplex.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            datapoints[i] = new DoubleDimension(nums, i);
        }
        getGroups();
        getMatrices();
    }
    
    public String toString()
    {
        String s = "";
        s += "Number of data points: " + this.NUM_OF_DATAPOINTS + "\n";
        s += "Dimension of data points: " + this.POINT_DIMENSION + "\n";
        s += "Epsilon: " + this.EPSILON + "\n";
        s += "Datapoints \n====================\n";
        for(DoubleDimension datapoint: datapoints)
        {
            s += datapoint.toString() + "\n";
        }
        s += "Simplices \n=====================\n";
        for(int i = 0; i < simplices.size(); i++)
        {
            s += "Dimension " + i + " - Number of simplices : " + simplices.get(i).size() + " \n";
            for(DoublePolygon dp : simplices.get(i))
            {
                s += dp.toString() + "\n";
            }
            s += "----------------------------- \n" ;
        }
        return s;
    }
    
    public String showIDs()
    {
        String s = "";
        s += "Simplices \n=====================\n";
        for(int i = 0; i < simplices.size(); i++)
        {
            s += "Dimension " + i + " - Number of simplices : " + simplices.get(i).size() + " \n";
            for(DoublePolygon dp : simplices.get(i))
            {
                s += dp.showIDs() + "\n";
            }
            s += "----------------------------- \n" ;
        }
        return s;
    }
    
    public void show()
    {
        System.out.println(toString());
    }
    
    public void getGroups()
    {
        int i = 0;
        simplices.add(new ArrayList<>());
        while(i <= POINT_DIMENSION && (i >= 0 || !simplices.get(i).isEmpty()))
        {
            if(i == 0)
            {
                for(DoubleDimension dd: datapoints)
                {
                    DoubleDimension[] d = {dd};
                    simplices.get(i).add(new DoublePolygon(d));
                }
            }
            else if(i == 1)
            {
                for(int j = 0; j < datapoints.length; j++)
                {
                    for(int k = j + 1; k < datapoints.length; k++)
                    {
                        if(datapoints[j].intersectsWith(datapoints[k], EPSILON))
                        {
                            DoubleDimension[] d = {datapoints[k], datapoints[j]};
                            DoublePolygon dp = new DoublePolygon(d);
                            dp.sort();
                            simplices.get(i).add(dp);
                            intersects[j][k] = true;
                        }
                        else
                        {
                            intersects[j][k] = false;
                        }
                    }
                }
            }
            else
            {
                for(int j = 0; j < simplices.get(i - 1).size(); j++)
                {
                    for(int k = j + 1; k < simplices.get(i - 1).size(); k++)
                    {
                        if(simplices.get(i - 1).get(j).numberOfCommonPoints(simplices.get(i - 1).get(k)) == i - 1)
                        {
                            DoublePolygon min = simplices.get(i - 1).get(j).minus(simplices.get(i - 1).get(k));
                            if(intersects[min.getPointAt(0).getID()][min.getPointAt(1).getID()])
                            {
                               ArrayList<DoubleDimension> like = simplices.get(i - 1).get(j).mutexAnd(simplices.get(i - 1).get(k)).getPointsInArrayList();
                               ArrayList minx = min.getPointsInArrayList();
                               like.addAll(minx);
                               DoublePolygon newP = new DoublePolygon(like);
                               newP.sort();
                               simplices.get(i).add(newP);
                            }
                        }
                    }
                }
            }
            for(int j = simplices.get(i).size() - 1; j >= 0; j--)
            {
                for(int k = j - 1; k >= 0; k--)
                {
                    if(simplices.get(i).get(j).minus(simplices.get(i).get(k)).getNumOfPoints() == 0)
                    {
                        simplices.get(i).remove(k);
                        j--;
                    }
                }
            }
            
            simplices.add(new ArrayList<>());
            i++;
        }
        while(simplices.get(simplices.size() - 1).isEmpty())
        {
            simplices.remove(simplices.size() - 1);
        }
    }
    
    public void getMatrices()
    {
        for(int i = 0; i < simplices.size() - 1; i++)
        {
            double[][] temp = (new double[simplices.get(i).size()][simplices.get(i + 1).size()]);
            int[] isNegative = new int[temp[0].length];
            for(int j = 0; j < isNegative.length; j++)
            {
                isNegative[j] = i % 2;
            }
            for(int j = 0; j < temp.length; j++)
            {
                for(int k = 0; k < temp[j].length; k++)
                {
                    DoublePolygon likes = simplices.get(i).get(j).mutexAnd(simplices.get(i + 1).get(k));
                    if(likes.getPoints().length == i + 1)
                    {
                            int e = simplices.get(i).indexOf(simplices.get(i).get(j));
                            int f = simplices.get(i + 1).indexOf(simplices.get(i + 1).get(k));
                            temp[e][f] = (2 * isNegative[k]) - 1;
                            isNegative[k] = (isNegative[k] + 1) % 2;
                    }
                }
            }
            
            functionMatrix.add(new double[temp[0].length][temp.length]);
            
            for(int x = 0; x < functionMatrix.get(i).length; x++)
            {
                for(int y = 0; y < functionMatrix.get(i)[x].length; y++)
                {
                    functionMatrix.get(i)[x][y] = temp[y][x];
                }
            }
            isNegative = new int[simplices.get(i + 1).size()];
            for(int n : isNegative)
            {
                n = ((i + 1) % 2);
            }
        }
    }
    
    public String showMatrices()
    {
        String s = "";
        for(int x = 0; x < functionMatrix.size(); x++)
        {
            for(int i = 0; i < functionMatrix.get(x).length; i++)
            {
                for(int j = 0; j < functionMatrix.get(x)[i].length; j++)
                {
                    s += functionMatrix.get(x)[i][j] + "\t";
                }
                s += "\n";
            }
            s += "\n";
        }
        
        return s;
    }
}
