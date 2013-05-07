package ualmemory.javainterface;
public class Vect1DFloat
{
    float array[];
    
    public Vect1DFloat(int dim)
    {
        array = new float[dim];
    }
    public Vect1DFloat(float array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public float getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, float element) {array[idx] = element;}
    public float[] getArray() {return array;}
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


