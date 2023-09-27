
.. highlight:: java

.. include:: ../../doc_common/use_ids.rst

.. |lang| replace:: Java

.. |create_ids_text| replace:: creating a new class object for that IDS, for example :code:`new imas.core_profiles()`
.. |copy_ids| replace:: by putting it to the memory backend and getting it again
.. |deallocate_ids_text| replace:: In Java this is automatically handled by the garbage collector of the JVM.

.. |structures_type| replace:: class
.. |structures_child_attribute| replace:: instance fields
.. |aos_type| replace:: an array of a class
.. |aos_default| replace:: :code:`null`
.. |aos_resize_meth| replace:: :code:`node = new nodeClass[n]`
.. |aos_resize_keep_meth| replace:: a temporary variable (as in below example)

.. |str_type| replace:: :java:ref:`String`
.. |str_1d_type| replace:: :java:ref:`Vect1DString`
.. |int_type| replace:: :code:`int`
.. |int_nd_type| replace:: :java:ref:`Vect1DInt`, :java:ref:`Vect2DInt`, :java:ref:`Vect3DInt`
.. |double_type| replace:: :code:`double`
.. |double_nd_type| replace:: :java:ref:`Vect1DDouble`, :java:ref:`Vect2DDouble`, :java:ref:`Vect3DDouble`, :java:ref:`Vect4DDouble`, :java:ref:`Vect5DDouble`, :java:ref:`Vect6DDouble`
.. |complex_type| replace:: :java:ref:`Complex`
.. |complex_nd_type| replace:: :java:ref:`Vect1DComplex`, :java:ref:`Vect2DComplex`, :java:ref:`Vect3DComplex`, :java:ref:`Vect4DComplex`, :java:ref:`Vect5DComplex`, :java:ref:`Vect6DComplex`

.. |str_default| replace:: :code:`null`
.. |str_1D_default| replace:: :code:`null`
.. |int_default| replace:: :code:`-999_999_999`, :java:ref:`EMPTY_INT`
.. |double_default| replace:: :code:`-9e40`, :java:ref:`EMPTY_DOUBLE`
.. |complex_default| replace:: :code:`-9e40 -9e40i`, :java:ref:`EMPTY_COMPLEX`
.. |ND_default| replace:: :code:`null`

.. |isFieldValid| replace:: \ .. ids_is_valid is not implemented in java

.. |tm_homogeneous| replace:: :java:ref:`IDS_TIME_MODE_HOMOGENEOUS`
.. |tm_heterogeneous| replace:: :java:ref:`IDS_TIME_MODE_HETEROGENEOUS`
.. |tm_independent| replace:: :java:ref:`IDS_TIME_MODE_INDEPENDENT`

.. |ids_validate| replace:: :java:ref:`ids_validate`
.. |validate_error| replace:: throws an error
