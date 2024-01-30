``imasjava.utilities`` API
==========================

.. java:package:: imasjava.utilities

.. java:type:: public class ImasReflection

    .. java:method:: static public Class<?> getIdsClass(String idsName) throws java.lang.ClassNotFoundException

        Function returns class for provided IDS.

        :param String idsName: IDS Name
        :return: IDS's class name

    .. java:method:: static public Class<?> getNodeClass(Class<?> classParent, String strNodeName) throws java.lang.NoSuchFieldException

        Function returns node's class for provided Node.
		
	:param Class<?> classParent: Patent class
        :param String strNodeName: Node Name
        :return: IDS Node class

    .. java:method:: static public Class<?> getClassForNode(String strIdsName, String strNodePath)

        Function returns class for IDS in Node path.

        :param String strIdsName: IDS Name
	:param String strNodePath: Node Path
        :return: class for IDS in Node path

    .. java:method:: static public String getClassNameForNode(String strIdsName, String strNodePath)

        Function return class name for in Node path.

        :param String strIdsName: IDS Name
	:param String strNodePath: Node Path
        :return: class name
        :example: See :ref:``

    .. java:method:: static public Object[] getResizedClassObject(Object[] className, int newSize)

        Function return resized array of a provided class object.

        :param Object[] className: array of object of class
	:param int newSize: new size of array
        :return: resized array
        :example: .. literalinclude:: code_samples/resize_array
