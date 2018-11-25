package Main;

import java.util.ArrayList;

public final class MatrixReducer {
    
    public static double[][] findColumnEchelonForm(double[][] array)
    {
        ArrayList<double[][]> transformMatrices = new ArrayList<>();
        int min = 0;
        if(array.length < array[0].length)
        {
            min = array.length;
        }
        else
        {
            min = array[0].length;
        }
        
        //Tries to create a non-zero for each array[i][i]
        for(int y = 0; y < min; y++)
        {
            double[][] arr = findNonZeroColumn(array, y, min);
            if(arr != null)
            {
                transformMatrices.add(arr);
            }
        }
            
        for(int x = min - 1; x >= 0; x--)
        {
            if(array[x][x] != 0)
            {
               transformMatrices.add(multiplyColumnByMultiple(array, x, 1 / array[x][x]));
                for(int y = x - 1; y >= 0; y--)
                {
                    transformMatrices.add(addColumnByMultipleOfColumn(array, y, x, -array[x][y]));
                    if(y < min && array[y][y] == 0)
                    {
                        double[][] arr = findNonZeroColumn(array, y, min);
                        if(arr != null)
                        {
                            transformMatrices.add(arr);
                        }
                    }
                } 
            }
        }
        
        //Tranforms Matrix into upper triangular matrix
        double[][] tempArr = deepCopyArray(array);
        int counter = 0;
        boolean isTheSame = false;
        while(!isTheSame)
        {
            //Creates the matrix in row 
            for(int x = 0; x < min;  x++)
            {
                for(int y = x + 1; y < array[0].length; y++)
                {
                    transformMatrices.add(addColumnByMultipleOfColumn(array, y, x, -array[x][y]));
                    if(y < min && array[y][y] == 0)
                    {
                        double[][] arr = findNonZeroColumn(array, y, min);
                        if(arr != null)
                        {
                            transformMatrices.add(arr);
                        }
                    }
                }
            }
            
            for(int x = min - 1; x >= 0; x--)
            {
                if(array[x][x] != 0)
                {
                   transformMatrices.add(multiplyColumnByMultiple(array, x, 1 / array[x][x]));
                    for(int y = x - 1; y >= 0; y--)
                    {
                        transformMatrices.add(addColumnByMultipleOfColumn(array, y, x, -array[x][y]));
                        if(y < min && array[y][y] == 0)
                        {
                            double[][] arr = findNonZeroColumn(array, y, min);
                            if(arr != null)
                            {
                                transformMatrices.add(arr);
                            }
                        }
                    } 
                }
            }
            
            if(!areTheSameArrays(tempArr, array))
            {
                tempArr = array.clone();
                counter++;
            }
            else
            {
                isTheSame = true;
            }
        }
        System.out.println(counter);
        /*for(int i = 0; i < transformMatrices.size(); i++)
        {
            printArray(transformMatrices.get(i));
        }*/
        
        for (double[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                if (Double.toString(array1[j]).equals("-0.0")) {
                    array1[j] = 0.0;
                }  
            }
        }
        
        double[][] result = transformMatrices.get(0);
        
        for(int i = 1; i < transformMatrices.size(); i++)
        {
            result = multiplyArrays(result, transformMatrices.get(i));
        }
        return result;
    }
    
    private static double[][] findNonZeroColumn(double[][] array, int col, int min)
    {
        int y1 = 0;
        while(array[col][col] == 0 && y1 < array[0].length)
        {
            if((y1 > col && (y1 >= min || (array[y1][col] != 0 && array[col][y1] != 0)) || 
                    y1 < col && array[y1][col] != 0))
            {
                return(switchColumns(array, col, y1));
            }
            y1++;
        }
        return null;
    }
    
    //Looking at an array, add the 
    public static double[][] addColumnByMultipleOfColumn(double[][] array, int col, int colAdd, double multiple)
    {
        for (double[] array1 : array) {
            if (array1[colAdd] != 0 && multiple != 0) {
                array1[col] += (array1[colAdd] * multiple);
            }
        }
        double[][] ret = new double[array[0].length][array[0].length];
        for(int i = 0; i < ret.length; i++)
        {
            for(int j = 0; j < ret.length; j++)
            {
                if(i == j)
                {
                    ret[i][j] = 1;
                }
                else if(j == col && i == colAdd)
                {
                    ret[i][j] = multiple;
                }
                else
                {
                    ret[i][j] = 0;
                }
            }
        }
        return ret;
    }
    
