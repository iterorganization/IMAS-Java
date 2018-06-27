package imasjava;

public class Vect5DDouble
{
    int dim1;
    int dim2;
    int dim3;
    int dim4;
    int dim5;
    double array[];
    
    public Vect5DDouble(int dim1, int dim2, int dim3, int dim4, int dim5)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        this.dim5 = dim5;
        array = new double[dim1*dim2*dim3*dim4*dim5];
    }
    public Vect5DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, double array[])
    {
        this(dim1, dim2, dim3, dim4, dim5);
        this.array = array;
    }
    
    
    public int getDim(int idx) 
    {
        switch(idx) {
            case 0: return dim1;
            case 1: return dim2;
            case 2: return dim3;
            case 3: return dim4;
            default: return dim5;
        }
    }
   public Vect4DDouble getElementAt(int i) 
    {
        double retArr[] = new double[dim1*dim2*dim3*dim4];
        System.arraycopy(array, i*dim1*dim2*dim3*dim4, retArr, 0, dim1*dim2*dim3*dim4);
        return new Vect4DDouble(dim1, dim2, dim3, dim4, retArr);
    }
    public void setElementAt(int i, Vect4DDouble element)
    {
        System.arraycopy(element.array, 0, array, i * dim1*dim2*dim3*dim4, dim1*dim2*dim3*dim4);
    }
     
    public double getElementAt(int i, int j, int k, int h, int l) {return array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4];}
    public void setElementAt(int i, int j, int k, int h,int l, double element) {array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim2*dim2*dim3*dim4] = element;}
    public double[] getArray() 
    {
        return array;
    }

    public void setArray(double[] array, int dim1, int dim2, int dim3, int dim4, int dim5) 
    {
        //this.array = new double[dim1 * dim2 * dim3 * dim4 * dim5];
        this.array = array;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        this.dim5 = dim5;
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
                    retStr += "[";
                    for(int h = 0; h < dim4; h++)
                    {
                        retStr += "[";
                        for(int l = 0; l < dim5; l++)
                        {
                            if(l < dim5 - 1)
                                retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4]+",";
                            else
                                retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4];
                        }
                    }
                    retStr += "]";
               }
               retStr += "]";
            }
            retStr += "]";
       }
       retStr += "]";
       return retStr;
    }
}


