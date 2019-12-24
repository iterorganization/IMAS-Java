package imasjava;

public class Vect4DDouble extends SummaryString
{
    int dim1;
    int dim2;
    int dim3;
    int dim4;
    double array[];
    
    public Vect4DDouble(int dim1, int dim2, int dim3, int dim4)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        array = new double[dim1*dim2*dim3*dim4];
    }
    public Vect4DDouble(int dim1, int dim2, int dim3, int dim4, double array[])
    {
        this(dim1, dim2, dim3, dim4);
        this.array = array;
    }
    
    
    public int getDim(int idx) 
    {
        switch(idx) {
            case 0: return dim1;
            case 1: return dim2;
            case 2: return dim3;
            default: return dim4;
        }
    }
   public Vect3DDouble getElementAt(int i) 
    {
        double retArr[] = new double[dim1*dim2*dim3];
        System.arraycopy(array, i*dim1*dim2*dim3, retArr, 0, dim1*dim2*dim3);
        return new Vect3DDouble(dim1, dim2, dim3, retArr);
    }
    public void setElementAt(int i, Vect3DDouble element)
    {
        System.arraycopy(element.array, 0, array, i * dim1*dim2*dim3, dim1*dim2*dim3);
    }
     
    public double getElementAt(int i, int j, int k, int h) {return array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3];}
    public void setElementAt(int i, int j, int k, int h, double element) {array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3] = element;}
    public double[] getArray() 
    {
        return array;
    }

    public void setArray(double[] array, int dim1, int dim2, int dim3, int dim4) 
    {
        //this.array = new double[dim1 * dim2 * dim3 * dim4];
        this.array = array;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
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
                        if(h < dim4 - 1)
                            addString(sb, ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3]+",", length);
                        else
                            addString(sb, ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3], length);
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
                        if(h < dim4 - 1)
                            retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3]+",";
                        else
                            retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3];
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