    public static double[][] multiplyColumnByMultiple(double[][] array, int x, double multiple)
    {
        for (double[] array1 : array) {
            if (array1[x] == 0 || multiple == 0) {
                array1[x] = 0;
            } else {
                array1[x] *= multiple;
            }
        }
        
        double[][] ret = new double[array[0].length][array[0].length];
        
        for(int i = 0; i < ret.length; i++)
        {
            for(int j = 0; j < ret.length; j++)
            {
                if(i == j)
                {
                    if(i == x)
                    {
                        ret[i][j] = multiple;
                    }
                    else
                    {
                        ret[i][j] = 1;
                    }
                }
                else
                {
                    ret[i][j] = 0;
                }
            }
        }
        
        return ret;
    }
    
    public static double[][] switchColumns(double[][] array, int col1, int col2)
    {
        for (double[] array1 : array) {
            double temp = array1[col1];
            array1[col1] = array1[col2];
            array1[col2] = temp;
        }
        
        double[][] ret = new double[array[0].length][array[0].length];
        
        for(int i = 0; i < ret.length; i++)
        {
            for(int j = 0; j < ret.length; j++)
            {
                if((i == col1 && j == col2) || (i == col2 && j == col1) || (i == j && (i != col1 && i != col2)))
                {
                    ret[i][j] = 1;
                }
                else
                {
                    ret[i][j] = 0;
                }
            }
        }
        return ret;
    }
    
    public static double[][] multiplyArrays(double[][] array1, double[][] array2)
    {
        double[][] result = new double[array1.length][array2[0].length];
        for(int i = 0; i < array1.length; i++)
        {
            for(int j = 0; j < array2[0].length; j++)
            {
                double sum = 0;
                for(int k = 0; k < array2.length; k++)
                {
                    if(array1[i][k] != 0 && array2[k][j] != 0)
                    {
                        sum += array1[i][k] * array2[k][j];
                    }
                }
                if(Math.abs(Math.round(sum) - sum) < 0.0000000000001)
                {
                    sum = Math.round(sum);
                }
                result[i][j] = sum;
            }
        }
        return result;
    }
    
    public static double[][] deepCopyArray(double[][] array)
    {
        double[][] ret = new double[array.length][array[0].length];
        for(int i = 0; i < array.length; i++)
        {
            for(int j = 0; j < array[i].length; j++)
            {
                ret[i][j] = array[i][j];
            }
        }
        return ret;
    }
    
    public static boolean areTheSameArrays(double[][] arr1, double[][] arr2)
    {
        boolean ret = true;
        for(int j = 0; j < arr1.length; j++)
        {
            for(int i = 0; i < arr1[j].length; i++)
            {
                if(arr1[j][i] != arr2[j][i])
                {
                    ret = false;
                }
            }
        }
        return ret;
    }
    
    public static double[][] findInverse(double[][] array)
    {
        double[][] ret = new double[array.length][array.length];
        double det = findDeterminant(array);
        try
        {
            if(det == 0)
            {
                throw new ArithmeticException();
            }
            double recip = 1.0 / det;
            for(int i = 0; i < ret.length; i++)
            {
                for(int j = 0; j < ret.length; j++)
                {
                    ret[i][j] = array[i][j] * recip;
                }
            }
        }
        catch(ArithmeticException e)
        {
            System.out.println("Determinant is zero. There doesn't exist an inverse for this matrix.");
        }
        return ret;
    }
    
    public static double findDeterminant(double[][] array)
    {
        double ret = 0;
        if(array.length == 2)
        {
            ret = (array[0][0] * array[1][1] - array[1][0] * array[0][1]);
        }
        else
        {
            for(int i = 0; i < array.length; i++)
            {
                double[][] miniArr = new double[array.length - 1][array.length - 1];
                for(int j = 0; j < array.length - 1; j++)
                {
                    for(int k = 0; k < array.length - 1; k++)
                    {
                        if(k >= i)
                        {
                            miniArr[j][k] = array[j + 1][k + 1];
                        }
                        else
                        {
                            miniArr[j][k] = array[j + 1][k];
                        }
                    }
                }
                ret += (((i + 1) % 2 * 2) - 1)*(array[0][i])*(findDeterminant(miniArr));
            }
        }
        return ret;
    }
    
    public static void printArray(double[][] array)
    {
        for (double[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                System.out.print(array1[j] + "\t");
            }
            System.out.println();
        }
    }
}
