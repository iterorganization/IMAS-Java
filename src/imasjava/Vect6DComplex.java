package imasjava;

public class Vect6DComplex extends VectComplex
{
    int dim1;
    int dim2;
    int dim3;
    int dim4;
    int dim5;
    int dim6;

    
    public Vect6DComplex(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        this.dim5 = dim5;
        this.dim6 = dim6;
        array = new Complex[dim1*dim2*dim3*dim4*dim5*dim6];
    }
    public Vect6DComplex(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, Complex array[])
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
    public int[] getDims() {int[] d={dim1,dim2,dim3,dim4,dim5,dim6}; return d;}
    public Vect5DComplex getElementAt(int i) 
    {
	   Complex retArr[] = new Complex[dim1*dim2*dim3*dim4*dim5];
        System.arraycopy(array, i*dim1*dim2*dim3*dim4*dim5, retArr, 0, dim1*dim2*dim3*dim4*dim5);
        return new Vect5DComplex(dim1, dim2, dim3, dim4, dim5, retArr);
    }
    public void setElementAt(int i, Vect5DComplex element)
    {
        System.arraycopy(element.array, 0, array, i * dim1*dim2*dim3*dim4*dim5, dim1*dim2*dim3*dim4*dim5);
    }
    public Complex getElementAt(int i, int j, int k, int h, int l, int m) 
    {
        return array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5];
    }
    public void setElementAt(int i, int j, int k, int h,int l, int m, Complex element) 
    {
        array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+l*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5] = element;
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
                            sb.append( "[" );
                            for(int m = 0; m < dim6; m++)
                            {
                                if(m < dim6 - 1)
                                    sb.append( ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5]+"," );
                                else
                                    sb.append( ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5] );
                            }
                            sb.append( "]" );
                        }
                        sb.append( "]" );
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
              retStr += "[";
              int l = 0;
              for(l = 0; l < dim5 && l < elements; l++)
              {
                retStr += "[";
                int m = 0;
                for(m = 0; m < dim6 && m < elements; m++)
                {
                  if(m < dim6 - 1)
                    retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5]+",";
                  else
                    retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5];
                }
                if(m < dim6) {
                  retStr += "...";
                }
              }
              if(l < dim5) {
                retStr += "...";
              }
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
      if(i < dim1) {
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
                  retStr += "[";
                  for(int m = 0; m < dim6; m++)
                  {
                    if(m < dim6 - 1)
                      retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5]+",";
                    else
                      retStr += ""+array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3+k*dim1*dim2*dim3*dim4+m*dim1*dim2*dim3*dim4*dim5];
                  }
                  retStr += "]";
                }
                retStr += "]";
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
