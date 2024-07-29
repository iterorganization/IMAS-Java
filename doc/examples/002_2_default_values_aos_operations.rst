=========================================================================================================
Handling AoS & Default Values
=========================================================================================================

This example focuses on handling arrays of structures and default values


.. literalinclude:: ../code_samples/java/imasjava/examples/Example002_fillingDataInIDS.java
    :start-after:      // This example focuses on handling arrays of structures and default values
    :end-before:       // This example focuses on creating multi-dimensional arrays, using copmlex type and copying IDS structures
    :language: java
    :dedent: 4

.. output::

    .. code-block:: bash

        Size of grid_ggd before resize: 1
        Message in 'emptyEdgeProfiles.grid_ggd[0]' before resize: Structure before resize

        Size of grid_ggd after resize: 2
        Message in 'emptyEdgeProfiles.grid_ggd[0]' after resize(data destroyed): null

        Size of grid_ggd after resize to 3: 3
        Message in 'emptyEdgeProfiles.grid_ggd[0]' after resize: First test struct
        Message in 'emptyEdgeProfiles.grid_ggd[1]' after resize: Second test struct

        All messages saved in grid_ggd:
        First test struct
        Second test struct
        Third test struct

        Default value for 'INT' data  (edge_profiles/midplane/index)                                   : -999999999
        Default value for 'FLOAT' data  (edge_profiles/vacuum_toroidal_field/vacuum_toroidal_field/r0) : -9.0E40
        Default value for 'COMPLEX' data                                                               : (-9.0E40, -9.0E40i)
        Default value for 1+ dimensional data                                                          : null


After all modifications ``edge_profiles`` IDS looks like this:

.. code-block::

    Dumping edge_profiles from default_values_and_aos_operations() function

    class edge_profiles
    Attribute ids_properties
            class ids_properties
            Attribute homogeneous_time: 1
    Attribute grid_ggd
            class grid_ggddynamic
            grid_ggddynamic[0] =
                    class grid_ggddynamicObj
                    Attribute identifier
                            class identifier
                            Attribute name: First test struct
            grid_ggddynamic[1] =
                    class grid_ggddynamicObj
                    Attribute identifier
                            class identifier
                            Attribute name: Second test struct
            grid_ggddynamic[2] =
                    class grid_ggddynamicObj
                    Attribute identifier
                            class identifier
                            Attribute name: Third test struct


..
   Unable to add reference to "dump()" method, due to missing description of this method in documentation
