``imasjava.imas`` API
=====================

.. java:package:: imasjava

.. java:type:: public class imas

    .. java:method:: public static int open(String uri, int mode)

        Open or create the Data Entry at the provided URI.

        :param String uri: :ref:`Data entry URI <Data entry URIs>`
        :param int mode: One of :java:ref:`OPEN_PULSE`,
            :java:ref:`FORCE_OPEN_PULSE`, :java:ref:`CREATE_PULSE`, :java:ref:`FORCE_CREATE_PULSE`
        :return: Opened data entry context
        :example: See :ref:`Open an existing IMAS Database Entry`.

    .. java:method:: public static int openEnv(int shot, int run, String user, String tokamak, String version)
    .. java:method:: public static int openEnv(int shot, int run, String user, String tokamak, String version, String options)
    .. java:method:: public static int openEnv(int shot, int run, String user, String tokamak, String version, int backendType)
    .. java:method:: public static int openEnv(int shot, int run, String user, String tokamak, String version, int backendType, String options) throws UALException

        Open the Data Entry defined by the provided parameters.

        :param int shot: Shot number
        :param int run: Run number
        :param String user: User name
        :param String tokamak: Tokamak name, also known as Database name
        :param String version: Major version of the data dictionary, e.g. ``"3"``
        :param int backendType: Backend ID to use: :java:ref:`ASCII_BACKEND`,
            :java:ref:`MDSPLUS_BACKEND`, :java:ref:`HDF5_BACKEND`,
            :java:ref:`MEMORY_BACKEND` or :java:ref:`UDA_BACKEND`.
        :param String options: Backend specific options
        :return: Opened data entry context

    .. java:method:: public static int createEnv(int shot, int run, String user, String tokamak, String version)
    .. java:method:: public static int createEnv(int shot, int run, String user, String tokamak, String version, String options)
    .. java:method:: public static int createEnv(int shot, int run, String user, String tokamak, String version, int backendType)
    .. java:method:: public static int createEnv(int shot, int run, String user, String tokamak, String version, int backendType, String options)

        Create the Data Entry defined by the provided parameters.

        :param integer shot: Shot number
        :param integer run: Run number
        :param String user: User name
        :param String tokamak: Tokamak name, also known as Database name
        :param String version: Major version of the data dictionary, e.g. ``"3"``
        :param int backendType: Backend ID to use: :java:ref:`ASCII_BACKEND`,
            :java:ref:`MDSPLUS_BACKEND`, :java:ref:`HDF5_BACKEND`,
            :java:ref:`MEMORY_BACKEND` or :java:ref:`UDA_BACKEND`.
        :param String options: Backend specific options
        :return: Opened data entry context
