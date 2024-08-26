IDS API
=======

.. highlight:: java

..
    Subroutines are generated, so choosing to document manual instead.

.. java:type:: public class ids_type

    .. java:method:: public static ids_type get(int expIdx, String idsFullName)

        Read the contents of the an IDS into memory.

        This method fetches the IDS in its entirety, with all time slices it may
        contain. See :java:ref:`getSlice` for reading a specific time slice.

        Empty fields within the IDS in the Data Entry are returned with the
        default values indicated in :ref:`Default values`.

        :param int expIdx: Data entry context created with
            :java:ref:`imas.open`, :java:ref:`imas.openEnv` or
            :java:ref:`imas.createEnv`
        :param String idsFullName: name of the ids with optional occurrence
            number, e.g. ``"core_profiles"`` (for occurrence 0),
            ``"core_profiles/1"`` (for occurrence 1)
        :return: The loaded IDS
        :example: .. literalinclude:: code_samples/dbentry_get

    .. java:method:: public static ids_type getSample(int expIdx, String idsFullName, double tmin, double tmax, double[] dtime, int interpolMode)

        Read the contents of an IDS over a specific time range into memory.

        This method fetches an IDS with all time slices in a time range between tmin and tmax. 
        1. In case of no interpolation in the time range, interpolMode must be set to 0 and dtime = {}.
        2. The method can interpolate time slices in the time range, if interpolMode = 1 and dtime = {step} (double array of size equals 1). 
           The 'step' is the regular time step. 

        3. Interpolation of dynamic data on an explicit time base. This method is selected
            when dtime and interpolMode are provided.
            dtime must be a double[] of size larger than 1.

            This mode will generate an IDS with a homogeneous time vector equal to
            dtime. tmin and tmax are ignored in this mode.

        Empty fields within the IDS in the Data Entry are returned with the
        default values indicated in :ref:`Default values`.

        :param int expIdx: Data entry context created with
            :java:ref:`imas.open`, :java:ref:`imas.openEnv` or
            :java:ref:`imas.createEnv`
        :param String idsFullName: name of the ids with optional occurrence
            number, e.g. ``"core_profiles"`` (for occurrence 0),
            ``"core_profiles/1"`` (for occurrence 1)
        :param double tmin: Lower bound of the requested time range
        :param double tmax:  Upper bound of the requested time range, must be larger than or
                equal to :param:`tmin`
        :param double[] dtime: Interval to use when interpolating, must be a std::vector<double>
                containing an explicit time base to interpolate.
        :param interpolMode: Interpolation method to use. Available options:

            - :const: CLOSEST_INTERP
            - :const: PREVIOUS_INTERP
            - :const: LINEAR_INTERP

            :returns: The loaded IDS.
        :example: .. literalinclude:: code_samples/dbentry_getSample

    .. java:method:: public boolean isDefined()
        
        Checks whether IDS was initialized or not.
        
        This method returns true in case `ids_properties` field inside `IDS` is initialized and `homogenous_time` is set to either `0`, or `1`, or `2`

        :return: `true` in case `IDS` is already initialized, `false` otherwise
        :example: .. literalinclude:: code_samples/ids_is_defined

    .. java:method:: public static ids_type getSlice(int pulseCtx, String idsFullName, double time, int interpolMode)

        Read a single time slice from an IDS in this Database Entry.

        This method fetches the IDS object with all constant/static data filled.
        The dynamic data is interpolated on the requested time slice. This means
        that the size of the time dimension in the returned data is 1.

        :param int pulseCtx: Data entry context created with
            :java:ref:`imas.open`, :java:ref:`imas.openEnv` or
            :java:ref:`imas.createEnv`
        :param String idsFullName: name of the ids with optional occurrence
            number, e.g. ``"core_profiles"`` (for occurrence 0),
            ``"core_profiles/1"`` (for occurrence 1)
        :param double time: Requested time slice
        :param int interpolMode: Interpolation method to use, see :ref:`Load a
                single \`time slice\` of an IDS`
        :return: The loaded IDS
        :example: .. literalinclude:: code_samples/dbentry_getslice

    .. java:method:: public static void put(int pulseCtx, String idsFullName, ids_type ids)

        Write the contents of an IDS to the Database Entry.

        The IDS is written entirely, with all time slices it may contain.

        The IDS object can have none or many empty fields, empty fields are
        ignored and remain empty in the data entry. Some fields are required to
        be filled before calling this method, see :ref:`Mandatory and
        recommended IDS attributes`.

        .. caution::
            The put method deletes any previously existing data within the
            target IDS occurrence in the Database Entry.

        :param int pulseCtx: Data entry context created with
            :java:ref:`imas.open`, :java:ref:`imas.openEnv` or
            :java:ref:`imas.createEnv`
        :param String idsFullName: name of the ids with optional occurrence
            number, e.g. ``"core_profiles"`` (for occurrence 0),
            ``"core_profiles/1"`` (for occurrence 1)
        :param ids_type ids: IDS object to put
        :example: .. literalinclude:: code_samples/dbentry_put

    .. java:method:: public static void putSlice(int pulseCtx, String idsFullName, ids_type ids)

        Append a time slice of the provided IDS to the Database Entry.

        Time slices must be appended in strictly increasing time order, since
        the Access Layer is not reordering time arrays. Doing otherwise will
        result in non-monotonic time arrays, which will create confusion and
        make subsequent :java:ref:`getSlice` commands to fail.

        Although being put progressively time slice by time slice, the final IDS
        must be compliant with the data dictionary. A typical error when
        constructing IDS variables time slice by time slice is to change the
        size of the IDS fields during the time loop, which is not allowed but
        for the children of an array of structure which has time as its
        coordinate.

        The :java:ref:`putSlice` command is appending data, so does not modify
        previously existing data within the target IDS occurrence in the Data
        Entry.

        It is possible possible to append several time slices to a node of the
        IDS in one :java:ref:`putSlice` call, however the user must ensure that
        the size of the time dimension of the node remains consistent with the
        size of its timebase.

        :param int pulseCtx: Data entry context created with
            :java:ref:`imas.open`, :java:ref:`imas.openEnv` or
            :java:ref:`imas.createEnv`
        :param String idsFullName: name of the ids with optional occurrence
            number, e.g. ``"core_profiles"`` (for occurrence 0),
            ``"core_profiles/1"`` (for occurrence 1)
        :param ids_type ids: IDS object to put
        :example: .. literalinclude:: code_samples/dbentry_put_slice

    .. java:method:: public byte[] serialize()
    .. java:method:: public byte[] serialize(int protocol)

        Serialize the contents of this IDS into binary data.

        While it is by design allowed to specify various serialization
        protocols, it currently implements only a serialization through usage of
        the ASCII backend (simpler but less efficient) which is de-facto the
        default serializer protocol. The ID of the used serializer protocol is
        kept in the serialized buffer, such that specifying the protocol is not
        necessary when deserializing.

        :param int protocol: Which serialization protocol to use. Available
            options are: 

            - :java:ref:`ASCII_SERIALIZER_PROTOCOL`
            - :java:ref:`DEFAULT_SERIALIZER_PROTOCOL`
        :return: Binary representation of this IDS.
        :example:
            .. code-block:: java

                imas.ids_pf_active ids = new imas.ids_pf_active();
                // populate the IDS
                // ...
                byte[] data = ids.serialize();

                // move the binary data around, for example to another process using
                // memory communication, then deserialize
                imas.ids_pf_active ids2 = new imas.ids_pf_active();
                ids2.deserialize(data);

    .. java:method:: public void deserialize(byte[] data)

        Deserialize the provided binary data into an IDS.

        :param byte[] data: data representing a serialized IDS.
        :example: See :java:ref:`serialize`.

    .. java:method:: public void validate()

        Validate the IDS coordinate consistency. The method should always be tested for exception/errors while it is being executed. A ValidationException can be raised if a coordinate inconsistency is found. Nothing occurs if the data are valids.

        :example: .. literalinclude:: code_samples/ids_validate
