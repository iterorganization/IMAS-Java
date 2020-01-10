package imasjava;
public class Vect1DBoolean extends SummaryString
{
    boolean array[];
    
    public Vect1DBoolean(int dim)
    {
        array = new boolean[dim];
    }
    public Vect1DBoolean(boolean array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public boolean getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, boolean element) {array[idx] = element;}
    public boolean[] getArray() {return array;}


    // negative number means that we don't limit the size
    // of the string
    public String toSummaryString(int length) {
        
        if(length < 0) {
          return toString();
        } 

        LimittedSizeStringBuilder sb = new LimittedSizeStringBuilder(length);

        try {
          sb.append("[");
          for(int i = 0; i < array.length; i++)
          {
            if(i < array.length - 1)
              sb.append(""+array[i]+",");
            else
              sb.append( String.valueOf(array[i]) );
          }
          sb.append("]");
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
        for(i = 0; i < array.length && i < elements; i++)
        {
          if(i < array.length - 1)
            retStr += ""+array[i]+",";
          else
            retStr += ""+array[i];
        }
        if( i < array.length) {
          retStr += "...";
        }
        retStr += "]";
        return retStr;
    }



    public String toString()
    {
        String retStr = "[";
        for(int i = 0; i < array.length; i++)
        {
            if(i < array.length - 1)
                retStr += ""+array[i]+",";
            else
                retStr += ""+array[i];
        }
        retStr += "]";
        return retStr;
    }
}


