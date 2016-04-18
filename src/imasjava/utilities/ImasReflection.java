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
	

	
	static public Class<?> getClassForNode(String strIdsName, String strNodePath) 
	{
		Class<?> nodeClass = null;
		
		try
		{
			nodeClass = ImasReflection.getIdsClass(strIdsName);
	
		     	String[] strIdsNodeArray = strNodePath.split("/");
		
			for(String strNodeName : strIdsNodeArray){		
		       		nodeClass = ImasReflection.getNodeClass(nodeClass, strNodeName);
		 	}
	     	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return nodeClass;
	}
	
	
	static public String getClassNameForNode(String strIdsName, String strNodePath) 
	{
		Class<?> nodeClass = null;
		
		nodeClass = ImasReflection.getClassForNode(strIdsName, strNodePath);
	
		return nodeClass.getName();
	}




	public static void main(String [] arg) 
	{
	
	String strIdsName = "equilibrium";
	String nodePath   = "time_slice/boundary/x_point";
	
	 System.out.println(ImasReflection.getClassNameForNode(strIdsName, nodePath));

	}
}
