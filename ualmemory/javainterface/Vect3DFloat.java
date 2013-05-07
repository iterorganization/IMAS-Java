package ualmemory.javainterface;
public class Vect3DFloat
{
    int dim1;
    int dim2;
    int dim3;
    float array[];
    
    public Vect3DFloat(int dim1, int dim2, int dim3)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        array = new float[dim1*dim2*dim3];
    }
    public Vect3DFloat(int dim1, int dim2, int dim3, float array[])
    {
        this(dim1, dim2, dim3);
        this.array = array;
    }
    
    
    public int getDim(int idx) 
    {
        switch(idx) {
            case 0: return dim1;
            case 1: return dim2;
            default: return dim3;
        }
    }
    public Vect2DFloat getElementAt(int i) 
    {
        float retArr[] = new float[dim1*dim2];
        System.arraycopy(array, i*dim1*dim2, retArr, 0, dim1*dim2);
        return new Vect2DFloat(dim1, dim2, retArr);
    }
    public void setElementAt(int i, Vect2DFloat element)
    {
        System.arraycopy(element.array, 0, array, i * dim1 * dim2, dim1*dim2);
    }
    
    public float getElementAt(int i, int j, int k) {return array[i+j*dim1+k*dim1*dim2];}
    public void setElementAt(int i, int j, int k, float element) {array[i+j*dim1+k*dim1*dim2] = element;}
    public float[] getArray() 
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
                retStr += "[";
                for(int k = 0; k < dim3; k++)
                {
                    if(k < dim3 - 1)
                        retStr += ""+array[i+j*dim1+k*dim1*dim2]+",";
                    else
                        retStr += ""+array[i+j*dim1+k*dim1*dim2];
                }
                retStr += "]";
            }
            retStr += "]";
       }
       retStr += "]";
       return retStr;
    }
}


