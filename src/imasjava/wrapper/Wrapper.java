package imasjava.wrapper;


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
        
            int dataArray[] = {value};
            

		 LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArray, LowLevel.INTEGER_DATA, 0, null);
        }

	    static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect1DInt array)throws UALException
        {
        
		int dataArr[] =  array.getArray();
		int arrayOfSizes[] = {	array.getDim()};

		LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArr, LowLevel.INTEGER_DATA, 1, arrayOfSizes);
        }


        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect2DInt array)throws UALException
        {
           
            int dataArr[] =  array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1) };

            LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArr, LowLevel.INTEGER_DATA, 2, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect3DInt array)throws UALException
        {
           
            int dataArr[] =  array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2) };

            LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArr, LowLevel.INTEGER_DATA, 3, arrayOfSizes);
    
        }


  	/************************************************************************************************************************************************/
	static public void writeData(int ctx, String fieldPath, String timeBasePath, double value)throws UALException
        {
		double dataArr[] = {value};

		 LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 0, null);

        }

	static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect1DDouble array)throws UALException
        {
		double dataArr[] = array.getArray();
		int arrayOfSizes[] = {	array.getDim()};

		LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 1, arrayOfSizes);

        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect2DDouble array)throws UALException
        {
           
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 2, arrayOfSizes);
    
        }
	
        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect3DDouble array)throws UALException
        {
           
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2)  };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 3, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect4DDouble array)throws UALException
        {
           
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3)   };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 4, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect5DDouble array)throws UALException
        {
           
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 5, arrayOfSizes);
    
        }

       static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect6DDouble array)throws UALException
        {
           
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4), array.getDim(5) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 6, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect7DDouble array)throws UALException
        {
           
            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4), array.getDim(5), array.getDim(6) };

            LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, LowLevel.DOUBLE_DATA, 7, arrayOfSizes);
    
        }
	/************************************************************************************************************************************************/

    	static public void writeData(int ctx, String fieldPath, String timeBasePath, String text)throws UALException
        {
		    int arrayOfSizes[] = {	text.length()};

		    LowLevel.ual_write_data_char(ctx, fieldPath, timeBasePath, text.getBytes(), LowLevel.CHAR_DATA, 1, arrayOfSizes);
        }


        static public void writeData(int ctx, String fieldPath, String timeBasePath, Vect1DString array)throws UALException
        {
            int arrayOfSizes[] = new int[2];

        int maxStringSize = -1;
        int  numberOfStrings = array.getDim();
        String text = null;
        byte[] textBytes = null;



        byte strByteArray[] = null;
        int size;

        for(int i = 0; i < numberOfStrings; i++)
        {
            text = array.getElementAt(i);
            size = text.getBytes().length;
            if( size > maxStringSize)
                maxStringSize = size;
                
        }

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


            LowLevel.ual_write_data_char(ctx, fieldPath, timeBasePath, strByteArray, LowLevel.CHAR_DATA, 2, arrayOfSizes);
        }

    	/************************************************************************************************************************************************/
    	/*********************************                                                                           ************************************/
    	/*********************************                             READ DATA                                     ************************************/
    	/*********************************                                                                           ************************************/
    	/************************************************************************************************************************************************/

	static public void readData(int ctx, String fieldPath, String timeBasePath, double value)throws UALException
        {

		int retSize[]  = new int[1];
		double retVal = -1;
		double dataArr[] = null;

  		dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 0, retSize);

		
		value = dataArr[0];


	}


	static public void readData(int ctx, String fieldPath, String timeBasePath, Vect1DDouble array)throws UALException
        {

		int retSize[] = new int[1];
		double dataArr[] = null;

  		dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 1, retSize);


		array.setArray(dataArr, retSize[0]);



	}

    static public void readData(int ctx, String fieldPath, String timeBasePath, Vect2DDouble array)throws UALException
        {

        int retSize[] = new int[2];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 2, retSize);


        array.setArray(dataArr, retSize[0], retSize[1]);



    }


  static public void readData(int ctx, String fieldPath, String timeBasePath, Vect3DDouble array)throws UALException
        {

        int retSize[] = new int[3];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 3, retSize);


        array.setArray(dataArr, retSize[0], retSize[1], retSize[2]);



    }

  static public void readData(int ctx, String fieldPath, String timeBasePath, Vect4DDouble array)throws UALException
        {

        int retSize[] = new int[4];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 4, retSize);


        array.setArray(dataArr, retSize[0], retSize[1], retSize[2], retSize[3]);



    }

  static public void readData(int ctx, String fieldPath, String timeBasePath, Vect5DDouble array)throws UALException
        {

        int retSize[] = new int[5];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 5, retSize);
