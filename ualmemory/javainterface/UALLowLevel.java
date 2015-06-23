package ualmemory.javainterface;
public class UALLowLevel {
    
    static {
        try {
            System.loadLibrary("imas");
        }catch(Throwable exc)
        {
            System.err.println("Cannot link to JNI library: " + exc);
            System.exit(0);
        }
    }

	/* returns a vector containing all the dimensions of an array) */
    public static native Vect1DInt getDimension(int expIdx, String path, String subPath) throws UALException;

    /* OH not defined here, use directly lowlevel ual_jni
    public static native void enableMemCache(int expIdx);
    public static native void disableMemCache(int expIdx);
    public static native void discardAll(int expIdx);
    public static native void flushAll(int expIdx);
    public static native void discard(int expIdx, String idsPath);
    public static native void flush(int expIdx, String idsPath);
    */
    
    public static void beginIDSPutTimed(int expIdx, String path, Vect1DDouble times) throws UALException
    {  
        /* beginIDSPutTimed(expIdx, path, times.getDim(), times.getArray());*/
        beginIDSPutTimed(expIdx, path, (times == null)?0:times.getDim(), (times == null)?null:times.getArray() );
    }      
    public static native void beginIDSPutTimed(int expIdx, String path, int len, double []times) throws UALException;
    public static native void endIDSPutTimed(int expIdx, String path) throws UALException;
    public static native void beginIDSPut(int expIdx, String path) throws UALException;
    public static native void endIDSPut(int expIdx, String path) throws UALException;
    public static native void beginIDSPutNonTimed(int expIdx, String path) throws UALException;
    public static native void endIDSPutNonTimed(int expIdx, String path) throws UALException;
    public static native void beginIDSPutSlice(int expIdx, String path) throws UALException;
    public static native void endIDSPutSlice(int expIdx, String path) throws UALException;
    public static native void beginIDSReplaceLastSlice(int expIdx, String path) throws UALException;
    public static native void endIDSReplaceLastSlice(int expIdx, String path) throws UALException;
    
    public static native int beginIDSGet(int expIdx, String path, boolean timed) throws UALException;
    public static native void endIDSGet(int expIdx, String path) throws UALException;
    public static native double beginIDSGetSlice(int expIdx, String path, double time) throws UALException;
    public static native void endIDSGetSlice(int expIdx, String path) throws UALException;
    
