=========================================================================================================
Read IDS Slices
=========================================================================================================

This example focuses on reading IDS slices from entry.

.. literalinclude:: ../code_samples/java/imasjava/examples/Example004_readDataFromEntry.java
    :start-after:         // This example focuses on reading IDS slices from entry
    :language: java
    :linenos:

.. output::

    .. code-block:: bash

        summary/heating_current_drive/nbi[0]/beam_current_fraction/value:
        [[0.0,0.0,0.0][0.0,100.0,200.0][0.0,200.0,400.0]]

        summary/global_quantities/ip/value:
        [10.0,11.0,12.0]

        summary/global_quantities/ip/value for PREVIOUS_INTERP and time=1: [10.0]
        summary/global_quantities/ip/value for CLOSEST_INTERP and time=2: [11.0]
        summary/global_quantities/ip/value for LINEAR_INTERP and time=1.75: [10.75]
    
.. seealso::

    API documentation for :java:ref:`imas.open`, :java:ref:`put`, :java:ref:`getSlice`