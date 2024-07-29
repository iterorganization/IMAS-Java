=========================================================================================================
Create and copy multi-dimensional AoS 
=========================================================================================================

This example focuses on creating multi-dimensional arrays, using copmlex type and copying IDS structures.


.. literalinclude:: ../code_samples/java/imasjava/examples/Example002_fillingDataInIDS.java
    :start-after:      // This example focuses on creating multi-dimensional arrays, using copmlex type and copying IDS structures
    :language: java

.. output::

    .. code-block:: bash

        Filled 2D array (gyrokinetics_local/non_linear/fields_zonal_2d/phi_potential_perturbed_norm):
        [[(0.0, 0.0i),(0.0, 1.0i),(0.0, 2.0i)][(1.0, 0.0i),(1.0, 1.0i),(1.0, 2.0i)][(2.0, 0.0i),(2.0, 1.0i),(2.0, 2.0i)]]

        Caught exception (raised intentionally): With the time mode 'HOMOGENEOUS', the time array must be allocated and not be empty.

        Original value:
        [[(0.0, 0.0i),(0.0, 1.0i),(0.0, 2.0i)][(1.0, 0.0i),(1.0, 1.0i),(1.0, 2.0i)][(2.0, 0.0i),(2.0, 1.0i),(2.0, 2.0i)]]
        Copied value:
        [[(0.0, 0.0i),(0.0, 1.0i),(0.0, 2.0i)][(1.0, 0.0i),(1.0, 1.0i),(1.0, 2.0i)][(2.0, 0.0i),(2.0, 1.0i),(2.0, 2.0i)]]

.. seealso::

    API documentation for :java:ref:`validate`, :java:ref:`set` or :java:ref:`setElementAt`