    public static native void putString(int expIdx, String path, String subPath, String data) throws UALException;
    public static native void putInt(int expIdx, String path, String subPath, int data) throws UALException;
    public static native void putBoolean(int expIdx, String path, String subPath, boolean data) throws UALException;
    public static native void putFloat(int expIdx, String path, String subPath, float data) throws UALException;
    public static native void putDouble(int expIdx, String path, String subPath, double data) throws UALException;
    public static void putVect1DInt(int expIdx, String path, String subPath, String timeBasePath, Vect1DInt arr, boolean isTimed) throws UALException
    {
        putVect1DInt(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), isTimed);
    }
    public static native void putVect1DInt(int expIdx, String path, String subPath, String timeBasePath, int[] arr, int dim, boolean isTimed) throws UALException;
    public static void putVect1DBoolean(int expIdx, String path, String subPath, String timeBasePath, Vect1DBoolean arr, int dim, boolean isTimed) throws UALException
    {
        putVect1DBoolean(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), isTimed);
    }
    public static native void putVect1DBoolean(int expIdx, String path, String subPath, String timeBasePath, boolean[] arr, int dim, boolean isTimed) throws UALException;
    public static void putVect1DString(int expIdx, String path, String subPath, String timeBasePath, Vect1DString arr, boolean isTimed) throws UALException
    {
        putVect1DString(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), isTimed);
    }
    public static native void putVect1DString(int expIdx, String path, String subPath, String timeBasePath, String[] arr, int dim, boolean isTimed) throws UALException;
    public static void putVect1DFloat(int expIdx, String path, String subPath, String timeBasePath, Vect1DFloat arr, boolean isTimed) throws UALException
    {
        putVect1DFloat(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), isTimed);
    }
    public static native void putVect1DFloat(int expIdx, String path, String subPath, String timeBasePath, float[] arr, int dim, boolean isTimed) throws UALException;
    public static void putVect1DDouble(int expIdx, String path, String subPath, String timeBasePath, Vect1DDouble arr, boolean isTimed) throws UALException
    {
        putVect1DDouble(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), isTimed);
    }
    public static native void putVect1DDouble(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim, boolean isTimed) throws UALException;
    public static void putVect2DInt(int expIdx, String path, String subPath, String timeBasePath, Vect2DInt arr, boolean isTimed) throws UALException
    {
        putVect2DInt(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), isTimed);
    }
    public static native void putVect2DInt(int expIdx, String path, String subPath, String timeBasePath, int[] arr, int dim1, int dim2, boolean isTimed) throws UALException;
    public static void putVect2DFloat(int expIdx, String path, String subPath, String timeBasePath, Vect2DFloat arr, boolean isTimed) throws UALException
    {
        putVect2DFloat(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), isTimed);
    }
    public static native void putVect2DFloat(int expIdx, String path, String subPath, String timeBasePath, float[] arr, int dim1, int dim2, boolean isTimed) throws UALException;
    public static void putVect2DDouble(int expIdx, String path, String subPath, String timeBasePath, Vect2DDouble arr, boolean isTimed) throws UALException
    {
        putVect2DDouble(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), isTimed);
    }
    public static native void putVect2DDouble(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, boolean isTimed) throws UALException;
    public static void putVect3DInt(int expIdx, String path, String subPath, String timeBasePath, Vect3DInt arr, boolean isTimed) throws UALException
    {
        putVect3DInt(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), isTimed);
    }
    public static native void putVect3DInt(int expIdx, String path, String subPath, String timeBasePath, int[] arr, int dim1, int dim2, int dim3, boolean isTimed) throws UALException;
    public static void putVect3DFloat(int expIdx, String path, String subPath, String timeBasePath, Vect3DFloat arr, boolean isTimed) throws UALException
    {
        putVect3DFloat(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), isTimed);
    }
    public static native void putVect3DFloat(int expIdx, String path, String subPath, String timeBasePath, float[] arr, int dim1, int dim2, int dim3, boolean isTimed) throws UALException;
    public static void putVect3DDouble(int expIdx, String path, String subPath, String timeBasePath, Vect3DDouble arr, boolean isTimed) throws UALException
    {
        putVect3DDouble(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), isTimed);
    }
    public static native void putVect3DDouble(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, boolean isTimed) throws UALException;
    public static void putVect4DDouble(int expIdx, String path, String subPath, String timeBasePath, Vect4DDouble arr, boolean isTimed) throws UALException
    {
        putVect4DDouble(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr ==
	null)?0:arr.getDim(3), isTimed);
    }
    public static native void putVect4DDouble(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, int dim4, boolean isTimed) throws UALException;

    public static void putVect5DDouble(int expIdx, String path, String subPath, String timeBasePath, Vect5DDouble arr, boolean isTimed) throws UALException
    {
        putVect5DDouble(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr ==
	null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4), isTimed);
    }
    public static native void putVect5DDouble(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, int dim4, int dim5, boolean isTimed) throws UALException;

    public static void putVect6DDouble(int expIdx, String path, String subPath, String timeBasePath, Vect6DDouble arr, boolean isTimed) throws UALException
    {
        putVect6DDouble(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr ==
	null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4),(arr == null)?0:arr.getDim(5), isTimed);
    }
    public static native void putVect6DDouble(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, boolean isTimed) throws UALException;
    public static void putVect7DDouble(int expIdx, String path, String subPath, String timeBasePath, Vect7DDouble arr, boolean isTimed) throws UALException
    {
        putVect7DDouble(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr ==
	null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4),(arr == null)?0:arr.getDim(5), (arr == null)?0:arr.getDim(6), isTimed);
    }
    public static native void putVect7DDouble(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, int dim4, 
    	int dim5, int dim6, int dim7, boolean isTimed) throws UALException;

    

    public static native void replaceLastStringSlice(int expIdx, String path, String subPath, String data) throws UALException;
    public static native void replaceLastIntSlice(int expIdx, String path, String subPath, int data) throws UALException;
    public static native void replaceLastBooleanSlice(int expIdx, String path, String subPath, boolean data) throws UALException;
    public static native void replaceLastFloatSlice(int expIdx, String path, String subPath, float data) throws UALException;
    public static native void replaceLastDoubleSlice(int expIdx, String path, String subPath, double data) throws UALException;
    public static void replaceLastVect1DIntSlice(int expIdx, String path, String subPath, Vect1DInt arr) throws UALException
    {
        replaceLastVect1DIntSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void replaceLastVect1DIntSlice(int expIdx, String path, String subPath, int[] arr, int dim) throws UALException;
    public static void replaceLastVect1DBooleanSlice(int expIdx, String path, String subPath, Vect1DBoolean arr, int dim) throws UALException
    {
        replaceLastVect1DBooleanSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void replaceLastVect1DBooleanSlice(int expIdx, String path, String subPath, boolean[] arr, int dim) throws UALException;
    public static void replaceLastVect1DFloatSlice(int expIdx, String path, String subPath, Vect1DFloat arr) throws UALException
    {
        replaceLastVect1DFloatSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void replaceLastVect1DFloatSlice(int expIdx, String path, String subPath, float[] arr, int dim) throws UALException;
    public static void replaceLastVect1DDoubleSlice(int expIdx, String path, String subPath, Vect1DDouble arr) throws UALException
    {
        replaceLastVect1DDoubleSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void replaceLastVect1DDoubleSlice(int expIdx, String path, String subPath, double[] arr, int dim) throws UALException;
    public static void replaceLastVect2DIntSlice(int expIdx, String path, String subPath, Vect2DInt arr) throws UALException
    {
        replaceLastVect2DIntSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1));
    }
    public static native void replaceLastVect2DIntSlice(int expIdx, String path, String subPath, int[] arr, int dim1, int dim2) throws UALException;
    public static void replaceLastVect2DFloatSlice(int expIdx, String path, String subPath, Vect2DFloat arr) throws UALException
    {
        replaceLastVect2DFloatSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1));
    }
    public static native void replaceLastVect2DFloatSlice(int expIdx, String path, String subPath, float[] arr, int dim1, int dim2) throws UALException;
    public static void replaceLastVect2DDoubleSlice(int expIdx, String path, String subPath, Vect2DDouble arr) throws UALException
    {
        replaceLastVect2DDoubleSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1));
    }
    public static native void replaceLastVect2DDoubleSlice(int expIdx, String path, String subPath, double[] arr, int dim1, int dim2) throws UALException;
    public static void replaceLastVect3DIntSlice(int expIdx, String path, String subPath, Vect3DInt arr) throws UALException
    {
        replaceLastVect3DIntSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2));
    }
    public static native void replaceLastVect3DIntSlice(int expIdx, String path, String subPath, int[] arr, int dim1, int dim2, int dim3) throws UALException;
    public static void replaceLastVect3DFloatSlice(int expIdx, String path, String subPath, Vect3DFloat arr) throws UALException
    {
        replaceLastVect3DFloatSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2));
    }
    public static native void replaceLastVect3DFloatSlice(int expIdx, String path, String subPath, float[] arr, int dim1, int dim2, int dim3) throws UALException;
    public static void replaceLastVect3DDoubleSlice(int expIdx, String path, String subPath, Vect3DDouble arr) throws UALException
    {
        replaceLastVect3DDoubleSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2));
    }
    public static native void replaceLastVect3DDoubleSlice(int expIdx, String path, String subPath, double[] arr, int dim1, int dim2, int dim3) throws UALException;
    
    public static void replaceLastVect4DDoubleSlice(int expIdx, String path, String subPath, Vect4DDouble arr) throws UALException
    {
        replaceLastVect4DDoubleSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr ==
	null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3));
    }
    public static native void replaceLastVect4DDoubleSlice(int expIdx, String path, String subPath, double[] arr, int dim1, int dim2, int dim3, int dim4) throws UALException;
    
    public static void replaceLastVect5DDoubleSlice(int expIdx, String path, String subPath, Vect5DDouble arr) throws UALException
    {
        replaceLastVect5DDoubleSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr ==
	null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4));
    }
    public static native void replaceLastVect5DDoubleSlice(int expIdx, String path, String subPath, double[] arr, int dim1, int dim2, int dim3, int dim4, int dim5) throws UALException;
    
    public static void replaceLastVect6DDoubleSlice(int expIdx, String path, String subPath, Vect6DDouble arr) throws UALException
    {
        replaceLastVect6DDoubleSlice(expIdx, path, subPath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr ==
	null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4), (arr == null)?0:arr.getDim(5));
    }
    public static native void replaceLastVect6DDoubleSlice(int expIdx, String path, String subPath, double[] arr, int dim1, int dim2, int dim3, 
    	int dim4, int dim5, int dim6) throws UALException;
    

    public static native void putStringSlice(int expIdx, String path, String subPath, String timeBasePath, String data, double time) throws UALException;
    public static native void putIntSlice(int expIdx, String path, String subPath, String timeBasePath, int data, double time) throws UALException;
    public static native void putBooleanSlice(int expIdx, String path, String subPath, String timeBasePath, boolean data, double time) throws UALException;
    public static native void putFloatSlice(int expIdx, String path, String subPath, String timeBasePath, float data, double time) throws UALException;
    public static native void putDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double data, double time) throws UALException;
    public static void putVect1DIntSlice(int expIdx, String path, String subPath, String timeBasePath, Vect1DInt arr, double time) throws UALException
    {
        if (arr != null && arr.getDim() > 0) {
//        putVect1DIntSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(),time);
          putIntSlice(expIdx, path, subPath, timeBasePath, arr.getElementAt(0),time);
        }
    }
    public static native void putVect1DIntSlice(int expIdx, String path, String subPath, String timeBasePath, int[] arr, int dim, double time) throws UALException;
    public static void putVect1DStringSlice(int expIdx, String path, String subPath, String timeBasePath, Vect1DString arr, double time) throws UALException
    {
        if (arr != null && arr.getDim() > 0) {
          putStringSlice(expIdx, path, subPath, timeBasePath, arr.getElementAt(0), time);
        }
    }
    public static void putVect1DBooleanSlice(int expIdx, String path, String subPath, String timeBasePath, Vect1DBoolean arr, int dim, double time) throws UALException
    {
        if (arr != null && arr.getDim() > 0) {
//        putVect1DBooleanSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), time);
          putBooleanSlice(expIdx, path, subPath, timeBasePath, arr.getElementAt(0), time);
        }
    }
    public static native void putVect1DBooleanSlice(int expIdx, String path, String subPath, String timeBasePath, boolean[] arr, int dim, double time) throws UALException;
    public static void putVect1DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, Vect1DFloat arr, double time) throws UALException
    {
        if (arr != null && arr.getDim() > 0) {
//        putVect1DFloatSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), time);
          putFloatSlice(expIdx, path, subPath, timeBasePath, arr.getElementAt(0), time);
        }
    }
    public static native void putVect1DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, float[] arr, int dim, double time) throws UALException;
    public static void putVect1DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, Vect1DDouble arr, double time) throws UALException
    {
        if (arr != null && arr.getDim() > 0) {
//        putVect1DDoubleSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(), time);
          putDoubleSlice(expIdx, path, subPath, timeBasePath, arr.getElementAt(0), time);
        }
    }
    public static native void putVect1DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim, double time) throws UALException;
    public static void putVect2DIntSlice(int expIdx, String path, String subPath, String timeBasePath, Vect2DInt arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect2DIntSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), time);
          putVect1DIntSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), time);
        }
    }
    public static native void putVect2DIntSlice(int expIdx, String path, String subPath, String timeBasePath, int[] arr, int dim1, int dim2, double time) throws UALException;
    public static void putVect2DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, Vect2DFloat arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect2DFloatSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), time);
          putVect1DFloatSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), time);
        }
    }
    public static native void putVect2DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, float[] arr, int dim1, int dim2, double time) throws UALException;
    public static void putVect2DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, Vect2DDouble arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect2DDoubleSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), time);
          putVect1DDoubleSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), time);
        }
    }
    public static native void putVect2DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, double time) throws UALException;
    public static void putVect3DIntSlice(int expIdx, String path, String subPath, String timeBasePath, Vect3DInt arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect3DIntSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), time);
          putVect2DIntSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), arr.getDim(1), time);
        }
    }
    public static native void putVect3DIntSlice(int expIdx, String path, String subPath, String timeBasePath, int[] arr, int dim1, int dim2, int dim3, double time) throws UALException;
    public static void putVect3DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, Vect3DFloat arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect3DFloatSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), time);
          putVect2DFloatSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), arr.getDim(1), time);
        }
    }
    public static native void putVect3DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, float[] arr, int dim1, int dim2, int dim3, double time) throws UALException;
    public static void putVect3DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, Vect3DDouble arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect3DDoubleSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), time);
          putVect2DDoubleSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), arr.getDim(1), time);
        }
    }
    public static native void putVect3DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, double time) throws UALException;
    
    
    public static void putVect4DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, Vect4DDouble arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect4DDoubleSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3), time);
          putVect3DDoubleSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), arr.getDim(1), arr.getDim(2), time);
        }
    }
    public static native void putVect4DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, int dim4, double time) throws UALException;
    
    public static void putVect5DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, Vect5DDouble arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect5DDoubleSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr
          putVect4DDoubleSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), arr.getDim(1), arr.getDim(2), arr.getDim(3), time);
        }
    }
    public static native void putVect5DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, int dim4, int dim5, double time) throws UALException;
    
    public static void putVect6DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, Vect6DDouble arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect6DDoubleSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr
