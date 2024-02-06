.. include:: ./doc_common/using_al.rst

Using the Access Layer with your Java program
------------------------------------------------

The following example program will load the Java interface to the Access Layer
to print the version of the access layer and data dictionary.

.. highlight:: java


.. literalinclude:: code_samples/imas_hello_world.java
    :caption: ``imas_hello_world.java``

.. seealso:: :ref:`Version constants`

If you save this as a file ``imas_hello_world.java``, you can run it as
follows:

.. code-block:: console

    $ java imas_hello_world.java
    Hello world!
    Access Layer version info:
      Low level version: 5.0.0
      Data Dictionary version: 3.39.0
      Java HLI version: 5.0.0

Congratulations if this runs successfully! You have included the Java Access
Layer successfully in a program. In the next sections of the documentation you
can see how to:

- :ref:`Loading and storing IMAS data`
- :ref:`Use Interface Data Structures`
