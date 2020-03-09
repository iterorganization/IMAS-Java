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

      LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(length);

      try {
        sb.append( "[" );
        for(int i = 0; i < dim1; i++)
        {
            sb.append( "[" );
            for(int j = 0; j < dim2; j++)
            {
                sb.append( "[" );
                for(int k = 0; k < dim3; k++)
                {
                    sb.append( "[" );
                    for(int h = 0; h < dim4; h++)
                    {
                        if(h < dim4 - 1)
                            sb.append( ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3]+"," );
                        else
                            sb.append( ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3] );
                    }
                    sb.append( "]" );
               }
               sb.append( "]" );
            }
            sb.append( "]" );
       }
       sb.append( "]" );
      } catch(StringLimitException ex) {
        // This might happen, eventually
        // but it's not an error       
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
            retStr += "[";
            int h = 0;
            for(h = 0; h < dim4 && h < elements; h++)
            {
              if(h < dim4 - 1)
                retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3]+",";
              else
                retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3];
            }
            if(h < dim4) {
              retStr += "...";
            }
            retStr += "]";
          }
          if(k < dim3) {
            retStr += "...";
          }
          retStr += "]";
        }
        if(j < dim2) {
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




