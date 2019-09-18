
import java.util.Arrays;
import java.util.Random;
import imasjava.*;



public class Generator {


    public static final int DIM_1 = 2;
    private static final int DIM_2 = 2;
    private static final int DIM_3 = 2;
    private static final int DIM_4 = 2;
    private static final int DIM_5 = 2;
    private static final int DIM_6 = 2;


    private static int dim1 = DIM_1;
    private static int dim2 = DIM_2;
    private static int dim3 = DIM_3;
    private static int dim4 = DIM_4;
    private static int dim5 = DIM_5;
    private static int dim6 = DIM_6;

    private static final Random RANDOM = new Random();
    //private static final String PRINTABLE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~\t\n\r";
    private static final String PRINTABLE = "0123l";
    private static final long SEED = RANDOM.nextInt();

    private static Vect1DDouble timeVector = null;



    /************************************************************************************************************************************************/
    /*********************************                         RANDOM VALUES GENERATION                          ************************************/
    /************************************************************************************************************************************************/

   public static void clearSeed() {
        RANDOM.setSeed(SEED);
    }


    private static int generateInteger() {
        return RANDOM.nextInt();
    }

    private static int[] generateIntegerArray(int size)
    {
        int array[] = new int[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = generateInteger();
        }
    
        return array;
    }
    

    private static double generateDouble() {
        return RANDOM.nextGaussian();
    }

    private static double[] generateDoubleArray(int size)
    {
        double array[] = new double[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = generateDouble();
        }
    
        return array;
    }


        private static Complex generateComplex() 
    {
        double dReal =  generateDouble() ;
        double dImaginary =  generateDouble();
    
        return new Complex(dReal, dImaginary);
    }


     private static Complex[] generateComplexArray(int size)
    {
        Complex array[] = new Complex[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = generateComplex();
        }
    
        return array;
    }
    

    /*private static String generateString() {
        int length = (Math.abs(RANDOM.nextInt()) % 1024) + 8;
        StringBuilder builder = new StringBuilder();
        while (length-- > 0)
            builder.append(PRINTABLE.charAt(Math.abs(RANDOM.nextInt()) % PRINTABLE.length()));
        return builder.toString();
    }
*/ 
private static String generateString() {

        return PRINTABLE;
    }

   private static  String[] generateStringArray(int size)
    {
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = generateString();
        }
    
