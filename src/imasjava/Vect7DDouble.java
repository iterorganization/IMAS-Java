package imasjava;

public class Vect7DDouble
{
    int dim1;
    int dim2;
    int dim3;
    int dim4;
    int dim5;
    int dim6;
    int dim7;
    double array[];
    
    public Vect7DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6,int dim7)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        this.dim5 = dim5;
        this.dim6 = dim6;
        this.dim7 = dim7;
        array = new double[dim1*dim2*dim3*dim4*dim5*dim6*dim7];
    }
    public Vect7DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, int dim7, double array[])
    {
        this(dim1, dim2, dim3, dim4, dim5, dim6, dim7);
        this.array = array;
    }
    
    
    public int getDim(int idx) 
    {
        switch(idx) {
            case 0: return dim1;
            case 1: return dim2;
            case 2: return dim3;
            case 3: return dim4;
            case 4: return dim5;
            case 5: return dim6;
            case 6: return dim7;
            default: return dim7;
        }
    }
   public Vect6DDouble getElementAt(int i) 
    {
        double retArr[] = new double[dim1*dim2*dim3*dim4*dim5*dim6];
        System.arraycopy(array, i*dim1*dim2*dim3*dim4*dim5*dim6, retArr, 0, dim1*dim2*dim3*dim4*dim5*dim6);
        return new Vect6DDouble(dim1, dim2, dim3, dim4, dim5, dim6, retArr);
    }
    public void setElementAt(int i, Vect6DDouble element)
    {
        System.arraycopy(element.array, 0, array, i * dim1*dim2*dim3*dim4*dim5*dim7, dim1*dim2*dim3*dim4*dim5*dim6);
    }
     
    public double getElementAt(int i, int j, int k, int h, int l, int m, int n) 
    {
        return array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4
		+m*dim1*dim2*dim3*dim4*dim5+n*dim1*dim2*dim3*dim4*dim5*dim6];
    }
    
    public void setElementAt(int i, int j, int k, int h,int l, int m, int n, double element) 
    {
        array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4
		+m*dim1*dim2*dim3*dim4*dim5+n*dim1*dim2*dim3*dim4*dim5*dim6] = element;
    }
    public double[] getArray() 
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
                    retStr += "[";
                    for(int h = 0; h < dim4; h++)
                    {
                        retStr += "[";
                        for(int l = 0; l < dim5; l++)
                        {
                            retStr += "[";
                            for(int m = 0; m < dim6; m++)
                            {
                              	for(int n = 0; n < dim7; m++)
                              	{
                                     if(n < dim7 - 1)
                                    	retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4
				    	  +m*dim1*dim2*dim3*dim4*dim5+n*dim1*dim2*dim3*dim4*dim5*dim6]+",";
                                     else
                                    	retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4
				    	  +m*dim1*dim2*dim3*dim4*dim5+n*dim1*dim2*dim3*dim4*dim5*dim6];
				}
                            }
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
