package imasjava.wrapper;

import java.util.Arrays;
import imasjava.*;

public class Wrapper {

		static private void warningWritingObsolescentNode(String idsName, String fieldPath, String lifeCycleStatus) 
        {
			String disable_obsolescent_warning_var = System.getenv("IMAS_AL_DISABLE_OBSOLESCENT_WARNING");
			int disable_obsolescent_warning = 0;
			if (disable_obsolescent_warning_var != null) {
				disable_obsolescent_warning = Integer.parseInt(disable_obsolescent_warning_var);
			}
			if (disable_obsolescent_warning==1)
				return;
	   
            if (lifeCycleStatus.equals(LowLevel.LIFECYCLE_STATUS_OBSOLETE))
                System.out.println("Warning : while putting IDS " + idsName + ", the written IDS has non-empty obsolescent node " + fieldPath + ". Please consider updating the code to avoid using obsolescent nodes.");
        }

        static public String alBuildUriFromLegacyParameters(int backendID, int shot, int run, String user, String tokamak, String version, String options) throws ALException
        {

            user = user.trim();
            tokamak = tokamak.trim();
            version = version.trim();
            options = options.trim();
            return LowLevel.al_build_uri_from_legacy_parameters(backendID, shot, run, user, tokamak, version, options);
        }

        static public int alBeginDataEntryAction(String uri, int mode) throws ALException
        {
            int pulseCtx = -1;

            uri = uri.trim();
            pulseCtx = LowLevel.al_begin_dataentry_action(uri, mode);

            return pulseCtx;
        }


    	/************************************************************************************************************************************************/
    	/*********************************                                                                           ************************************/
    	/*********************************                           WRITE DATA                                      ************************************/
    	/*********************************                                                                           ************************************/
    	/************************************************************************************************************************************************/
  