;

        array.setArray(dataArr, retSize[0], retSize[1], retSize[2], retSize[3], retSize[4]);



    }
  static public void readData(int ctx, String fieldPath, String timeBasePath, Vect6DDouble array)throws UALException
        {

        int retSize[] = new int[6];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 6, retSize);

        array.setArray(dataArr, retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], retSize[5]);



    }

  static public void readData(int ctx, String fieldPath, String timeBasePath, Vect7DDouble array)throws UALException
        {

        int retSize[] = new int[7];
        double dataArr[] = null;

        dataArr = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, LowLevel.DOUBLE_DATA, 7, retSize);


        array.setArray(dataArr, retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], retSize[5], retSize[6]);



    }

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/

	static public void readData(int ctx, String fieldPath, String timeBasePath, int  value) throws UALException
        {

        int retSize[]  = new int[1];
		int retVal = -1;
		int dataArr[] = null;

  		LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, LowLevel.INTEGER_DATA, 0, retSize);

		
		value = dataArr[0];

	}


	static public void readData(int ctx, String fieldPath, String timeBasePath, Vect1DInt array) throws UALException
        {

        int retSize[]  = new int[1];
		int dataArr[] = null;

  		dataArr = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, LowLevel.INTEGER_DATA, 1, retSize);


		array.setArray(dataArr, retSize[0]);

	}

    static public void readData(int ctx, String fieldPath, String timeBasePath, Vect2DInt array) throws UALException
        {

        int retSize[]  = new int[2];
        int dataArr[] = null;

        dataArr = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, LowLevel.INTEGER_DATA, 2, retSize);


        array.setArray(dataArr, retSize[0], retSize[1]);


    }

    static public void readData(int ctx, String fieldPath, String timeBasePath, Vect3DInt array) throws UALException
        {

        int retSize[]  = new int[3];
        int dataArr[] = null;

        dataArr = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, LowLevel.INTEGER_DATA, 3, retSize);


        array.setArray(dataArr, retSize[0], retSize[1], retSize[2]);


    }

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
    	static public void readData(int ctx, String fieldPath, String timeBasePath, String text) throws UALException
        {
        
/*
		int retSize[];	
		void* ptrData = NULL;
		
		ual_read_data(ctx, fieldPath, timeBasePath, ptrData, CHAR_DATA, 1, retSize);
		if (status != 0)
    			return status;
		
		text = (char*)ptrData;

        }
/*
	static public void readData(int ctx, String fieldPath, String timeBasePath, blitz::Array<String, 1> array)
        {

		int retSize[];
		char* ptrData = NULL;

		int  numberOfStrings = -1;
		int maxStringSize = -1;

  		ual_read_data(ctx, fieldPath, timeBasePath, (void**)(ptrData), CHAR_DATA, 2, retSize);
  		if (status != 0)
    			return status;

		numberOfStrings = retSize;
		maxStringSize = retSize[1];

		array.resize(numberOfStrings);

		for(int i=0; i < numberOfStrings; i++)
		{
			
			array(i) = ptrData; 	
			ptrData = ptrData + maxStringSize;	
		}

  		return status;
	}
*/
}
}

