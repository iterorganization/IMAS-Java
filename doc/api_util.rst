Additional Access Layer types
=============================

.. java:type:: public class Complex

    Representation of a Complex number, i.e. a number which has both a  real and
    imaginary part.

    Based on The Apache Commons Mathematics Library
    `<http://commons.apache.org/math/>`_
    
    .. java:field:: public static final Complex I

        The square root of -1. A number representing "0.0 + 1.0i"
    
    .. java:field:: public static final Complex NaN

        A complex number representing "NaN + NaNi"

    .. java:field:: public static final Complex INF

        A complex number representing "+INF + INFi"

    .. java:field:: public static final Complex ONE

        A complex number representing "1.0 + 0.0i"

    .. java:field:: public static final Complex ZERO

        A complex number representing "0.0 + 0.0i"

    .. java:constructor:: public Complex(double real)

        Create a complex number given only the real part.

        :param double real: Real part.

    .. java:constructor:: public Complex(double real, double imaginary)

        Create a complex number given the real and imaginary parts.

        :param double real: Real part.
        :param double imaginary: Imaginary part.

    .. java:constructor:: public Complex (String strVal)

        Create a complex number from a String. Its syntax must be (without
        spaces):

        - <decimal number>+<decimal number>i 
        - or (<decimal number>,<decimal number>i)

        :param String strVal: string value.

    .. java:method:: public double getReal()

        Access the real part.

    .. java:method:: public double getImaginary()

        Access the imaginary part.


Vector types
------------

All vector types provide the following methods:

.. info::

    Not all array methods are documented.

.. java:method:: public type getElementAt(int i, int j, int k, int h, int l, int m)

    Get the value at the provided index.

    :param int i: Index of first dimension.
    :param int j: Index of second dimension (only for 2D+ vectors).
    :param int k: Index of third dimension (only for 3D+ vectors).
    :param int h: Index of fourth dimension (only for 4D+ vectors).
    :param int l: Index of fifth dimension (only for 5D+ vectors).
    :param int m: Index of sixth dimension (only for 6D vectors).

.. java:method:: public void setElementAt(int i, int j, int k, int h, int l, int m, type element)

    Set the value at the provided index.

    :param int i: Index of first dimension.
    :param int j: Index of second dimension (only for 2D+ vectors).
    :param int k: Index of third dimension (only for 3D+ vectors).
    :param int h: Index of fourth dimension (only for 4D+ vectors).
    :param int l: Index of fifth dimension (only for 5D+ vectors).
    :param int m: Index of sixth dimension (only for 6D vectors).
    :param type element: Value to set at the provided index.

.. java:method:: public type[][][][][][] get()

    Returns internal array as row-major ordered array - copy of an array is
    returned.
   
    If you want to update values of original column-major ordered array you have
    to use one of the methods: :java:ref:`set` or :java:ref:`setElementAt`.

    .. note::
        The dimension of the returned array depends on the dimension of the
        vector. The example signature here is for a 6-dimensional vector.

.. java:method:: public void set(type[][][][][][] array)

    Sets internal array in column-major ordered way.

    :param type[][][][][][] array: row-major ordered array

    .. note::
        The dimension of the array you need to provide depends on the
        dimension of the vector. The example signature here is for a
        6-dimensional vector.

Overview of vector types:
'''''''''''''''''''''''''

.. java:type:: public class Vect1DString
.. java:type:: public class Vect1DInt
.. java:type:: public class Vect1DDouble
.. java:type:: public class Vect1DComplex

.. java:type:: public class Vect2DInt
.. java:type:: public class Vect2DDouble
.. java:type:: public class Vect2DComplex

.. java:type:: public class Vect3DInt
.. java:type:: public class Vect3DDouble
.. java:type:: public class Vect3DComplex

.. java:type:: public class Vect4DDouble
.. java:type:: public class Vect4DComplex

.. java:type:: public class Vect5DDouble
.. java:type:: public class Vect5DComplex

.. java:type:: public class Vect6DDouble
.. java:type:: public class Vect6DComplex