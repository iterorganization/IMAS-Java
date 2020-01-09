package imasjava;

public class Vect2DDouble extends SummaryString
{
    int dim1;
    int dim2;
    double array[];
    
    public Vect2DDouble(int dim1, int dim2)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        array = new double[dim1*dim2];
    }
    public Vect2DDouble(int dim1, int dim2, double array[])
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
    
    public double getElementAt(int i, int j) {return array[i+j*dim1];}
    public Vect1DDouble getElementAt(int i) 
    {
        double retArr[] = new double[dim1];
        System.arraycopy(array, i*dim1, retArr, 0, dim1);
        return new Vect1DDouble(retArr);
    }
    public void setElementAt(int i, Vect1DDouble element)
    {
        System.arraycopy(element.array, 0, array, i * dim1, dim1);
    }
    public void setElementAt(int i, int j, double element) {array[i+j*dim1] = element;}
    public double[] getArray() 
    {
        return array;
    }

    public void setArray(double[] array, int dim1, int dim2) 
    {
        //this.array = new double[dim1 * dim2];
        this.array = array;
        this.dim1 = dim1;
        this.dim2 = dim2;
    }

    public String toSummaryString(int length) {
        if(length < 0) {
          return toString();
        } 

        StringBuilder sb = new StringBuilder();

        try {
          addString(sb, "[", length);
          for(int i = 0; i < dim1 && i < 5; i++)
          {
              addString(sb, "[", length);
              for(int j = 0; j < dim2 && i < 5; j++)
              {
                  if(j < dim2 - 1)
                      addString(sb, ""+array[i+j*dim1]+",", length);
                  else
                      addString(sb, ""+array[i+j*dim1], length);
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
          if(j < dim2 - 1)
            retStr += ""+array[i + j*dim1]+",";
          else
            retStr += ""+array[i+j*dim1];
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
          if(j < dim2 - 1)
            retStr += ""+array[i + j*dim1]+",";
          else
            retStr += ""+array[i+j*dim1];
        }
        retStr += "]";
      }
      retStr += "]";
      return retStr;
    }
}



