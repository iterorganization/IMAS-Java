package imasjava.wrapper;

import java.util.Arrays;
import imasjava.*;

public class Wrapper {


        void setArray(Vect1DInt array,int dataArr[], int dim1)
        {
       /*    Vect1DInt newArray(arrayPtr, shape(dim1), duplicateData);
            array.resize(newArray.shape());
            array = newArray;
       */ }

        void setArray(Vect1DDouble array,double dataArr[], int dim1)
        {
    /*        Vect1DDouble array =     public Vect7DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, int dim7, double array[])(arrayPtr, shape(dim1), duplicateData);
            array.resize(newArray.shape());
            array = newArray;
      */  }
       



     


    	/************************************************************************************************************************************************/
    	/*********************************                                                                           ************************************/
    	/*********************************                           WRITE DATA                                      ************************************/
    	/*********************************                                                                           ************************************/
    	/************************************************************************************************************************************************/
  
	static public void writeData(int ctx, String fieldPath, String timeBasePath, int value)throws UALException
        {
            if(value == LowLevel.EMPTY_INT) 
                return;

            int dataArray[] = {value};
            int arrayOfSizes[] = { 0 }; // LowLevel doesn like null size....
            

		 LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArray, 0, arrayOfSizes);
        }

	    static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect1DInt array)throws UALException
        {
            if(array == null) 
                return;
		int dataArr[] =  array.getArray();
		int arrayOfSizes[] = {	array.getDim()};

		LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArr, 1, arrayOfSizes);
        }


        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect2DInt array)throws UALException
        {            
            if(array == null) 
                return;
            int dataArr[] =  array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1) };

            LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArr, 2, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect3DInt array)throws UALException
        {
            if(array == null) 
                return;

            int dataArr[] =  array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2) };

            LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArr, 3, arrayOfSizes);
    
        }


  	/************************************************************************************************************************************************/
	static public void writeData(int ctx, String fieldPath, String timeBasePath, double value)throws UALException
    {
       if(value == LowLevel.EMPTY_DOUBLE) 
                return;

        int arrayOfSizes[] = { 0 }; // LowLevel doesn like null size....
		double dataArr[] = {value};

		 LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 0, arrayOfSizes);

    }

	static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect1DDouble array)throws UALException
        {
        if(array == null || array.getArray().length < 1) 
                return;
		double dataArr[] = array.getArray();
		int arrayOfSizes[] = {	array.getDim()};

		LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 1, arrayOfSizes);

        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect2DDouble array)throws UALException
        {
            if(array == null || array.getArray().length < 1) 
                return;
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 2, arrayOfSizes);
    
        }
	
        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect3DDouble array)throws UALException
        {
            if(array == null || array.getArray().length < 1) 
                return;
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2)  };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 3, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect4DDouble array)throws UALException
        {
           if(array == null || array.getArray().length < 1) 
                return;

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3)   };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 4, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect5DDouble array)throws UALException
        {
            if(array == null || array.getArray().length < 1) 
                return;

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 5, arrayOfSizes);
    
        }

       static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect6DDouble array)throws UALException
        {
            if(array == null || array.getArray().length < 1) 
                return;
           
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4), array.getDim(5) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 6, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect7DDouble array)throws UALException
        {
            if(array == null || array.getArray().length < 1) 
                return;

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4), array.getDim(5), array.getDim(6) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 7, arrayOfSizes);
    
        }
	/************************************************************************************************************************************************/

    	static public void writeData(int ctx, String fieldPath, String timeBasePath, String text)throws UALException
        {
            if(text == null || text.length() < 1) 
                return;

		    int arrayOfSizes[] = {text.getBytes().length};

		    LowLevel.ual_write_data_char(ctx, fieldPath, timeBasePath, text.getBytes(), 1, arrayOfSizes);
        }


        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect1DString array)throws UALException
        {
            if(array == null || array.getArray().length < 1) 
                return;

            int arrayOfSizes[] = new int[2];

        int maxStringSize = -1;
        int  numberOfStrings = array.getDim();
        String text = null;
        byte[] textBytes = null;



        byte strByteArray[] = null;
        int size;
        
        //return on empty array
        if(numberOfStrings < 1)
            return;

        for(int i = 0; i < numberOfStrings; i++)
        {
            text = array.getElementAt(i);
            size = text.getBytes().length;
            if( size > maxStringSize)
                maxStringSize = size;
                
        }

         if(maxStringSize < 1)
            return;

        arrayOfSizes[0] = numberOfStrings; 
        arrayOfSizes[1] = maxStringSize;

        strByteArray = new byte [numberOfStrings * maxStringSize];
        
        for(int i=0; i < numberOfStrings; i++)
        {
            text = array.getElementAt(i);
            textBytes = text.getBytes();
            size = textBytes.length;
            System.arraycopy(textBytes, 0, strByteArray,  i * maxStringSize, size);
           
        }


            LowLevel.ual_write_data_char(ctx, fieldPath, timeBasePath, strByteArray, 2, arrayOfSizes);
        }

    	/************************************************************************************************************************************************/
    	/*********************************                                                                           ************************************/
    	/*********************************                             READ DATA                                     ************************************/
    	/*********************************                                                                           ************************************/
    	/************************************************************************************************************************************************/

	static public double readData(int ctx, String fieldPath, String timeBasePath, double value)throws UALException
        {

		int retSize[]  = new int[1];
		double retVal = -1;
		double dataArr[] = null;

  		dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 0, retSize);

		
		retVal = dataArr[0];

        return retVal;
	}


	static public Vect1DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect1DDouble array)throws UALException
        {

		int retSize[] = new int[1];
		double dataArr[] = null;

  		dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 1, retSize);


		array = new Vect1DDouble(dataArr);
        return array;
	}

    static public Vect2DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect2DDouble array)throws UALException
        {

        int retSize[] = new int[2];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 2, retSize);

        array = new Vect2DDouble(retSize[0], retSize[1], dataArr);
        return array;
    }


  static public Vect3DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect3DDouble array)throws UALException
  {

        int retSize[] = new int[3];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 3, retSize);

        array = new Vect3DDouble(retSize[0], retSize[1], retSize[2], dataArr);
        return array;
    }

  static public Vect4DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect4DDouble array)throws UALException
        {

        int retSize[] = new int[4];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 4, retSize);


        array = new Vect4DDouble(retSize[0], retSize[1], retSize[2], retSize[3], dataArr);

        return array;

    }

  static public Vect5DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect5DDouble array)throws UALException
        {

        int retSize[] = new int[5];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 5, retSize);
