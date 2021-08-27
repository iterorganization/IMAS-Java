package imasjava;

public class Vect2DInt extends SummaryString
{
    int dim1;
    int dim2;
    int array[];
    
    public Vect2DInt(int dim1, int dim2)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        array = new int[dim1*dim2];
    }
    public Vect2DInt(int dim1, int dim2, int array[])
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
    public Vect1DInt getElementAt(int i) 
    {
        int retArr[] = new int[dim1];
        System.arraycopy(array, i*dim1, retArr, 0, dim1);
        return new Vect1DInt(retArr);
    }
    public void setElementAt(int i, Vect1DInt element)
    {
        System.arraycopy(element.array, 0, array, i * dim1, dim1);
    }
    
    public int getElementAt(int i, int j) {return array[i+j*dim1];}
    public void setElementAt(int i, int j, int element) {array[i+j*dim1] = element;}
    public int[] getArray() 
    {
        return array;
    }

    /**
     * Returns internal array as row-major ordered array - copy of an array is returned.
     * 
     * If you want to update values of oryginal column-major ordered array you have to use
     * one of the methods: set2DArray, setElementAt, or setArray (requires 1D data with column-major order).
     *
     * @see set2DArray, setElementAt, setArray
     */
    public int [][] get2DArray() {

      int [][] array = new int[dim1][dim2];
      for(int i=0; i<dim1; i++) {
        for(int p=0; p<dim2; p++) {
          array[i][p] = this.array[i + p * dim1];
        }
      }

      return array;

    }

    /**
     * Sets internal array in column-major ordered way.
     * 
     * @param array row-major ordered array
     *
     */
    public void set2DArray(int [][] array) {

      for(int i=0; i<dim1; i++) {
        for(int p=0; p<dim2; p++) {
          this.array[i + p * dim1] = array[i][p];
        }
      }

    }

    public void setArray(int[] array, int dim1, int dim2) 
    {
        //this.array = new int[dim1 * dim2 ];
        this.array = array;
        this.dim1 = dim1;
        this.dim2 = dim2;
    }

    public String toSummaryString(int length) {
        if(length < 0) {
          return toString();
        } 

        LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(length);

        try {
          sb.append( "[" );
          for(int i = 0; i < dim1 && i < 5; i++)
          {
              sb.append( "[" );
              for(int j = 0; j < dim2 && i < 5; j++)
              {
                  if(j < dim2 - 1)
                      sb.append( ""+array[i+j*dim1]+"," );
                  else
                      sb.append( ""+array[i+j*dim1] );
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
                    retStr += ""+array[i+j*dim1]+",";
                else
                    retStr += ""+array[i+j*dim1];
            }
            retStr += "]";
       }
       retStr += "]";
       return retStr;
    }
}


