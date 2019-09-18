package imasjava;


public class Vect1DComplex extends VectComplex
{
    public Vect1DComplex(int dim)
    {
        array = new Complex[dim];
    }
    public Vect1DComplex(Complex array[])
    {
        this.array = array;
    }
       
    public int getDim() {return array.length;}
    public int[] getDims() {int[] d={array.length}; return d;}
    public Complex getElementAt(int idx) {return array[idx];}
    public void setElementAt(int idx, Complex element) {array[idx] = element;}
    public String toString()
    {
	if (getSize() > maxPrintSize) {
	    return toSummary();
	}
	else {
	    Complex complexNumber = null;
	    String retStr = "[";
	    complexNumber = this.array[0];
	    retStr += complexNumber.toString();
	    for(int i = 1; i < array.length; i++)
		{
		    complexNumber = this.array[i];
		    retStr += "," + complexNumber.toString();
		}
	    retStr += "]";
	    return retStr;
	}
    }
}