;

       array = new  Vect5DDouble(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], dataArr);

        return array;

    }
  static public Vect6DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect6DDouble array)throws UALException
        {

        int retSize[] = new int[6];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 6, retSize);

        array = new Vect6DDouble(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], retSize[5], dataArr);

        return array;

    }

  static public Vect7DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect7DDouble array)throws UALException
        {

        int retSize[] = new int[7];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, 7, retSize);


        array = new Vect7DDouble(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], retSize[5], retSize[6], dataArr);


        return array;
    }

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/

	static public int readData(int ctx, String fieldPath, String timeBasePath, int  value) throws UALException
        {

        int retSize[]  = new int[1];
		int retVal = -1;
		int dataArr[] = null;

  		dataArr = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, 0, retSize);

		
		retVal = dataArr[0];
        return retVal;
	}


	static public Vect1DInt readData(int ctx, String fieldPath, String timeBasePath, Vect1DInt array) throws UALException
        {

        int retSize[]  = new int[1];
		int dataArr[] = null;



  		dataArr = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, 1, retSize);

		array = new Vect1DInt(dataArr);
        return array;

	}

    static public Vect2DInt readData(int ctx, String fieldPath, String timeBasePath, Vect2DInt array) throws UALException
        {

        int retSize[]  = new int[2];
        int dataArr[] = null;

        dataArr = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, 2, retSize);


        array = new Vect2DInt(retSize[0], retSize[1], dataArr);

        return array;
    }

    static public Vect3DInt readData(int ctx, String fieldPath, String timeBasePath, Vect3DInt array) throws UALException
        {

        int retSize[]  = new int[3];
        int dataArr[] = null;

        dataArr = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, 3, retSize);


        array = new Vect3DInt(retSize[0], retSize[1], retSize[2], dataArr);
        return array;

    }

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
    static public String readData(int ctx, String fieldPath, String timeBasePath, String text) throws UALException
    {
        

		int retSize[] = new int[1];	
		byte dataArr[] = null;
        String str = null;

		
		dataArr = LowLevel.ual_read_data_char(ctx, fieldPath, timeBasePath, 1, retSize);

		
		text = new String(dataArr);
        return text;

    }

	static public Vect1DString readData(int ctx, String fieldPath, String timeBasePath, Vect1DString array) throws UALException
    {
        int retSize[] = new int[2]; 
        byte dataArr[] = null;
        String strArr[] = null;

		int numberOfStrings = -1;
		int maxStringSize = -1;

  		dataArr = LowLevel.ual_read_data_char(ctx, fieldPath, timeBasePath, 2, retSize);
 

		numberOfStrings = retSize[0];
		maxStringSize = retSize[1];

		strArr = new String[numberOfStrings];

		for(int i=0; i < numberOfStrings; i++)
		{
            byte buffer[] = Arrays.copyOfRange(dataArr, i * maxStringSize, maxStringSize);
			String str = new String(buffer);
			strArr[i] = str; 	
		}

  	
        array = new Vect1DString(strArr);
        return array;
	}


}

