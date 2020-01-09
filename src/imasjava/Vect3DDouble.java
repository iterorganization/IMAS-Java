package imasjava;

public class Vect3DDouble extends SummaryString
{
    int dim1;
    int dim2;
    int dim3;
    double array[];
    
    public Vect3DDouble(int dim1, int dim2, int dim3)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        array = new double[dim1*dim2*dim3];
    }
    public Vect3DDouble(int dim1, int dim2, int dim3, double array[])
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
   public Vect2DDouble getElementAt(int i) 
    {
        double retArr[] = new double[dim1*dim2];
        System.arraycopy(array, i*dim1*dim2, retArr, 0, dim1*dim2);
        return new Vect2DDouble(dim1, dim2, retArr);
    }
    public void setElementAt(int i, Vect2DDouble element)
    {
        System.arraycopy(element.array, 0, array, i * dim1 * dim2, dim1*dim2);
    }
     
    public double getElementAt(int i, int j, int k) {return array[i+j*dim1+k*dim1*dim2];}
    public void setElementAt(int i, int j, int k, double element) {array[i+j*dim1+k*dim1*dim2] = element;}
    public double[] getArray() 
    {
        return array;
    }

    public void setArray(double[] array, int dim1, int dim2, int dim3) 
    {
        //this.array = new double[dim1 * dim2 * dim3];
        this.array = array;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
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
                    if(k < dim3 - 1)
                        addString(sb, ""+array[i+j*dim1+k*dim1*dim2]+",", length);
                    else
                        addString(sb, ""+array[i+j*dim1+k*dim1*dim2], length);
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
    
    public String toSummaryStringElements(int elements)
    {

      String retStr = "[";
      int i = 0;
      for(i = 0; i < dim1 && i < elements; i++)
      {
        retStr += "[";
        int j = 0;
        for(j = 0; j < dim2 && j < elements; j++)
        {
          retStr += "[";
          int k = 0;
          for(k = 0; k < dim3 && k < elements; k++)
          {
            if(k < dim3 - 1)
              retStr += ""+array[i+j*dim1+k*dim1*dim2]+",";
            else
              retStr += ""+array[i+j*dim1+k*dim1*dim2];
          }
          if( k < dim3 ) {
            retStr += "...";
          }
          retStr += "]";
        }
        if( j < dim2 ) {
          retStr += "...";
        }
        retStr += "]";
      }
      if( i < dim1 ) {
        retStr += "...";
      }
      retStr += "]";
      return retStr;
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



