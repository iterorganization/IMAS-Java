package imasjava;


public class Vect5DComplex extends VectComplex
{
    int dim1;
    int dim2;
    int dim3;
    int dim4;
    int dim5;
    
    public Vect5DComplex(int dim1, int dim2, int dim3, int dim4, int dim5)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        this.dim5 = dim5;
        array = new Complex[dim1*dim2*dim3*dim4*dim5];
    }
    public Vect5DComplex(int dim1, int dim2, int dim3, int dim4, int dim5, Complex array[])
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
    public int[] getDims() {int[] d={dim1,dim2,dim3,dim4,dim5}; return d;}
    public Vect4DComplex getElementAt(int i) 
    {
	Complex retArr[] = new Complex[dim1*dim2*dim3*dim4];
        System.arraycopy(array, i*dim1*dim2*dim3*dim4, retArr, 0, dim1*dim2*dim3*dim4);
        return new Vect4DComplex(dim1, dim2, dim3, dim4, retArr);
    }
    public void setElementAt(int i, Vect4DComplex element)
    {
        System.arraycopy(element.array, 0, array, i * dim1*dim2*dim3*dim4, dim1*dim2*dim3*dim4);
    }
    public Complex getElementAt(int i, int j, int k, int h, int l)
    {
    	return array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4];
    }
    public void setElementAt(int i, int j, int k, int h,int l, Complex element)
    {
    	array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim2*dim2*dim3*dim4] = element;
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
                        sb.append( "[" );
                        for(int l = 0; l < dim5; l++)
                        {
                            if(l < dim5 - 1)
                                sb.append( ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4]+"," );
                            else
                                sb.append( ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4] );
                        }
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
        for(j = 0; j < dim2 && i < elements; j++)
        {
          retStr += "[";
          int k = 0;
          for(k = 0; k < dim3 && i < elements; k++)
          {
            retStr += "[";
            int h = 0;
            for(h = 0; h < dim4 && i < elements; h++)
            {
              retStr += "[";
              int l = 0;
              for(l = 0; l < dim5 && l < elements; l++)
              {
                if(l < dim5 - 1)
                  retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4]+",";
                else
                  retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4];
              }
              if( l < dim5 ) {
                retStr += "...";
              }
            }
            if( h < dim4 ) {
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
      if (getSize() > maxPrintSize) {
        return toSummary();
      }
      else {
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
}


