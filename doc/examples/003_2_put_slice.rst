=========================================================================================================
Put Multiple Slices
=========================================================================================================

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

.. seealso::

    API documentation for :java:ref:`imas.open`, :java:ref:`putSlice`, :java:ref:`setElementAt`, :java:ref:`getElementAt`