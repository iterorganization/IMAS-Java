package imasjava;

import java.io.Serializable;



/**
 * Representation of a Complex number, i.e. a number which has both a
 * real and imaginary part.
 *  Based on The Apache Commons Mathematics Library http://commons.apache.org/math/
 */
public class Complex implements Serializable  
{
    /** The square root of -1. A number representing "0.0 + 1.0i" */
    public static final Complex I = new Complex(0.0, 1.0);
    /** A complex number representing "NaN + NaNi" */
    public static final Complex NaN = new Complex(Double.NaN, Double.NaN);
    /** A complex number representing "+INF + INFi" */
    public static final Complex INF = new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    /** A complex number representing "1.0 + 0.0i" */
    public static final Complex ONE = new Complex(1.0, 0.0);
    /** A complex number representing "0.0 + 0.0i" */
    public static final Complex ZERO = new Complex(0.0, 0.0);

    /** Serializable version identifier */
    private static final long serialVersionUID = -1L;

    /** The imaginary part. */
    private final double imaginary;
    /** The real part. */
    private final double real;
    /** Record whether this complex number is equal to NaN. */
    private final transient boolean isNaN;
    /** Record whether this complex number is infinite. */
    private final transient boolean isInfinite;

    /**
     * Create a complex number given only the real part.
     *
     * @param real Real part.
     */
    public Complex(double real) {
        this(real, 0.0);
    }

    /**
     * Create a complex number given the real and imaginary parts.
     *
     * @param real Real part.
     * @param imaginary Imaginary part.
     */
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;

