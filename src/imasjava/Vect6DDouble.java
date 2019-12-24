package imasjava;

public class Vect6DDouble extends SummaryString
{
    int dim1;
    int dim2;
    int dim3;
    int dim4;
    int dim5;
    int dim6;
    double array[];
    
    public Vect6DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        this.dim5 = dim5;
        this.dim6 = dim6;
        array = new double[dim1*dim2*dim3*dim4*dim5*dim6];
    }
    public Vect6DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, double array[])
    {
        this(dim1, dim2, dim3, dim4, dim5, dim6);
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
           default: return dim6;
        }
    }
   public Vect5DDouble getElementAt(int i) 
    {
        double retArr[] = new double[dim1*dim2*dim3*dim4*dim5];
        System.arraycopy(array, i*dim1*dim2*dim3*dim4*dim5, retArr, 0, dim1*dim2*dim3*dim4*dim5);
        return new Vect5DDouble(dim1, dim2, dim3, dim4, dim5, retArr);
    }
    public void setElementAt(int i, Vect5DDouble element)
    {
        System.arraycopy(element.array, 0, array, i * dim1*dim2*dim3*dim4*dim5, dim1*dim2*dim3*dim4*dim5);
    }
     
    public double getElementAt(int i, int j, int k, int h, int l, int m) 
    {
        return array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5];
    }
    
    public void setElementAt(int i, int j, int k, int h,int l, int m, double element) 
    {
        array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5] = element;
    }
    public double[] getArray() 
    {
        return array;
    }

    public void setArray(double[] array, int dim1, int dim2, int dim3, int dim4, int dim5, int dim6) 
    {
         //this.array = new double[dim1 * dim2 * dim3 * dim4 * dim5 * dim6];
        this.array = array;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        this.dim5 = dim5;
        this.dim6 = dim6;
    }

    public String toSummaryString(int length)
    {
      if(length < 0) {
        return toString();
      }

      StringBuilder sb = new StringBuilder();

      try {

        addString(sb, "[", length);
        for(int i = 0; i < dim1; i++)
        {
            addString(sb, "[", length);
            for(int j = 0; j < dim2; j++)
            {
                addString(sb, "[", length);
                for(int k = 0; k < dim3; k++)
                {
                    addString(sb, "[", length);
                    for(int h = 0; h < dim4; h++)
                    {
                        addString(sb, "[", length);
                        for(int l = 0; l < dim5; l++)
                        {
                            addString(sb, "[", length);
                            for(int m = 0; m < dim6; m++)
                            {
                                if(m < dim6 - 1)
                                    addString(sb, ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5]+",", length);
                                else
                                    addString(sb, ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5], length);
                            }
                        }
                    }
                    addString(sb, "]", length);
               }
               addString(sb, "]", length);
            }
            addString(sb, "]", length);
       }
       addString(sb, "]", length);
      } catch(StringLimitException ex) {
      }
      return sb.toString();
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
                                if(m < dim6 - 1)
                                    retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5]+",";
                                else
                                    retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5];
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
