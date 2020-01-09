package imasjava;

public class Vect1DFloat extends SummaryString 
{
    float array[];
    
    public Vect1DFloat(int dim)
    {
        array = new float[dim];
    }
    public Vect1DFloat(float array[])
    {
        this.array = array;
    }
    
    
    public int getDim() {return array.length;}
    public float getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, float element) {array[idx] = element;}
    public float[] getArray() {return array;}

    public String toSummaryString(int length) {
        
        if(length < 0) {
          return toString();
        } 

        StringBuilder sb = new StringBuilder();

        try {
          addString(sb, "[", length);
          for(int i = 0; i < array.length; i++)
          {
            if(i < array.length - 1)
              addString(sb, ""+array[i]+",", length);
            else
              addString(sb, String.valueOf(array[i]), length);
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


