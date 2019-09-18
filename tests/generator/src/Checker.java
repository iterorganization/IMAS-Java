import java.util.Arrays;
import java.util.Random;
import imasjava.*;



public class Checker {


     /*******************************************************************************/
    /**********************        TIME           ***********************/
    /*******************************************************************************/

    static boolean checkTime(Vect1DDouble observedValue, String fieldPath, int timeIdx)
    {
        Vect1DDouble expectedValue = null;
        double[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.getTimeVector(timeIdx);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    /************************************************************************************************************************************************/
    /*********************************                                 INTEGER                                   ************************************/
    /************************************************************************************************************************************************/

    static boolean checkData(int observedValue, String fieldPath, boolean isReduced)
    {
        int expectedValue = -1;
        
        expectedValue = Generator.randomData(expectedValue, isReduced);


        if( observedValue != expectedValue) 
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + observedValue );
            System.err.println("\t\tExpected = " + expectedValue );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect1DInt observedValue, String fieldPath, boolean isReduced)
    {
        Vect1DInt expectedValue = null;
        int[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect2DInt observedValue, String fieldPath, boolean isReduced)
    {
        Vect2DInt expectedValue = null;
        int[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect3DInt observedValue, String fieldPath, boolean isReduced)
    {
        Vect3DInt expectedValue = null;
        int[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    /************************************************************************************************************************************************/
    /*********************************                                  DOUBLE                                   ************************************/
    /************************************************************************************************************************************************/

    static boolean checkData(double observedValue, String fieldPath, boolean isReduced)
    {
        double expectedValue = -1;
        
        expectedValue = Generator.randomData(expectedValue, isReduced);


        if( observedValue != expectedValue) 
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + observedValue );
            System.err.println("\t\tExpected = " + expectedValue );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect1DDouble observedValue, String fieldPath, boolean isReduced)
    {
        Vect1DDouble expectedValue = null;
        double[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect2DDouble observedValue, String fieldPath, boolean isReduced)
    {
        Vect2DDouble expectedValue = null;
        double[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect3DDouble observedValue, String fieldPath, boolean isReduced)
    {
        Vect3DDouble expectedValue = null;
        double[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }


    static boolean checkData(Vect4DDouble observedValue, String fieldPath, boolean isReduced)
    {
        Vect4DDouble expectedValue = null;
        double[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect5DDouble observedValue, String fieldPath, boolean isReduced)
    {
        Vect5DDouble expectedValue = null;
        double[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect6DDouble observedValue, String fieldPath, boolean isReduced)
    {
        Vect6DDouble expectedValue = null;
        double[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

   /************************************************************************************************************************************************/
    /*********************************                                COMPLEX                                   ************************************/
    /***********************************************************************************************************************************************/

   static boolean checkData(Complex observedValue, String fieldPath, boolean isReduced)
    {
        Complex expectedValue = null;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }

        expectedValue = Generator.randomData(expectedValue, isReduced);


        if( !expectedValue.equals(observedValue)) 
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + observedValue );
            System.err.println("\t\tExpected = " + expectedValue );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect1DComplex observedValue, String fieldPath, boolean isReduced)
    {
        Vect1DComplex expectedValue = null;
        Complex[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect2DComplex observedValue, String fieldPath, boolean isReduced)
    {
        Vect2DComplex expectedValue = null;
        Complex[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }
    static boolean checkData(Vect3DComplex observedValue, String fieldPath, boolean isReduced)
    {
        Vect3DComplex expectedValue = null;
        Complex[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }
    static boolean checkData(Vect4DComplex observedValue, String fieldPath, boolean isReduced)
    {
        Vect4DComplex expectedValue = null;
        Complex[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }
    static boolean checkData(Vect5DComplex observedValue, String fieldPath, boolean isReduced)
    {
        Vect5DComplex expectedValue = null;
        Complex[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }
    static boolean checkData(Vect6DComplex observedValue, String fieldPath, boolean isReduced)
    {
        Vect6DComplex expectedValue = null;
        Complex[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }


    /************************************************************************************************************************************************/
    /*********************************                                 String                                   ************************************/
    /************************************************************************************************************************************************/

    static boolean checkData(String observedValue, String fieldPath, boolean isReduced)
    {
        String expectedValue = null;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }

        expectedValue = Generator.randomData(expectedValue, isReduced);


        if( !expectedValue.equals(observedValue)) 
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + observedValue );
            System.err.println("\t\tExpected = " + expectedValue );
            return false;
        }

        return true;
    }

    static boolean checkData(Vect1DString observedValue, String fieldPath, boolean isReduced)
    {
        Vect1DString expectedValue = null;
        String[] observedData, expectedData;
        
        if(observedValue == null)
        {
            System.err.println(fieldPath + ": NULL detected");
            return false;
        }
        expectedValue = Generator.randomData(expectedValue, isReduced);

        observedData = observedValue.getArray();
        expectedData = expectedValue.getArray();

        if( !Arrays.equals(observedData, expectedData) )
        {
            System.err.println(fieldPath + " : different values!");
            System.err.println("\t\tObserved = " + Arrays.toString(observedData) );
            System.err.println("\t\tExpected = " + Arrays.toString(expectedData) );
            return false;
        }

        return true;
    }
}