	    static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, int value, String lifeCycleStatus)throws ALException
        {
                if(value != LowLevel.EMPTY_INT) 
                    Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                
                if (value != LowLevel.EMPTY_INT || (value == LowLevel.EMPTY_INT && (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE")))) {
                    int dataArray[] = {value};
                    int arrayOfSizes[] = { 0 }; // LowLevel doesn like null size....
                    LowLevel.al_write_data_int(ctx, fieldPath, timeBasePath, dataArray, 0, arrayOfSizes);
                }
        }

	    static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect1DInt array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_int(ctx, fieldPath, timeBasePath, null, 1, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

		    int dataArr[] =  array.getArray();
		    int arrayOfSizes[] = {	array.getDim()};
		    LowLevel.al_write_data_int(ctx, fieldPath, timeBasePath, dataArr, 1, arrayOfSizes);
        }


        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect2DInt array, String lifeCycleStatus)throws ALException
        {            
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_int(ctx, fieldPath, timeBasePath, null, 2, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            int dataArr[] =  array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1) };

            LowLevel.al_write_data_int(ctx, fieldPath, timeBasePath, dataArr, 2, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect3DInt array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_int(ctx, fieldPath, timeBasePath, null, 3, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            int dataArr[] =  array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2) };

            LowLevel.al_write_data_int(ctx, fieldPath, timeBasePath, dataArr, 3, arrayOfSizes);
    
        }


  	/************************************************************************************************************************************************/
	    static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, double value, String lifeCycleStatus)throws ALException
        {
            if(value != LowLevel.EMPTY_DOUBLE) 
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);
            
            String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
            if (value != LowLevel.EMPTY_DOUBLE || (value == LowLevel.EMPTY_DOUBLE && (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))))  {
                int arrayOfSizes[] = { 0 }; // LowLevel doesn like null size....
		        double dataArr[] = {value};
		        LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 0, arrayOfSizes);
            }
        }

	    static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect1DDouble array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
		            LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, null, 1, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);
    
		    double dataArr[] = array.getArray();
		    int arrayOfSizes[] = {	array.getDim()};
    
		    LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 1, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect2DDouble array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, null, 2, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1) };

            LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 2, arrayOfSizes);
    
        }
	
        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect3DDouble array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, null, 3, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2)  };

            LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 3, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect4DDouble array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, null, 4, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3)   };

            LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 4, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect5DDouble array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, null, 5, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4) };

            LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 5, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect6DDouble array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, null, 6, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4), array.getDim(5) };

            LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 6, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect7DDouble array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, null, 7, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            double dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4), array.getDim(5), array.getDim(6) };

            LowLevel.al_write_data_double(ctx, fieldPath, timeBasePath, dataArr, 7, arrayOfSizes);
    
        }
        /************************************************************************************************************************************************/
        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Complex value, String lifeCycleStatus)throws ALException
        {
            if(value != LowLevel.EMPTY_COMPLEX) 
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);
            
            String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
            if (value != LowLevel.EMPTY_COMPLEX || (value == LowLevel.EMPTY_COMPLEX && (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))))  {
                int arrayOfSizes[] = { 0 }; // LowLevel doesn like null size....
                Complex dataArr[] = {value};
                LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, dataArr, 0, arrayOfSizes);
            }
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect1DComplex array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, null, 1, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            Complex dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim()};
    
            LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, dataArr, 1, arrayOfSizes);

        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect2DComplex array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, null, 2, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            Complex dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1) };

            LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, dataArr, 2, arrayOfSizes);
    
        }
    
        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect3DComplex array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, null, 3, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            Complex dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2)  };

            LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, dataArr, 3, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect4DComplex array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, null, 4, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            Complex dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3)   };

            LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, dataArr, 4, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect5DComplex array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, null, 5, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

            Complex dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4) };

            LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, dataArr, 5, arrayOfSizes);
    
        }

        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect6DComplex array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, null, 6, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);
           
            Complex dataArr[] = array.getArray();
            int arrayOfSizes[] = {  array.getDim(0), array.getDim(1), array.getDim(2), array.getDim(3), array.getDim(4), array.getDim(5) };

            LowLevel.al_write_data_complex(ctx, fieldPath, timeBasePath, dataArr, 6, arrayOfSizes);
    
        }

        /************************************************************************************************************************************************/
    	static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, String text, String lifeCycleStatus)throws ALException
        {
            if(text == null || text.length() < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS.equals("TRUE"))
                    LowLevel.al_write_data_char(ctx, fieldPath, timeBasePath, null, 1, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

		    int arrayOfSizes[] = {text.getBytes().length};

		    LowLevel.al_write_data_char(ctx, fieldPath, timeBasePath, text.getBytes(), 1, arrayOfSizes);
        }


        static public void writeData(int ctx, String idsName, String fieldPath, String timeBasePath, Vect1DString array, String lifeCycleStatus)throws ALException
        {
            if(array == null || array.getArray().length < 1) {
                String IMAS_AL_ENABLE_PLUGINS = System.getenv("IMAS_AL_ENABLE_PLUGINS");
                if (IMAS_AL_ENABLE_PLUGINS != null && IMAS_AL_ENABLE_PLUGINS == "TRUE")
                    LowLevel.al_write_data_char(ctx, fieldPath, timeBasePath, null, 2, null);
                return;
            }
            else
                Wrapper.warningWritingObsolescentNode(idsName, fieldPath, lifeCycleStatus);

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
    
    
                LowLevel.al_write_data_char(ctx, fieldPath, timeBasePath, strByteArray, 2, arrayOfSizes);
        }

    	/************************************************************************************************************************************************/
    	/*********************************                                                                           ************************************/
    	/*********************************                             READ DATA                                     ************************************/
    	/*********************************                                                                           ************************************/
    	/************************************************************************************************************************************************/

	static public double readData(int ctx, String fieldPath, String timeBasePath, double value)throws ALException
        {

		int retSize[]  = new int[1];
		double retVal = -1;
		double dataArr[] = null;

  		dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 0, retSize);

		
		retVal = dataArr[0];

        return retVal;
	}


	static public Vect1DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect1DDouble array)throws ALException
        {

		int retSize[] = new int[1];
		double dataArr[] = null;

  		dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 1, retSize);


		array = new Vect1DDouble(dataArr);
        return array;
	}

    static public Vect2DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect2DDouble array)throws ALException
        {

        int retSize[] = new int[2];
        double dataArr[] = null;

        dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 2, retSize);

        array = new Vect2DDouble(retSize[0], retSize[1], dataArr);
        return array;
    }


  static public Vect3DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect3DDouble array)throws ALException
  {

        int retSize[] = new int[3];
        double dataArr[] = null;

        dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 3, retSize);

        array = new Vect3DDouble(retSize[0], retSize[1], retSize[2], dataArr);
        return array;
    }

  static public Vect4DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect4DDouble array)throws ALException
        {

        int retSize[] = new int[4];
        double dataArr[] = null;

        dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 4, retSize);


        array = new Vect4DDouble(retSize[0], retSize[1], retSize[2], retSize[3], dataArr);

        return array;

    }

  static public Vect5DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect5DDouble array)throws ALException
        {

        int retSize[] = new int[5];
        double dataArr[] = null;

        dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 5, retSize);
