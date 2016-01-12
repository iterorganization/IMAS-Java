package imasjava;
public class Vect1DBoolean
{
    boolean array[];
    
    public Vect1DBoolean(int dim)
    {
        array = new boolean[dim];
    }
    public Vect1DBoolean(boolean array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public boolean getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, boolean element) {array[idx] = element;}
    public boolean[] getArray() {return array;}
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


