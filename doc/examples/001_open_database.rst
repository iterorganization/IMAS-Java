========================================================================================================================
IMAS DBEntry Example
========================================================================================================================

These examples demonstrates how to **create** and **open** *DB entries* using different methods in the IMAS framework.

We'll explore three examples:

* creating a DB entry using the **legacy** method,
* opening a DB entry using a **URI** created based on **legacy** parameters
* opening a DB entry using a **URI** and explicit **path**.




---------------------------------------------------------------------------------------------------------
Create DBEntry from scratch using *legacy* mode
---------------------------------------------------------------------------------------------------------

This example focuses on creating DBEntry using legacy mode method.

.. warning::

    The legacy method is deprecated from ``AL>=5.0.0``.

    It is recommended to use the **URI** approach instead.


.. literalinclude:: ../code_samples/java/imasjava/examples/Example001_OpenDatabase.java
    :start-after:  // This example focuses on creating DBEntry using legacy mode method
    :end-before:      // This example focuses on opening DBEntry using URI
    :language: java
    :dedent: 4



---------------------------------------------------------------------------------------------------------
Open existing DBEntry using new IMAS AL5 URI with *legacy* parameters
---------------------------------------------------------------------------------------------------------
This example focuses on opening DBEntry using **URI with legacy parameters**

.. literalinclude:: ../code_samples/java/imasjava/examples/Example001_OpenDatabase.java
    :start-after:  // This example focuses on opening DBEntry using URI
    :end-before:      // This example focuses on creating DBEntry using explicit path
    :language: java
    :dedent: 4



---------------------------------------------------------------------------------------------------------
Open existing DBEntry using new IMAS AL5 URI with new explicit ``path`` parameter
---------------------------------------------------------------------------------------------------------

This example focuses on creating DBEntry using explicit **path** parameter.

.. literalinclude:: ../code_samples/java/imasjava/examples/Example001_OpenDatabase.java
    :start-after:      // This example focuses on creating DBEntry using explicit path
    :language: java



.. output::

    .. code-block:: bash

        javac -cp $CLASSPATH:. imasjava/examples/TestNewExamples.java ; java -cp $CLASSPATH:. imasjava.examples.TestNewExamples

        Running OpenDatabase example case

        Content of testdb_mdsplus:
        ./testdb_mdsplus/ids_001.characteristics
        ./testdb_mdsplus/ids_001.datafile
        ./testdb_mdsplus/ids_001.tree

        Content of testdb_hdf5:
        ./testdb_hdf5/master.h5

        Content of testdb_ascii:


