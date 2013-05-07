package ualmemory.javainterface;
public class Vect1DInt
{
    int array[];
    
    public Vect1DInt(int dim)
    {
        array = new int[dim];
    }
    public Vect1DInt(int array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public int getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, int element) {array[idx] = element;}
    public int[] getArray() {return array;}
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

