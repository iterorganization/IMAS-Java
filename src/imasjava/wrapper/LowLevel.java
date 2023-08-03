package imasjava.wrapper;

import imasjava.ALException;
import imasjava.Complex;

public class LowLevel {

      public static final int EMPTY_INT = -999999999;
      public static final double EMPTY_DOUBLE = -9.0E40;
      public static final Complex EMPTY_COMPLEX = new Complex(EMPTY_DOUBLE, EMPTY_DOUBLE);

      public static final String LIFECYCLE_STATUS_OBSOLETE = "obsolescent";

      public final static int MAXDIM = 7;

      public final static int OP_INTERP_0 = 0;
      public final static int BACKEND_ID_0 = 10;
      public final static int OP_RANGE_0 = 20;
      public final static int OP_ACCESS_0 = 30;
      public final static int ACCESS_PULSE_0 = 40;
      public final static int DATA_TYPE_0 = 50;
      public final static int SERIALIZER_PROTOCOL_0 = 60;
      public final static int ERR_0 = -1;

      public final static int TIMED = 1;
      public final static int NON_TIMED = 0;

      public final static int ASCII_SERIALIZER_PROTOCOL = SERIALIZER_PROTOCOL_0;
      public final static int DEFAULT_SERIALIZER_PROTOCOL = ASCII_SERIALIZER_PROTOCOL;

      public final static int GLOBAL_OP = OP_RANGE_0;
      public final static int SLICE_OP = OP_RANGE_0 + 1;

      public final static int READ_OP = OP_ACCESS_0;
      public final static int WRITE_OP = OP_ACCESS_0 + 1;
      public final static int REPLACE_OP = OP_ACCESS_0 + 2;

      public final static int UNDEFINED_INTERP = OP_INTERP_0;
      public final static int CLOSEST_INTERP = OP_INTERP_0 + 1;
      public final static int PREVIOUS_INTERP = OP_INTERP_0 + 2;
      public final static int LINEAR_INTERP = OP_INTERP_0 + 3;

      public final static int UNDEFINED_TIME = -999;

      public final static int OPEN_PULSE = ACCESS_PULSE_0;
      public final static int FORCE_OPEN_PULSE = ACCESS_PULSE_0 + 1;
      public final static int CREATE_PULSE = ACCESS_PULSE_0 + 2;
      public final static int FORCE_CREATE_PULSE = ACCESS_PULSE_0 + 3;
      public final static int CLOSE_PULSE = ACCESS_PULSE_0 + 4;
      public final static int ERASE_PULSE = ACCESS_PULSE_0 + 5;
      /*
       * public final static int CHAR_DATA = DATA_TYPE_0; public final static int
       * INTEGER_DATA = DATA_TYPE_0 + 1; public final static int DOUBLE_DATA =
       * DATA_TYPE_0 + 2; public final static int COMPLEX_DATA = DATA_TYPE_0 + 3;
       */
      public final static int UNKNOWN_ERR = ERR_0;
      public final static int CONTEXT_ERR = ERR_0 - 1;
      public final static int BACKEND_ERR = ERR_0 - 2;
      public final static int LOWLEVEL_ERR = ERR_0 - 3;

      public final static int NO_BACKEND = BACKEND_ID_0;
      public final static int ASCII_BACKEND = BACKEND_ID_0 + 1;
      public final static int MDSPLUS_BACKEND = BACKEND_ID_0 + 2;
      public final static int HDF5_BACKEND = BACKEND_ID_0 + 3;
      public final static int MEMORY_BACKEND = BACKEND_ID_0 + 4;
      public final static int UDA_BACKEND = BACKEND_ID_0 + 5;

      public final static int IDS_TIME_MODE_UNKNOWN = EMPTY_INT;
      public final static int IDS_TIME_MODE_HETEROGENEOUS = 0;
      public final static int IDS_TIME_MODE_HOMOGENEOUS = 1;
      public final static int IDS_TIME_MODE_INDEPENDENT = 2;

      /* returns a vector containing all the dimensions of an array) */

 /******************** DEFINITION OF THE C API ********************/