;

       array = new  Vect5DDouble(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], dataArr);

        return array;

    }
  static public Vect6DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect6DDouble array)throws ALException
        {

        int retSize[] = new int[6];
        double dataArr[] = null;

        dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 6, retSize);

        array = new Vect6DDouble(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], retSize[5], dataArr);

        return array;

    }

  static public Vect7DDouble readData(int ctx, String fieldPath, String timeBasePath, Vect7DDouble array)throws ALException
        {

        int retSize[] = new int[7];
        double dataArr[] = null;

        dataArr = LowLevel.al_read_data_double(ctx, fieldPath, timeBasePath, 7, retSize);


        array = new Vect7DDouble(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], retSize[5], retSize[6], dataArr);


        return array;
    }

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
    static public Complex readData(int ctx, String fieldPath, String timeBasePath, Complex value)throws ALException
        {

        int retSize[]  = new int[1];
        Complex retVal;
        Complex dataArr[] = null;

        dataArr = LowLevel.al_read_data_complex(ctx, fieldPath, timeBasePath, 0, retSize);

        
        retVal = dataArr[0];

        return retVal;
    }


    static public Vect1DComplex readData(int ctx, String fieldPath, String timeBasePath, Vect1DComplex array)throws ALException
        {

        int retSize[] = new int[1];
        Complex dataArr[] = null;

        dataArr = LowLevel.al_read_data_complex(ctx, fieldPath, timeBasePath, 1, retSize);


        array = new Vect1DComplex(dataArr);
        return array;
    }

    static public Vect2DComplex readData(int ctx, String fieldPath, String timeBasePath, Vect2DComplex array)throws ALException
        {

        int retSize[] = new int[2];
        Complex dataArr[] = null;

        dataArr = LowLevel.al_read_data_complex(ctx, fieldPath, timeBasePath, 2, retSize);

        array = new Vect2DComplex(retSize[0], retSize[1], dataArr);
        return array;
    }


  static public Vect3DComplex readData(int ctx, String fieldPath, String timeBasePath, Vect3DComplex array)throws ALException
  {

        int retSize[] = new int[3];
        Complex dataArr[] = null;

        dataArr = LowLevel.al_read_data_complex(ctx, fieldPath, timeBasePath, 3, retSize);

        array = new Vect3DComplex(retSize[0], retSize[1], retSize[2], dataArr);
        return array;
    }

  static public Vect4DComplex readData(int ctx, String fieldPath, String timeBasePath, Vect4DComplex array)throws ALException
        {

        int retSize[] = new int[4];
        Complex dataArr[] = null;

        dataArr = LowLevel.al_read_data_complex(ctx, fieldPath, timeBasePath, 4, retSize);


        array = new Vect4DComplex(retSize[0], retSize[1], retSize[2], retSize[3], dataArr);

        return array;

    }

  static public Vect5DComplex readData(int ctx, String fieldPath, String timeBasePath, Vect5DComplex array)throws ALException
        {

        int retSize[] = new int[5];
        Complex dataArr[] = null;

        dataArr = LowLevel.al_read_data_complex(ctx, fieldPath, timeBasePath, 5, retSize);

       array = new  Vect5DComplex(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], dataArr);

        return array;

    }

    static public Vect6DComplex readData(int ctx, String fieldPath, String timeBasePath, Vect6DComplex array)throws ALException
    {

        int retSize[] = new int[6];
        Complex dataArr[] = null;

        dataArr = LowLevel.al_read_data_complex(ctx, fieldPath, timeBasePath, 6, retSize);

        array = new Vect6DComplex(retSize[0], retSize[1], retSize[2], retSize[3], retSize[4], retSize[5], dataArr);

        return array;

    }

    /************************************************************************************************************************************************/
    /************************************************************************************************************************************************/
    /************************************************************************************************************************************************/
	static public int readData(int ctx, String fieldPath, String timeBasePath, int  value) throws ALException
        {

        int retSize[]  = new int[1];
		int retVal = -1;
		int dataArr[] = null;

  		dataArr = LowLevel.al_read_data_int(ctx, fieldPath, timeBasePath, 0, retSize);

		
		retVal = dataArr[0];
        return retVal;
	}


	static public Vect1DInt readData(int ctx, String fieldPath, String timeBasePath, Vect1DInt array) throws ALException
        {

        int retSize[]  = new int[1];
		int dataArr[] = null;



  		dataArr = LowLevel.al_read_data_int(ctx, fieldPath, timeBasePath, 1, retSize);

		array = new Vect1DInt(dataArr);
        return array;

	}

    static public Vect2DInt readData(int ctx, String fieldPath, String timeBasePath, Vect2DInt array) throws ALException
        {

        int retSize[]  = new int[2];
        int dataArr[] = null;

        dataArr = LowLevel.al_read_data_int(ctx, fieldPath, timeBasePath, 2, retSize);


        array = new Vect2DInt(retSize[0], retSize[1], dataArr);

        return array;
    }

    static public Vect3DInt readData(int ctx, String fieldPath, String timeBasePath, Vect3DInt array) throws ALException
        {

        int retSize[]  = new int[3];
        int dataArr[] = null;

        dataArr = LowLevel.al_read_data_int(ctx, fieldPath, timeBasePath, 3, retSize);


        array = new Vect3DInt(retSize[0], retSize[1], retSize[2], dataArr);
        return array;

    }

	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
	/************************************************************************************************************************************************/
    static public String readData(int ctx, String fieldPath, String timeBasePath, String text) throws ALException
    {
        

		int retSize[] = new int[1];	
		byte dataArr[] = null;
        String str = null;

		
		dataArr = LowLevel.al_read_data_char(ctx, fieldPath, timeBasePath, 1, retSize);

		
		text = new String(dataArr);
        return text;

    }

	static public Vect1DString readData(int ctx, String fieldPath, String timeBasePath, Vect1DString array) throws ALException
    {
        int retSize[] = new int[2]; 
        byte dataArr[] = null;
        String strArr[] = null;

		int numberOfStrings = -1;
		int maxStringSize = -1;

  		dataArr = LowLevel.al_read_data_char(ctx, fieldPath, timeBasePath, 2, retSize);
 

		numberOfStrings = retSize[0];
		maxStringSize = retSize[1];

		strArr = new String[numberOfStrings];

		for(int i=0; i < numberOfStrings; i++)
		{
            byte buffer[] = Arrays.copyOfRange(dataArr, i * maxStringSize, (i + 1) * maxStringSize);
			String str = new String(buffer);
			strArr[i] = str; 	
		}

  	
        array = new Vect1DString(strArr);
        return array;
	}


}