        return array;
    }


    private static Vect1DDouble getTimeVector()
    {
        if(Generator.timeVector == null)
        {
            Generator.timeVector = new Vect1DDouble(dim1);
            for (int i = 0; i < dim1; i++)
                Generator.timeVector.setElementAt(i, (double) i);
        }


        return Generator.timeVector;
    }

    /*******************************************************************************/
    /**********************        TIME           ***********************/
    /*******************************************************************************/
    static Vect1DDouble getTimeVector(int timeIdx)
    {
        Vect1DDouble retObject;
        Vect1DDouble timeVector;
    
    
        timeVector = getTimeVector();
    
    
    
        if(timeIdx >= 0)
        {
            retObject = new Vect1DDouble(1);
            double time = timeVector.getElementAt(timeIdx);
            retObject.setElementAt(0, time);
        }
        else
        {
            retObject = timeVector;
        }
    
        return retObject;
    }


    static double getTimeScalar(int timeIdx)
    {
        double time;
        Vect1DDouble  timeVector = getTimeVector();
  
        time = timeVector.getElementAt(timeIdx);

    
        return time;
    }



    /************************************************************************************************************************************************/
    /*********************************                                 INTEGER                                   ************************************/
    /************************************************************************************************************************************************/


    static int randomData(int field, boolean isReduced) 
    {
        int value;

        value = generateInteger();

        return value;
    }

    static Vect1DInt randomData(Vect1DInt array, boolean isReduced) 
    {
        int flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim1;

        flatArray = generateIntegerArray(lastDim);
        array =  new Vect1DInt(flatArray);
        return array;
    }

    static Vect2DInt randomData(Vect2DInt array, boolean isReduced)
    {
        int flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim2;

        flatArray = generateIntegerArray(dim1 * lastDim);
        array =  new Vect2DInt(dim1, lastDim, flatArray);
        return array;
    }

    static Vect3DInt randomData(Vect3DInt array, boolean isReduced)
    {
        int flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim3;

        flatArray = generateIntegerArray(dim1 * dim2 * lastDim);
        array =  new Vect3DInt(dim1, dim2, lastDim, flatArray);
        return array;
    }

    /************************************************************************************************************************************************/
    /*********************************                                  DOUBLE                                   ************************************/
    /************************************************************************************************************************************************/

    static double randomData(double field, boolean isReduced)
    {
        double value;

        value = generateDouble();

        return value;
    }

    static Vect1DDouble randomData(Vect1DDouble array, boolean isReduced)
    {
        double flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim1;

        flatArray = generateDoubleArray(lastDim);
        array =  new Vect1DDouble(flatArray);
        return array;
    }

   static Vect2DDouble randomData(Vect2DDouble array, boolean isReduced)
    {
        double flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim2;

        flatArray = generateDoubleArray(dim1 * lastDim);
        array =  new Vect2DDouble(dim1, lastDim, flatArray);
        return array;
    }

    static Vect3DDouble randomData(Vect3DDouble array, boolean isReduced)
    {
        double flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim3;

        flatArray = generateDoubleArray(dim1 * dim2 * lastDim);
        array =  new Vect3DDouble(dim1, dim2, lastDim, flatArray);
        return array;
    }

    static Vect4DDouble randomData(Vect4DDouble array, boolean isReduced)
    {
        double flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim4;

        flatArray = generateDoubleArray(dim1 * dim2 * dim3 * lastDim);
        array =  new Vect4DDouble(dim1, dim2, dim3, lastDim, flatArray);
        return array;
    }


    static Vect5DDouble randomData(Vect5DDouble array, boolean isReduced)
    {
        double flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim5;

        flatArray = generateDoubleArray(dim1 * dim2 * dim3 * dim4 * lastDim);
        array =  new Vect5DDouble(dim1, dim2, dim3, dim4, lastDim, flatArray);
        return array;
    }

    static Vect6DDouble randomData(Vect6DDouble array, boolean isReduced)
    {
        double flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim6;

        flatArray = generateDoubleArray(dim1 * dim2 * dim3 * dim4 * dim5 * lastDim);
        array =  new Vect6DDouble(dim1, dim2, dim3, dim4, dim5, lastDim, flatArray);
        return array;
    }

   /************************************************************************************************************************************************/
    /*********************************                                COMPLEX                                   ************************************/
    /***********************************************************************************************************************************************/

    static Complex randomData(Complex field, boolean isReduced)
    {
        Complex value;

        value = generateComplex();

        return value;
    }

    static Vect1DComplex randomData(Vect1DComplex array, boolean isReduced)
    {
        Complex flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim1;

        flatArray = generateComplexArray(lastDim);
        array =  new Vect1DComplex(flatArray);
        return array;
    }

    static Vect2DComplex randomData(Vect2DComplex array, boolean isReduced)
    {
        Complex flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim2;

        flatArray = generateComplexArray(dim1 * lastDim);
        array =  new Vect2DComplex(dim1, lastDim, flatArray);
        return array;
    }

    static Vect3DComplex randomData(Vect3DComplex array, boolean isReduced)
    {
        Complex flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim3;

        flatArray = generateComplexArray(dim1 * dim2 * lastDim);
        array =  new Vect3DComplex(dim1, dim2, lastDim, flatArray);
        return array;
    }

    static Vect4DComplex randomData(Vect4DComplex array, boolean isReduced)
    {
        Complex flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim4;

        flatArray = generateComplexArray(dim1 * dim2 * dim3 * lastDim);
        array =  new Vect4DComplex(dim1, dim2, dim3, lastDim, flatArray);
        return array;
    }


    static Vect5DComplex randomData(Vect5DComplex array, boolean isReduced)
    {
        Complex flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim5;

        flatArray = generateComplexArray(dim1 * dim2 * dim3 * dim4 * lastDim);
        array =  new Vect5DComplex(dim1, dim2, dim3, dim4, lastDim, flatArray);
        return array;
    }

    static Vect6DComplex randomData(Vect6DComplex array, boolean isReduced)
    {
        Complex flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim6;

        flatArray = generateComplexArray(dim1 * dim2 * dim3 * dim4 * dim5 * lastDim);
        array =  new Vect6DComplex(dim1, dim2, dim3, dim4, dim5, lastDim, flatArray);
        return array;
    }

    /************************************************************************************************************************************************/
    /*********************************                                 String                                   ************************************/
    /************************************************************************************************************************************************/


    static String randomData(String field, boolean isReduced) 
    {
        String value;

        value = generateString();

        return value;
    }

    static Vect1DString randomData(Vect1DString array, boolean isReduced)
    {
        String flatArray[] = null;
        int lastDim = -1;
    
        if(isReduced)
            lastDim = 1;
        else
            lastDim = dim1;

        flatArray = generateStringArray(lastDim);
        array =  new Vect1DString(flatArray);
        return array;
    }


}