  /**
     Print all the Context information corresponding to the passed Context identifier.
     @param[in] ctx Context ID (either DataEntryContext, OperationContext or ArraystructContext)
     @result error status
     int al_print_context(int ctx) throws ALException;
   */
      public static native void al_print_context(int ctx) throws ALException;
  /**
     Builds an URI from legacy parameters.
     @param[in] backendID name/ID of the back-end
     @param[in] shot shot number
     @param[in] run run number
     @param[in] user username [_optional, "" for default_]
     @param[in] tokamak tokamak name
     @param[in] version data version
     @param[in] options options
     @return uri

  int al_build_uri_from_legacy_parameters(final int backendID, 
			     const int shot, 
			     const int run, 
			     const char *user, 
			     const char *tokamak, 
			     const char *version,
                 const char *options,
                 char* uri) throws ALException;
  */
  public static native String al_build_uri_from_legacy_parameters(final int backendID, 
			     final int shot, 
			     final int run, 
			     final String user, 
			     final String tokamak, 
			     final String version,
                 final String options) throws ALException;

  /**
     Starts an action on a Data Entry in the database.
     @param[in] uri
     @param[in] mode opening option:
     - OPEN_PULSE = open an existing pulse (only if exist)
     - FORCE_OPEN_PULSE = open a pulse (create it if not exist)
     - CREATE_PULSE = create a new pulse (do not overwrite if already exist)
     - FORCE_CREATE_PULSE = create a new pulse (erase old one if already exist)
     @return data entry context id

  int al_begin_dataentry_action(const char *uri, int mode) throws ALException;
  */
  public static native int al_begin_dataentry_action(
                 final String uri, int mode) throws ALException;

  /**
     Closes a database entry.
     This function closes a database entry described by the passed pulse context.
     @param[in] pulseCtx pulse context id (from al_begin_uri_action())
     @param[in] mode closing option:
     - CLOSE_PULSE = close the pulse
     - ERASE_PULSE = close and remove the pulse 
     @param[in] options additional options (possibly backend specific)
     @result error status
  int al_close_pulse(int pulseCtx, 
		      int mode) throws ALException;

*/
  public static native void al_close_pulse(int pulseCtx, 
		      int mode) ;
  /**
     Starts an I/O action on a DATAOBJECT.
     This function gives a new operation context for the duration of an action on a DATAOBJECT.
     @param[in] ctx pulse context id (from al_begin_uri_action())
     @param[in] dataobjectname name of the DATAOBJECT
     @param[in] rwmode mode for this operation:
     - READ_OP = read operation
     - WRITE_OP = write operation
     @result operation context id [_or error status if < 0_]
     
     @test Low-level API, implementation of beginDataObjectPut()
     @snippet al_low_level.c ex_al_begin_global_action

  int al_begin_global_action(int ctx,
			      const char *dataobjectname,
			      int rwmode) throws ALException;
*/
  public static native int al_begin_global_action(int ctx,
			      final String dataobjectname,
			      int rwmode) throws ALException;
  /**
     Starts an I/O action on a DATAOBJECT slice.
     This function gives a new operation context for the duration of an action on a slice.  
     @param[in] ctx pulse context (from al_begin_uri_action())
     @param[in] dataobjectname name of the DATAOBJECT
     @param[in] rwmode mode for this operation:
     - READ_OP: read operation
     - WRITE_OP: write operation
     - REPLACE_OP: replace operation
     @param[in] time time of the slice
     - UNDEFINED_TIME if not relevant (e.g to append a slice or replace the last slice)
     @param[in] interpmode mode for interpolation:
     - CLOSEST_INTERP take the slice at the closest time
     - PREVIOUS_INTERP take the slice at the previous time
     - LINEAR_INTERP interpolate the slice between the values of the previous and next slice
     - UNDEFINED_INTERP if not relevant (for write operations)
     @result operation context id [_or error status if < 0_]
     
     @test Low-level API, implementation of beginDataObjectGetSlice()
     @snippet al_low_level.c ex_al_begin_slice_action

  int al_begin_slice_action(int ctx,
			     const char *dataobjectname,
			     int rwmode,
			     double time,
			     int interpmode) throws ALException;
*/
  public static native int al_begin_slice_action(int ctx,
			     final String dataobjectname,
			     int rwmode,
			     double time,
			     int interpmode) throws ALException;
  /**
     Stops an I/O action.
     This function stop the current action designed by the context passed as argument. This context is then 
     not valide anymore.
     @param[in] ctx a pulse (al_begin_uri_action()), an operation (al_begin_global_action() or al_begin_slice_action()) or an array of structure context id (al_begin_array_struct_action())
     @result error status
     
     @test Low-level API, implementation of endDataObjectGetSlice()
     @snippet al_low_level.c ex_al_end_action

  int al_end_action(int ctx) throws ALException; 
  */
  public static native void al_end_action(int ctx) throws ALException;