        isNaN = Double.isNaN(real) || Double.isNaN(imaginary);
        isInfinite = !isNaN &&
            (Double.isInfinite(real) || Double.isInfinite(imaginary));
    }

    /**
     * Create a complex number from a String. Its syntax must be (without spaces) : 
     * 			<decimal number>+<decimal number>i 
     *		or	(<decimal number>,<decimal number>i) 
     *
     * Raise java.util.IllegalFormatException or java.util.NumberFormatException
     * if the String parameter is malformed
     * @param strVal string value.
     */
    public Complex (String strVal)  throws java.lang.Exception {
        
        Complex cplxNumber= Complex.NaN;
        double re=0.0;
        double im=0.0;
        String r1, r2;
        int iPos; 
	String realStr;
	String imaginaryStr;
	
	try {
	    
	    /* These regexp allow to use scientific notation for real part and imaginary part */
	    /* Regex for notation : (<decimal number>,<decimal number>i) 
               with an optional space before and after the comma */
	    r1= "^\\([-+]?[0-9]?+(\\.([0-9]+)?)?([e,E][-+]?[0-9])?\\s*,\\s*[-+]?[0-9]?+(\\.([0-9]+)?)?([e,E][-+]?[0-9])?[i]\\)$";
            /* Regex for notation : <decimal number>+<decimal number>i 
               with an optional space before and after the + */
            r2= "^[-+]?[0-9]?+(\\.([0-9]+)?)?([e,E][-+]?[0-9])?\\s*\\+\\s*[-+]?[0-9]?+(\\.([0-9]+)?)?([e,E][-+]?[0-9])?[i]$";
	    
	    if (strVal.matches(r1)) {
	    	iPos=strVal.indexOf(",");				// position of the comma which separates the 2 parts
                realStr=strVal.substring(1, iPos);				// extract the real part without the open parenthesis
                imaginaryStr=strVal.substring(iPos+1,strVal.length()-2);	// extract the real part without the closing parenthesis and the 'i'
                if (realStr.length()==0) realStr="0.0";
                if (imaginaryStr.length()==0) imaginaryStr="1.0";
                //System.out.println("Complex number : real="+real+" imaginary="+imaginary+"i");
		
                re = java.lang.Double.parseDouble(realStr);
                im = java.lang.Double.parseDouble(imaginaryStr);
	    } else if (strVal.matches(r2)) {
	    	iPos=strVal.indexOf("+");				// position of the '+' which separates the 2 parts
                realStr=strVal.substring(0, iPos);				// extract the real part 
                imaginaryStr=strVal.substring(iPos+1,strVal.length()-1);	// extract the real part without the 'i'
                if (realStr.length()==0) realStr="0.0";
                if (imaginaryStr.length()==0) imaginaryStr="1.0";
                //System.out.println("Complex number : real="+real+" imaginary="+imaginary+"i");
		
                re = java.lang.Double.parseDouble(realStr);
                im = java.lang.Double.parseDouble(imaginaryStr);
	    } else {
                System.out.println("Syntax error : invalid complex number "+strVal);
		re=0.0;
	    	im=0.0;
		throw new java.lang.Exception();
            }
	} catch (java.util.IllegalFormatException e) {
	    System.out.println("java.util.IllegalFormatException : invalid complex number "+strVal);
	    re=0.0;
	    im=0.0;
	    throw new java.lang.Exception();
	} catch (java.lang.NumberFormatException e) {
	    System.out.println("java.lang.NumberFormatException : invalid complex number "+strVal);
	    re=0.0;
	    im=0.0;
	    throw new java.lang.Exception();
	}
        this.real = re;
        this.imaginary = im;
	//System.out.println(" (" + real + ", " + imaginary + "i)");

        isNaN = Double.isNaN(real) || Double.isNaN(imaginary);
        isInfinite = !isNaN &&
            (Double.isInfinite(real) || Double.isInfinite(imaginary));
    }
    


    /**
     * Test for the equality of two Complex objects.
     * If both the real and imaginary parts of two complex numbers
     * are exactly the same, and neither is {@code Double.NaN}, the two
     * Complex objects are considered to be equal.
     * All {@code NaN} values are considered to be equal - i.e, if either
     * (or both) real and imaginary parts of the complex number are equal
     * to {@code Double.NaN}, the complex number is equal to
     * {@code NaN}.
     *
     * @param other Object to test for equality to this
     * @return true if two Complex objects are equal, false if object is
     * {@code null}, not an instance of Complex, or not equal to this Complex
     * instance.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Complex){
            Complex c = (Complex)other;
            if (c.isNaN) {
                return isNaN;
            } else {
                return (real == c.real) && (imaginary == c.imaginary);
            }
        }
        return false;
    }

    
    /**
     * Access the imaginary part.
     *
     * @return the imaginary part.
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Access the real part.
     *
     * @return the real part.
     */
    public double getReal() {
        return real;
    }

    /**
     * Checks whether either or both parts of this complex number is
     * {@code NaN}.
     *
     * @return true if either or both parts of this complex number is
     * {@code NaN}; false otherwise.
     */
    public boolean isNaN() {
        return isNaN;
    }

    /**
     * Checks whether either the real or imaginary part of this complex number
     * takes an infinite value (either {@code Double.POSITIVE_INFINITY} or
     * {@code Double.NEGATIVE_INFINITY}) and neither part
     * is {@code NaN}.
     *
     * @return true if one or both parts of this complex number are infinite
     * and neither part is {@code NaN}.
     */
    public boolean isInfinite() {
        return isInfinite;
    }

  

    /**
     * Create a complex number given the real and imaginary parts.
     *
     * @param realPart Real part.
     * @param imaginaryPart Imaginary part.
     * @return a Complex instance.
     */
    public static Complex valueOf(double realPart,
                                  double imaginaryPart) {
        if (Double.isNaN(realPart) ||
            Double.isNaN(imaginaryPart)) {
            return NaN;
        }
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Create a complex number given only the real part.
     *
     * @param realPart Real part.
     * @return a Complex instance.
     */
    public static Complex valueOf(double realPart) {
        if (Double.isNaN(realPart)) {
            return NaN;
        }
        return new Complex(realPart);
    }

     @Override
    public String toString() {
        return "(" + real + ", " + imaginary + "i)";
    }
 
    /**
     * Get a hashCode for the complex number.
     * Any {@code Double.NaN} value in real or imaginary part produces\
     * the same hash code {@code 7}.
     *
     * @return a hash code value for this object.
     */
     @Override
     public int hashCode() {
       	if (isNaN) {
	   return 7;
	}
	
	return 37 * (17 * Double.valueOf(imaginary).hashCode() +
		 	Double.valueOf(real).hashCode());
  	}
}

