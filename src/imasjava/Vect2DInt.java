package imasjava;
public class Vect2DInt
{
    int dim1;
    int dim2;
    int array[];
    
    public Vect2DInt(int dim1, int dim2)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        array = new int[dim1*dim2];
    }
    public Vect2DInt(int dim1, int dim2, int array[])
    {
        this(dim1, dim2);
        this.array = array;
    }
    
    
    public int getDim(int idx) 
    {
        if(idx == 0)
            return dim1;
        else
            return dim2;
    }
    public Vect1DInt getElementAt(int i) 
    {
        int retArr[] = new int[dim1];
        System.arraycopy(array, i*dim1, retArr, 0, dim1);
        return new Vect1DInt(retArr);
    }
    public void setElementAt(int i, Vect1DInt element)
    {
        System.arraycopy(element.array, 0, array, i * dim1, dim1);
    }
    
    public int getElementAt(int i, int j) {return array[i+j*dim1];}
    public void setElementAt(int i, int j, int element) {array[i+j*dim1] = element;}
    public int[] getArray() 
    {
        return array;
    }
    public String toString()
    {
        String retStr = "[";
        for(int i = 0; i < dim1; i++)
        {
            retStr += "[";
            for(int j = 0; j < dim2; j++)
            {
                if(j < dim2 - 1)
                    retStr += ""+array[i+j*dim1]+",";
                else
                    retStr += ""+array[i+j*dim1];
            }
            retStr += "]";
       }
       retStr += "]";
       return retStr;
    }
}


