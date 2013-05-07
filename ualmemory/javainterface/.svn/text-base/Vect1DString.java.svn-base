package ualmemory.javainterface;
public class Vect1DString
{
    String array[];
    
    public Vect1DString(int dim)
    {
        array = new String[dim];
    }
    public Vect1DString(String array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public String getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, String element) {array[idx] = element;}
    public String[] getArray() {return array;}
    public String toString()
    {
        String retStr = "[";
        for(int i = 0; i < array.length; i++)
        {
            if(i < array.length - 1)
                retStr += "\""+array[i]+"\",";
            else
                retStr += "\""+array[i]+"\"";
        }
        retStr += "]";
        return retStr;
    }
}



