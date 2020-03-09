package imasjava;

public class Vect1DDouble extends SummaryString
{
    double array[] = null;
    
    public Vect1DDouble(int dim)
    {
        array = new double[dim];
    }

    public Vect1DDouble(double array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public double getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, double element) {array[idx] = element;}
    public double[] getArray() {return array;}

    public void setArray(double[] array, int dim) 
    {
        //this.array = new double[dim];
        this.array = array;
    }

    // negative number means that we don't limit the size
    // of the string
    public String toSummaryString(int length) {
        
        if(length < 0) {
          return toString();
        } 

        LimitedSizeStringBuilder sb = new LimitedSizeStringBuilder(length);

        try {
          sb.append("[");
          for(int i = 0; i < array.length; i++)
          {
            if(i < array.length - 1)
              sb.append(""+array[i]+",");
            else
              sb.append(String.valueOf(array[i]));
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


