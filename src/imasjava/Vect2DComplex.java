package imasjava;


public class Vect2DComplex extends VectComplex
{
    int dim1;
    int dim2;
    
    public Vect2DComplex(int dim1, int dim2)
    {
        this.dim1 = dim1;
        this.dim2 = dim2;
        array = new Complex[dim1*dim2];
    }
    public Vect2DComplex(int dim1, int dim2, Complex array[])
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
    public int[] getDims() {int[] d={dim1,dim2}; return d;}
    
    public Complex getElementAt(int i, int j) 
    {
	return array[i+j*dim1];
    }
    public Vect1DComplex getElementAt(int i) 
    {
    	Complex retArr[] = new Complex[dim1];
        System.arraycopy(array, i*dim1, retArr, 0, dim1);
        return new Vect1DComplex(retArr);
    }
    public void setElementAt(int i, Vect1DComplex element)
    {
        System.arraycopy(element.array, 0, array, i * dim1, dim1);
    }
    public void setElementAt(int i, int j, Complex element)
    {
    	array[i+j*dim1] = element;
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
			    if(j < dim2 - 1)
				retStr += ""+array[i + j*dim1].toString()+",";
			    else
				retStr += ""+array[i + j*dim1].toString();
			}
		    retStr += "]";
		}
	    retStr += "]";
	    return retStr;
	}
    }
}