   /**
     This function registers a plugin.
     @param[in] plugin name
  */
  public static native void al_register_plugin(final String pluginName) throws ALException;

   /**
     This function unregisters a plugin.
     @param[in] plugin name
  */
  public static native void al_unregister_plugin(final String pluginName) throws ALException; 
  
    /**
     Bind readback plugins.
     This function binds readback plugins before a get()/get_slice() operation.
     @param[in] ctx an operation context (al_begin_global_action() or al_begin_slice_action())
  */
  public static native void al_bind_readback_plugins(int ctx) throws ALException; 

    /**
     Unbind readback plugins.
     This function unbinds readback plugins after a get()/get_slice() operation.
     @param[in] ctx an operation context (al_begin_global_action() or al_begin_slice_action())
  */
  public static native void al_unbind_readback_plugins(int ctx) throws ALException;

  /**
     Store plugins metadata.
     This function stores plugins metadata after a put()/put_slice() operation.
     @param[in] ctx an operation context (al_begin_global_action() or al_begin_slice_action())
  */
  public static native void al_write_plugins_metadata(int ctx) throws ALException;

  /**
     Set the value of type double of a plugin parameter.
     This function sets the value of a parameter plugin.
     @param[parameterName] name of the parameter
     @param[dim] dimension of the parameter (0 to 6)
     @param[size] shape of the values
     @param[data] parmameter double values
     @param[pluginName] name of the plugin
  */
  public static native void al_set_doublevalue_parameter_plugin(final String parameterName, int dim, int[] size, double[] data, final String pluginName) throws ALException;

  /**
     Set the value of type int of a plugin parameter.
     This function sets the value of a parameter plugin.
     @param[parameterName] name of the parameter
     @param[dim] dimension of the parameter (0 to 6)
     @param[size] shape of the values
     @param[data] parmameter int values
     @param[pluginName] name of the plugin
  */
  public static native void al_set_intvalue_parameter_plugin(final String parameterName, int dim, int[] size, int[] data, final String pluginName) throws ALException;
 
  /**
     Writes data.
     This function writes a signal in the database given the passed context.
     @param[in] ctx operation context id (from al_begin_global_action() or al_begin_slice_action()) or
     array of structure context id (from al_begin_arraystruct_action())
     @param[in] fieldpath field path for the data (paths are always relative to current Context, dataobject absolute path can be specified with a prepended '/')
     @param[in] timebasepath field path for the timebase (paths are always relative to current Context, dataobject absolute path can be specified with a prepended '/')
     @param[in] data pointer on the data to be written
     @param[in] datatype type of data to be written:
     - CHAR_DATA strings
     - INTEGER_DATA integers
     - DOUBLE_DATA double precision floating points
     - COMPLEX_DATA complex numbers
     @param[in] dim dimension of the data (0=scalar, 1=1D vector, etc... up to MAXDIM)
     @param[in] size array of the size of each dimension (can be NULL if dim=0)
     @result error status

     @test Low-level API, implementation of putVect2DIntSlice()
     @snippet al_low_level.c ex_al_write_data

  int al_write_data(int ctx,
		     const char *fieldpath,
		     const char *timebasepath,
		     void *data,
		     int datatype,
		     int dim,
		     int *size) throws ALException;
*/

  public static native void al_write_data_int(int ctx,
		     final String fieldpath,
		     final String timebasepath,
		     int[] data,
		     int dim,
		     int[] size) throws ALException;


