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
       



     
   int INTEGER_DATA, DOUBLE_DATA, CHAR_DATA;

 
    	/************************************************************************************************************************************************/
    	/*********************************                                                                           ************************************/
    	/*********************************                           WRITE DATA                                      ************************************/
    	/*********************************                                                                           ************************************/
    	/************************************************************************************************************************************************/
  
	int writeData(int ctx, String fieldPath, String timeBasePath, int value)throws UALException
        {
        	int status = -1;
            int dataArray[] = {value};
            

		status =  LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArray, INTEGER_DATA, 0, null);
  		return status;
        }

	int writeData(int ctx, String fieldPath, String timeBasePath, Vect1DInt array)throws UALException
        {
        	int status = -1;
		int dataArr[] =  array.getArray();
		int arrayOfSizes[] = {	array.getDim()};

		status = LowLevel.ual_write_data_int(ctx, fieldPath, timeBasePath, dataArr, INTEGER_DATA, 1, arrayOfSizes);
  		return status;
        }



  	/************************************************************************************************************************************************/
	int writeData(int ctx, String fieldPath, String timeBasePath, double value)throws UALException
        {
        	int status = -1;
		double dataArr[] = {value};

		status =  LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, DOUBLE_DATA, 0, null);
  		return status;
        }

	int writeData(int ctx, String fieldPath, String timeBasePath, Vect1DDouble array)throws UALException
        {
        	int status = -1;
		double dataArr[] = array.getArray();
		int arrayOfSizes[] = {	array.getDim()};

		status = LowLevel.ual_write_data_double(ctx, fieldPath, timeBasePath, dataArr, DOUBLE_DATA, 1, arrayOfSizes);
  		return status;
        }

	
	/************************************************************************************************************************************************/

    	int writeData(int ctx, String fieldPath, String timeBasePath, String text)throws UALException
        {
        	int status = -1;
		int arrayOfSizes[] = {	text.length()};

		status = LowLevel.ual_write_data_char(ctx, fieldPath, timeBasePath, text.getBytes(), CHAR_DATA, 1, arrayOfSizes);
  		return status;
        }
/*
    	int writeData(int ctx, String fieldPath, String timeBasePath, const blitz::Array<String, 1> text)
        {
        	int status = -1;
		int maxStringSize = -1;
		int  numberOfStrings = text.extent(0);
		char* ptrData = NULL;
		char* ptrCString = NULL;
		int arrayOfSizes[2];
		int size;

		for(int i=0; i < numberOfStrings; i++)
		{
			size = text(i).size();
			if( size > maxStringSize)
				maxStringSize = size;
				
		}

		maxStringSize = maxStringSize + 1; //ending zero
		arrayOfSizes[0] = numberOfStrings; 
		arrayOfSizes[1] = maxStringSize;

		ptrData = (char*)  malloc(numberOfStrings * maxStringSize);
		memset(ptrData,  0 , numberOfStrings * maxStringSize);

		
		for(int i=0; i < numberOfStrings; i++)
		{
			ptrCString = const_cast<char *> (text(i));
			size = text(i).size();
			memcpy(ptrData + i * maxStringSize, ptrCString, size);	
		}

		status = ual_write_data(ctx, fieldPath, timeBasePath, (void*)ptrData, CHAR_DATA, 2, arrayOfSizes);
  		return status;
        }
*/
    	/************************************************************************************************************************************************/
    	/*********************************                                                                           ************************************/
    	/*********************************                             READ DATA                                     ************************************/
    	/*********************************                                                                           ************************************/
    	/************************************************************************************************************************************************/

	int readData(int ctx, String fieldPath, String timeBasePath, double value)throws UALException
        {
		int status = 0;
		int retSize[]  = null;
		double retVal = -1;
		double dataArr[] = null;

  		status = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, dataArr, DOUBLE_DATA, 0, retSize);
  		if (status != 0)
    			return status;
		
		value = dataArr[0];

  		return status;
	}


	int readData(int ctx, String fieldPath, String timeBasePath, Vect1DDouble array)throws UALException
        {
		int status = 0;
		int retSize[] = null;
		double dataArr[] = null;

  		status = LowLevel.ual_read_data_double(ctx, fieldPath, timeBasePath, dataArr, DOUBLE_DATA, 1, retSize);
  		if (status != 0)
    			return status;

		array.setArray(dataArr, retSize[0]);


  		return status;
	}





	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/

	int readData(int ctx, String fieldPath, String timeBasePath, int  value) throws UALException
        {
		int status = 0;
		int retSize[] = null;
		int retVal = -1;
		int dataArr[] = null;

  		status = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, dataArr, INTEGER_DATA, 0, retSize);
  		if (status != 0)
    			return status;
		
		value = dataArr[0];

  		return status;
	}


	int readData(int ctx, String fieldPath, String timeBasePath, Vect1DInt array) throws UALException
        {
		int status = 0;
		int retSize[] = null;
		int dataArr[] = null;

  		status = LowLevel.ual_read_data_int(ctx, fieldPath, timeBasePath, dataArr, INTEGER_DATA, 1, retSize);
  		if (status != 0)
    			return status;

		array.setArray(dataArr, retSize[0]);

  		return status;
	}


	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
    	int readData(int ctx, String fieldPath, String timeBasePath, String text) throws UALException
        {
        	int status = -1;
/*
		int retSize[];	
		void* ptrData = NULL;
		
		status = ual_read_data(ctx, fieldPath, timeBasePath, ptrData, CHAR_DATA, 1, retSize);
		if (status != 0)
    			return status;
		
		text = (char*)ptrData;
*/
  		return status;
        }
/*
	int readData(int ctx, String fieldPath, String timeBasePath, blitz::Array<String, 1> array)
        {
		int status = 0;
		int retSize[];
		char* ptrData = NULL;

		int  numberOfStrings = -1;
		int maxStringSize = -1;

  		status = ual_read_data(ctx, fieldPath, timeBasePath, (void**)(ptrData), CHAR_DATA, 2, retSize);
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

