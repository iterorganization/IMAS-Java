=========================================================================================================
Create DBEntry using AL5 URI with ``path`` parameter
=========================================================================================================

This example focuses on creating DBEntry using explicit path

.. seealso::

    API documentation for :java:ref:`imas.open`

.. caution::

    the ``LowLevel.FORCE_CREATE_PULSE`` mode is used to create an entry. 
    
    Remember:  **it overwrites existing entry!**

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