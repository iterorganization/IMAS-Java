========================================================================================================================
Reading Data From DBentry
========================================================================================================================

This document provides examples of using IMAS in Python to read and write IDS data.

In this document, we will learn how to differently read data from DBEntry:
    - reading entire IDS,
    - reading IDS slices,
    - reading partial IDS fields.


---------------------------------------------------------------------------------------------------------
Reading Entire IDS
---------------------------------------------------------------------------------------------------------

This example focuses on reading whole IDS from entry.
We are storing and reading back an IDS - equilibrium. Data are stored inside MDS+ file.


.. literalinclude:: ../code_samples/java/imasjava/examples/Example004_readDataFromEntry.java
    :start-after:     // This example focuses on reading whole IDS from entry.
    :end-before:     // This example focuses on reading IDS slices from entry
    :language: java
    :dedent: 4
    :linenos:



.. output::

    .. code-block:: bash

        is equilibrium defined?: true



---------------------------------------------------------------------------------------------------------
Reading IDS Slices with Access Layer interpolating methods:
---------------------------------------------------------------------------------------------------------

This example focuses on reading IDS slices from entry
We are storing and reading back an IDS - summary. Data are stored inside MDS+ file.

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



