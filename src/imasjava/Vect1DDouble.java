package imasjava;
public class Vect1DDouble
{
    double array[];
    
    public Vect1DDouble(int dim)
    {
        array = new double[dim];
    }
    public Vect1DDouble(double array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public double getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, double element) {array[idx] = element;}
    public double[] getArray() {return array;}
    public String toString()
    {
        String retStr = "[";
        for(int i = 0; i < array.length; i++)
        {
            if(i < array.length - 1)
                retStr += ""+array[i]+",";
            else
                retStr += ""+array[i];
        }
        retStr += "]";
        return retStr;
    }
}


