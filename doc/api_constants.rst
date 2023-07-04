IMAS constants
==============

.. java:package:: imasjava.wrapper

.. java:type:: public class LowLevel

    Interpolation modes
    -------------------

    .. java:field:: public final static int CLOSEST_INTERP = 1

        Interpolation method that returns the `closest` time slice in the original
        IDS (can break causality as it can return data ahead of requested time).

        .. seealso:: :java:ref:`getSlice`

    .. java:field:: public final static int PREVIOUS_INTERP = 2

        Interpolation method that returns the previous time slice if the requested
        time does not exactly exist in the original IDS.

        .. seealso:: :java:ref:`getSlice`

    .. java:field:: public final static int LINEAR_INTERP = 3

        Interpolation method that returns a linear interpolation between the
        existing slices before and after the requested time.

        .. seealso:: :java:ref:`getSlice`


    Empty values
    ------------

    .. java:field:: public static final int EMPTY_INT = -999999999

        Value representing an unset integer in an IDS.

    .. java:field:: public static final double EMPTY_DOUBLE = -9.0E40

        Value representing an unset floating point number in an IDS.

    .. java:field:: public static final Complex EMPTY_COMPLEX = new Complex(EMPTY_DOUBLE, EMPTY_DOUBLE)

        Value representing an unset complex number in an IDS.


    Serializer protocols
    --------------------

    .. java:field:: public final static int ASCII_SERIALIZER_PROTOCOL = 60

        Identifier for the ASCII serialization protocol.

    .. java:field:: public final static int DEFAULT_SERIALIZER_PROTOCOL

        Identifier for the default serialization protocol.


    Time modes
    ----------

    .. java:field:: public final static int IDS_TIME_MODE_HETEROGENEOUS = 0

        Time mode indicating that dynamic nodes may be asynchronous.

        Timebases of quantities are as indicated in the "Coordinates" column of the
        Data Dictionary documentation.

    .. java:field:: public final static int IDS_TIME_MODE_HOMOGENEOUS = 1

        Time mode indicating that dynamic nodes are synchronous.

        Timebases of quantities are the "time" node that is the child of the nearest
        parent IDS.

    .. java:field:: public final static int IDS_TIME_MODE_INDEPENDENT = 2

        Time mode indicating that no dynamic nodes are filled in the IDS.


    Backend identifiers
    -------------------

    .. java:field:: public final static int ASCII_BACKEND = 11

        :ref:`ASCII backend`

    .. java:field:: public final static int MDSPLUS_BACKEND = 12

        :ref:`MDSPLUS backend`

    .. java:field:: public final static int HDF5_BACKEND = 13

        :ref:`HDF5 backend`

    .. java:field:: public final static int MEMORY_BACKEND = 14

        :ref:`MEMORY backend`

    .. java:field:: public final static int UDA_BACKEND = 15

        :ref:`UDA backend`


    Data entry open/create modes
    ----------------------------

    .. java:field:: public final static int OPEN_PULSE = 40

        Opens the access to the data only if the Data Entry exists, returns error
        otherwise.

    .. java:field:: public final static int FORCE_OPEN_PULSE = 41

        Opens access to the data, creates the Data Entry if it does not exists yet.

    .. java:field:: public final static int CREATE_PULSE = 42

        Creates a new empty Data Entry (returns error if Data Entry already exists)
        and opens it at the same time.

    .. java:field:: public final static int FORCE_CREATE_PULSE = 43

        Creates an empty Data Entry (overwrites if Data Entry already exists) and
        opens it at the same time.