  public static native void al_write_data_double(int ctx,
             final String fieldpath,
             final String timebasepath,
             double[] data,
             int dim,
             int[] size) throws ALException;

  public static native void al_write_data_complex(int ctx,
             final String fieldpath,
             final String timebasepath,
             Complex[] data,
             int dim,
             int[] size) throws ALException;

  public static native void al_write_data_char(int ctx,
             final String fieldpath,
             final String timebasepath,
             byte[] data,
             int dim,
             int[] size) throws ALException;

  /**
     Reads data.
     This function reads a signal in the database given the passed context.
     @param[in] ctx operation context id (from al_begin_global_action() or al_begin_slice_action()) or
     array of structure context id (from al_begin_arraystruct_action())
     @param[in] fieldpath field path for the data (paths are always relative to current Context, dataobject absolute path can be specified with a prepended '/')
     @param[in] timebasepath field path for the timebase (paths are always relative to current Context, dataobject absolute path can be specified with a prepended '/')
     @param[out] data returned pointer on the read data 
     @param[in] datatype type of data to be read:
     - CHAR_DATA strings
     - INTEGER_DATA integers
     - DOUBLE_DATA double precision floating points
     - COMPLEX_DATA complex numbers
     @param[in] dim dimension of the data (0=scalar, 1=1D vector, etc... up to MAXDIM)
     @param[in,out] size passed array for storing the size of each dimension (size[i] undefined if i>=dim)
     @result error status
     
     @test Low-level API, implementation of getVect3DDouble() 
     @snippet al_low_level.c ex_al_read_data

  int al_read_data(int ctx,
		    const char *fieldpath,
		    const char *timebasepath,
		    void **data,
		    int datatype,
		    int dim,
		    int *size) throws ALException;
  */
 public static native int[] al_read_data_int(int ctx,
		    final String fieldpath,
		    final String timebasepath,
		    int dim,
		    int[] size) throws ALException;

 public static native double[] al_read_data_double(int ctx,
            final String fieldpath,
            final String timebasepath,
            int dim,
            int[] size) throws ALException;

 public static native Complex[] al_read_data_complex(int ctx,
            final String fieldpath,
            final String timebasepath,
            int dim,
            int[] size) throws ALException;


 public static native byte[] al_read_data_char(int ctx,
            final String fieldpath,
            final String timebasepath,
            int dim,
            int[] size) throws ALException;

  /**
    Deletes data.
    This function deletes some data (can be a signal, a structure, the whole DATAOBJECT) in the database 
    given the passed context.
    @param[in] ctx operation context id (from al_begin_global_action() or al_begin_slice_action())
    @param[in] path path of the data structure element to delete (suppress the whole subtree)
    @result error status

  int al_delete_data(int ctx,
		      const char *path) throws ALException;


  */
  public static native void al_delete_data(int ctx,
		      final String path) throws ALException;
  /**
     Starts operations on a new array of structure.
     This function gives a new array of structure context id for the duration of an action 
     on an array of structures, either from an operation context id (single or top-level array) 
     or from an array of structure context id (nested array).  
     @param[in] ctx operation context id (single case) or array of structure context id 
     (nested case) 
     @param[in] path path of array of structure (relative to ctx, or absolute if starts with "/")
     @param[in] timebase path of timebase associated with the array of structure 
     @param[in,out] size specify the size of the struct_array (number of elements)
     @result array of structure context [_or error status if < 0_]

  int al_begin_arraystruct_action(int ctx,
				   const char *path,
				   const char *timebase,
				   int *size) throws ALException;


  */

  public static native int al_begin_arraystruct_action(int ctx,
				   String path,
				   String timebase,
				   int[] size) throws ALException;
  /**
     Change current index of interest in an array of structure.
     This function updates the index pointing at the current element of interest within an array of structure.
     @param[in] aosctx array of structure Context
     @param[in] step iteration step size (typically=1)
     @result error status

  int al_iterate_over_arraystruct(int aosctx, 
				   int step) throws ALException;

  */
  public static native void al_iterate_over_arraystruct(int aosctx, 
				   int step) throws ALException;

  public static native int al_get_backendID(int backendId) throws ALException;

  public static native String al_get_version();

}