//	== null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4), (arr == null)?0:arr.getDim(5), time);
          putVect5DDoubleSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), arr.getDim(1), arr.getDim(2), arr.getDim(3), arr.getDim(4), time);
        }
    }
    public static native void putVect6DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, 
    	int dim4, int dim5, int dim6, double time) throws UALException;
    
    public static void putVect7DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, Vect7DDouble arr, double time) throws UALException
    {
        if (arr != null && arr.getDim(0) > 0) {
//        putVect7DDoubleSlice(expIdx, path, subPath, timeBasePath, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr
//	== null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4), (arr == null)?0:arr.getDim(5),  (arr == null)?0:arr.getDim(6), time);
          putVect6DDoubleSlice(expIdx, path, subPath, timeBasePath, arr.getArray(), arr.getDim(0), arr.getDim(1), arr.getDim(2), arr.getDim(3), arr.getDim(4), arr.getDim(5),  time);
        }
    }
    public static native void putVect7DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double[] arr, int dim1, int dim2, int dim3, 
    	int dim4, int dim5, int dim6, int dim7, double time) throws UALException;
    
   
    
    public static native void deleteData(int expIdx, String path, String subPath); 
    
    
    public static native String getString(int expIdx, String path, String subPath) throws UALException;
    public static native int  getInt(int expIdx, String path, String subPath) throws UALException;
    public static native boolean  getBoolean(int expIdx, String path, String subPath) throws UALException;
    public static native float getFloat(int expIdx, String path, String subPath) throws UALException;
    public static native double getDouble(int expIdx, String path, String subPath) throws UALException;
    public static native Vect1DInt getVect1DInt(int expIdx, String path, String subPath) throws UALException;
    public static native Vect1DString getVect1DString(int expIdx, String path, String subPath) throws UALException;
    public static native Vect1DBoolean getVect1DBoolean(int expIdx, String path, String subPath) throws UALException;
    public static native Vect1DFloat getVect1DFloat(int expIdx, String path, String subPath) throws UALException;
    public static native Vect1DDouble getVect1DDouble(int expIdx, String path, String subPath) throws UALException;
    public static native Vect2DInt getVect2DInt(int expIdx, String path, String subPath) throws UALException;
    public static native Vect2DFloat getVect2DFloat(int expIdx, String path, String subPath) throws UALException;
    public static native Vect2DDouble getVect2DDouble(int expIdx, String path, String subPath) throws UALException;
    public static native Vect3DInt getVect3DInt(int expIdx, String path, String subPath) throws UALException;
    public static native Vect3DFloat getVect3DFloat(int expIdx, String path, String subPath) throws UALException;
    public static native Vect3DDouble getVect3DDouble(int expIdx, String path, String subPath) throws UALException;
    public static native Vect4DDouble getVect4DDouble(int expIdx, String path, String subPath) throws UALException;
    public static native Vect5DDouble getVect5DDouble(int expIdx, String path, String subPath) throws UALException;
    public static native Vect6DDouble getVect6DDouble(int expIdx, String path, String subPath) throws UALException;
    public static native Vect7DDouble getVect7DDouble(int expIdx, String path, String subPath) throws UALException;

    public static native String getStringSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native int  getIntSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native boolean  getBooleanSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native float getFloatSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native double getDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect1DInt getVect1DIntSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect1DFloat getVect1DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect1DDouble getVect1DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect2DInt getVect2DIntSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect2DFloat getVect2DFloatSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect2DDouble getVect2DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect3DDouble getVect3DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect4DDouble getVect4DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect5DDouble getVect5DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;
    public static native Vect6DDouble getVect6DDoubleSlice(int expIdx, String path, String subPath, String timeBasePath, double time, int interpolMode) throws UALException;


    public static Vect1DInt getVect1DInt(int []array) {return new Vect1DInt(array);}
    public static Vect1DFloat getVect1DFloat(float []array) {return new Vect1DFloat(array);}
    public static Vect1DDouble getVect1DDouble(double []array) {return new Vect1DDouble(array);}
    public static Vect1DBoolean getVect1DBoolean(boolean []array) {return new Vect1DBoolean(array);}
    public static Vect1DString getVect1DString(String []array) {return new Vect1DString(array);}
    public static Vect2DInt getVect2DInt(int dim1, int dim2, int []array) {return new Vect2DInt(dim1, dim2, array);}
    public static Vect2DFloat getVect2DFloat(int dim1, int dim2, float []array) {return new Vect2DFloat(dim1, dim2, array);}
    public static Vect2DDouble getVect2DDouble(int dim1, int dim2, double []array) {return new Vect2DDouble(dim1, dim2, array);}
    public static Vect3DInt getVect3DInt(int dim1, int dim2, int dim3, int []array) {return new Vect3DInt(dim1, dim2, dim3, array);}
    public static Vect3DFloat getVect3DFloat(int dim1, int dim2, int dim3, float []array) {return new Vect3DFloat(dim1, dim2, dim3, array);}
    public static Vect3DDouble getVect3DDouble(int dim1, int dim2, int dim3, double []array) {return new Vect3DDouble(dim1, dim2, dim3, array);}
    public static Vect4DDouble getVect4DDouble(int dim1, int dim2, int dim3, int dim4, double []array) {return new Vect4DDouble(dim1, dim2, dim3, dim4, array);}
    public static Vect5DDouble getVect5DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, double []array) {return new Vect5DDouble(dim1, dim2, dim3, dim4, dim5, array);}
    public static Vect6DDouble getVect6DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, double []array) {return new Vect6DDouble(dim1, dim2, dim3, dim4, dim5, dim6, array);}
    public static Vect7DDouble getVect7DDouble(int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, int dim7, double []array) 
    	{return new Vect7DDouble(dim1, dim2, dim3, dim4, dim5, dim6, dim7, array);}

    public static native int getUniqueRun(int shot);
    
    
    //Catalogue DB access methods
    public static native void putIdsDb(String user, String machine, int shot, int run, String path, int occurrence, int isRef,
	    	String refUser, String refMachine, int refShot, int refRun, int refOccurrence) throws UALException;

    public static native int openDb(String user, String machine, int shot, int run, String idsName, int occurrence) throws UALException;
    public static native int createNewRunDb(String user, String machine, int shot, String dataVersion) throws UALException;
    public static native int createNewRunParentDb(String user, String machine, int shot, String dataV, String parentUser, 
      String parentMachine, int parenShot, int parentRun) throws UALException;
    public static native void createSpecifiedRunDb(String user, String machine, int shot, int run, String dataVersion) throws UALException;
    public static native void createSpecifiedRunParentDb(String user, String machine, int shot, int run, String dataV,
      String parentUser, String parentMachine, int parenShot, int parentRun) throws UALException;

    //Helper functions
    public static native int getShot(int idx);
    public static native int getRun(int idx);

    // Arrays of structures
    public static native int beginObject(int expIdx, int obj, int idx, String fullPath, int isTimed);   // returns a handle on the new object
    public static native void releaseObject(int expIdx, int obj);
    public static native void putObject(int expIdx, String path, String subPath, int obj, int isTimed);

    public static native void putStringInObject(int expIdx, int obj, String subPath, int idx, String data) throws UALException;
    public static native void putIntInObject(int expIdx, int obj, String subPath, int idx, int data) throws UALException;
    public static native void putFloatInObject(int expIdx, int obj, String subPath, int idx, float data) throws UALException;
    public static native void putDoubleInObject(int expIdx, int obj, String subPath, int idx, double data) throws UALException;
    public static void putVect1DIntInObject(int expIdx, int obj, String subPath, int idx, Vect1DInt arr) throws UALException
    {
        putVect1DIntInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void putVect1DIntInObject(int expIdx, int obj, String subPath, int idx, int[] arr, int dim) throws UALException;
    public static void putVect1DStringInObject(int expIdx, int obj, String subPath, int idx, Vect1DString arr) throws UALException
    {
        putVect1DStringInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void putVect1DStringInObject(int expIdx, int obj, String subPath, int idx, String[] arr, int dim) throws UALException;
    public static void putVect1DFloatInObject(int expIdx, int obj, String subPath, int idx, Vect1DFloat arr) throws UALException
    {
        putVect1DFloatInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void putVect1DFloatInObject(int expIdx, int obj, String subPath, int idx, float[] arr, int dim) throws UALException;
    public static void putVect1DDoubleInObject(int expIdx, int obj, String subPath, int idx, Vect1DDouble arr) throws UALException
    {
        putVect1DDoubleInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim());
    }
    public static native void putVect1DDoubleInObject(int expIdx, int obj, String subPath, int idx, double[] arr, int dim) throws UALException;
    public static void putVect2DIntInObject(int expIdx, int obj, String subPath, int idx, Vect2DInt arr) throws UALException
    {
        putVect2DIntInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1));
    }
    public static native void putVect2DIntInObject(int expIdx, int obj, String subPath, int idx, int[] arr, int dim1, int dim2) throws UALException;
    public static void putVect2DFloatInObject(int expIdx, int obj, String subPath, int idx, Vect2DFloat arr) throws UALException
    {
        putVect2DFloatInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1));
    }
    public static native void putVect2DFloatInObject(int expIdx, int obj, String subPath, int idx, float[] arr, int dim1, int dim2) throws UALException;
    public static void putVect2DDoubleInObject(int expIdx, int obj, String subPath, int idx, Vect2DDouble arr) throws UALException
    {
        putVect2DDoubleInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1));
    }
    public static native void putVect2DDoubleInObject(int expIdx, int obj, String subPath, int idx, double[] arr, int dim1, int dim2) throws UALException;
    public static void putVect3DIntInObject(int expIdx, int obj, String subPath, int idx, Vect3DInt arr) throws UALException
    {
        putVect3DIntInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2));
    }
    public static native void putVect3DIntInObject(int expIdx, int obj, String subPath, int idx, int[] arr, int dim1, int dim2, int dim3) throws UALException;
    public static void putVect3DFloatInObject(int expIdx, int obj, String subPath, int idx, Vect3DFloat arr) throws UALException
    {
        putVect3DFloatInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2));
    }
    public static native void putVect3DFloatInObject(int expIdx, int obj, String subPath, int idx, float[] arr, int dim1, int dim2, int dim3) throws UALException;
    public static void putVect3DDoubleInObject(int expIdx, int obj, String subPath, int idx, Vect3DDouble arr) throws UALException
    {
        putVect3DDoubleInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2));
    }
    public static native void putVect3DDoubleInObject(int expIdx, int obj, String subPath, int idx, double[] arr, int dim1, int dim2, int dim3) throws UALException;
    public static void putVect4DDoubleInObject(int expIdx, int obj, String subPath, int idx, Vect4DDouble arr) throws UALException
    {
        putVect4DDoubleInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3));
    }
    public static native void putVect4DDoubleInObject(int expIdx, int obj, String subPath, int idx, double[] arr, int dim1, int dim2, int dim3, int dim4) throws UALException;

    public static void putVect5DDoubleInObject(int expIdx, int obj, String subPath, int idx, Vect5DDouble arr) throws UALException
    {
        putVect5DDoubleInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4));
    }
    public static native void putVect5DDoubleInObject(int expIdx, int obj, String subPath, int idx, double[] arr, int dim1, int dim2, int dim3, int dim4, int dim5) throws UALException;

    public static void putVect6DDoubleInObject(int expIdx, int obj, String subPath, int idx, Vect6DDouble arr) throws UALException
    {
        putVect6DDoubleInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4),(arr == null)?0:arr.getDim(5));
    }
    public static native void putVect6DDoubleInObject(int expIdx, int obj, String subPath, int idx, double[] arr, int dim1, int dim2, int dim3, int dim4, int dim5, int dim6) throws UALException;
    public static void putVect7DDoubleInObject(int expIdx, int obj, String subPath, int idx, Vect7DDouble arr) throws UALException
    {
        putVect7DDoubleInObject(expIdx, obj, subPath, idx, (arr == null)?null:arr.getArray(), (arr == null)?0:arr.getDim(0), (arr == null)?0:arr.getDim(1), (arr == null)?0:arr.getDim(2), (arr == null)?0:arr.getDim(3), (arr == null)?0:arr.getDim(4),(arr == null)?0:arr.getDim(5), (arr == null)?0:arr.getDim(6));
    }
    public static native void putVect7DDoubleInObject(int expIdx, int obj, String subPath, int idx, double[] arr, int dim1, int dim2, int dim3, int dim4, int dim5, int dim6, int dim7) throws UALException;
    public static native void putObjectInObject(int expIdx, int obj, String subPath, int idx, int dataObj);

    public static native int getObjectDim(int expIdx, int obj);
    public static native int getObject(int expIdx, String path, String subPath, int isTimed);
    public static native String getStringFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native int  getIntFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native float getFloatFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native double getDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect1DInt getVect1DIntFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect1DString getVect1DStringFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect1DFloat getVect1DFloatFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect1DDouble getVect1DDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect2DInt getVect2DIntFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect2DFloat getVect2DFloatFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect2DDouble getVect2DDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect3DInt getVect3DIntFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect3DFloat getVect3DFloatFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect3DDouble getVect3DDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect4DDouble getVect4DDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect5DDouble getVect5DDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect6DDouble getVect6DDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;
    public static native Vect7DDouble getVect7DDoubleFromObject(int expIdx, int obj, String subPath, int idx) throws UALException;

    public static native int getObjectFromObject(int expIdx, int obj, String subPath, int idx);
    
    public static native int getObjectSlice(int expIdx, String path, String subPath, double time);
    public static native void putObjectSlice(int expIdx, String path, String subPath, double time, int obj);
    public static native void replaceLastObjectSlice(int expIdx, String path, String subPath, int obj);

    //IDS copies

    public static native void ualCopyIds(int fromIdx, int toIdx, String idsName, int fromIdsOccur, int toIdsOccur);
    public static native void ualCopyIdsEnv(String tokamakFrom, String versionFrom, String userFrom, int shotFrom, int runFrom, int occurrenceFrom, String tokamakTo, String versionTo, String userTo, int shotTo, int runTo, int occurrenceTo, String idsName);
}
