package imasjava;


public class Vect4DComplex extends VectComplex
{
    int dim1;
    int dim2;
    int dim3;
    int dim4;

    public Vect4DComplex(int dim1, int dim2, int dim3, int dim4)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.dim4 = dim4;
        array = new Complex[dim1*dim2*dim3*dim4];
    }
    public Vect4DComplex(int dim1, int dim2, int dim3, int dim4, Complex array[])
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
    public int[] getDims() {int[] d={dim1,dim2,dim3,dim4}; return d;}
    public Vect3DComplex getElementAt(int i) 
    {
	   Complex retArr[] = new Complex[dim1*dim2*dim3];
        System.arraycopy(array, i*dim1*dim2*dim3, retArr, 0, dim1*dim2*dim3);
        return new Vect3DComplex(dim1, dim2, dim3, retArr);
    }
    public void setElementAt(int i, Vect3DComplex element)
    {
        System.arraycopy(element.array, 0, array, i * dim1*dim2*dim3, dim1*dim2*dim3);
    }
    public Complex getElementAt(int i, int j, int k, int h) 
    {
    	return array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3];
    }
    public void setElementAt(int i, int j, int k, int h, Complex element) 
    {
    	array[i+j*dim1+k*dim1*dim2+h*dim1*dim2*dim3] = element;
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
}




