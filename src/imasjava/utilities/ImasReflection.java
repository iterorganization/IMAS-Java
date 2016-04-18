package imasjava.utilities;

import imasjava.*;
import imasjava.ids.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ImasReflection {



	static public Class<?> getIdsClass(String idsName) throws java.lang.ClassNotFoundException
	{
		String idsClassName = "imasjava.imas$" + idsName;

		Class<?> idsClass = Class.forName(idsClassName);
    	
		return idsClass;
	}
	
	
	static public Class<?> getNodeClass(Class<?> classParent, String strNodeName) throws java.lang.NoSuchFieldException
	{
		Class<?> classIdsNode = null;
		
		Field field = classParent.getField(strNodeName);
		
		classIdsNode = field.getType();
    		if (classIdsNode.isArray()) 
		{
			classIdsNode = classIdsNode.getComponentType();
		}
	
		return classIdsNode;
	}
	
	
	static public String getClassNameForNode(String strIdsName, String strNodePath) 
	{
		Class<?> nodeClass = null;
		String strClassName = null;
		
		try
		{
			nodeClass = ImasReflection.getIdsClass(strIdsName);
	
		     	String[] strIdsNodeArray = strNodePath.split("/");
		
			for(String strNodeName : strIdsNodeArray){		
		       		nodeClass = ImasReflection.getNodeClass(nodeClass, strNodeName);
		 	}
	     	
			strClassName = nodeClass.getName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return strClassName;
	}


	public static void main(String [] arg) 
	{
	
	String strIdsName = "equilibrium";
	String nodePath   = "time_slice/boundary/x_point";
	
	 System.out.println(ImasReflection.getClassNameForNode(strIdsName, nodePath));

	}
}
