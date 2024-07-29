=========================================================================================================
Put IDS into Non-default Occurrence
=========================================================================================================

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

.. seealso::

    API documentation for :java:ref:`imas.open`, :java:ref:`put`, :java:ref:`listAllOccurrences`