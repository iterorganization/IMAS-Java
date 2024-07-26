========================================================================================================================
Writing Data into DBEntry
========================================================================================================================


Topics included in this lesson correspond to writing data into DBEntry:

- How to put entire IDS into entry and pass IDS validation,
- How to put multiple slices of IDS into entry,
- How to put IDS into another occurrence using `imas` and `numpy` libraries.


---------------------------------------------------------------------------------------------------------
Putting Entire IDS
---------------------------------------------------------------------------------------------------------

This example focuses on putting IDS into entry and passing IDS validation.

.. literalinclude:: ../code_samples/java/imasjava/examples/Example003_writeDataIntoEntry.java
    :start-after:   // This example focuses on putting IDS into entry and passing IDS validation
    :end-before:    // This example focuses on putting multiple slices of IDS into entry
    :language: java
    :dedent: 4


.. output::

    .. code-block:: bash

        Saved equilibrium/vacuum_toroidal_field/b0 field: [1.0,2.0,3.0]




---------------------------------------------------------------------------------------------------------
Putting Multiple Slices
---------------------------------------------------------------------------------------------------------

This example focuses on putting multiple slices of IDS into entry.

.. literalinclude:: ../code_samples/java/imasjava/examples/Example003_writeDataIntoEntry.java
    :start-after:   // This example focuses on putting multiple slices of IDS into entry
    :end-before:    // This example focuses on putting IDS into another occurrence
    :language: java
    :dedent: 4


.. output::

    .. code-block:: bash

        Saved summary/time field:
        [0.0,1.0,2.0,3.0,4.0,50.0,60.0,70.0]

        Saved summary/heating_current_drive/nbi[0]/beam_current_fraction/value field:
        [[0.0,100.0,200.0,300.0,400.0,1000.0,2000.0,3000.0][0.0,100.0,200.0,300.0,400.0,1000.0,2000.0,3000.0][0.0,100.0,200.0,300.0,400.0,1000.0,2000.0,3000.0]]

        Saved summary/stationary_phase_flag/value field:
        [0,10,20,30,40,11,12,13]



---------------------------------------------------------------------------------------------------------
Putting IDS into Non-default Occurrence
---------------------------------------------------------------------------------------------------------

This example focuses on putting IDS into another occurrence.

.. literalinclude:: ../code_samples/java/imasjava/examples/Example003_writeDataIntoEntry.java
    :start-after:       // This example focuses on putting IDS into another occurrence
    :language: java


.. output::

    .. code-block:: bash


        Saved equilibrium/ids_properties/comment field: (occurrence 1)
        comment from 1st occurrence

        Saved equilibrium/ids_properties/comment field: (occurrence 2)
        comment from 2nd occurrence

        equilibrium occurrences without nodePath
        {0=, 1=, 2=}

        equilibrium occurrences with 'ids_properties/comment' nodePath
        {0=, 1=comment from 1st occurrence, 2=comment from 2nd occurrence}