import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

class CacheMonitor extends JFrame
{
    static final int ITEM_HEIGHT = 30; 



static class ItemDescriptor implements Comparable

{
    String cpoPathName;
    String pathName;
    boolean exists;
    boolean isSliced;
    long dataSize;
    int numSlices;
    ItemDescriptor(String cpoPathName, String pathName, byte exists, byte isSliced, long dataSize, int numSlices)
    {
	this.cpoPathName = cpoPathName;
	this.pathName = pathName;
	this.exists = (exists != 0);
	this.isSliced = (isSliced != 0);
	this.dataSize = dataSize;
	this.numSlices = numSlices;
    }
    public int compareTo(Object obj)
    {
	ItemDescriptor itm = (ItemDescriptor)obj;
	if(itm.dataSize > dataSize) return 1;
	if(itm.dataSize < dataSize) return -1;
	if(itm.exists && itm.dataSize > 0)
	    return itm.pathName.compareTo(pathName);
	return 0;
    }
    public boolean equals(Object obj)
    {
	if(!(obj instanceof ItemDescriptor)) return false;
	ItemDescriptor itm = (ItemDescriptor)obj;
	return itm.pathName.equals(pathName) && itm.cpoPathName.equals(cpoPathName);
    }
    void print()
    {
/*	if(!exists)
	    System.out.println(cpoPathName+":"+pathName+" Empty");
	else if(!isSliced)
	    System.out.println(pathName+" Size: "+ dataSize);
	else
	    System.out.println(pathName+" Size: "+ dataSize+ " Num Slices: " + numSlices);
*/    }
}
/////End static class ItemDescriptor

class CacheMonitorHandler extends Thread
{
    Socket sock;
    CacheMonitorHandler(Socket sock)
    {
	this.sock = sock;
    }
    public void run()
    {
	try {
	    DataInputStream dis = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
	    int nameLen = dis.readByte();
	    byte[]nameBuf = new byte[nameLen];
	    dis.read(nameBuf);
	    String name = new String(nameBuf);
	    while(true)
	    {
		int cpoPathLen = dis.readInt();
   		while(cpoPathLen == -1) //Marks the end of an update
		{
		    updateView(itmSet);
		    cpoPathLen = dis.readInt();
		    itmSet.clear();
		}
		byte [] cpoPathBuf = new byte[cpoPathLen];
		dis.read(cpoPathBuf);
		String cpoPath = new String(cpoPathBuf);
		int pathLen = dis.readInt();
		byte [] pathBuf = new byte[pathLen];
		dis.read(pathBuf);
		String path = new String(pathBuf);
		byte exists = dis.readByte();
		byte isSliced = dis.readByte();
		long dataSize = dis.readInt();
		int numSlices = dis.readInt();
		ItemDescriptor itm = new ItemDescriptor(cpoPath, path, exists, isSliced, dataSize, numSlices);
		itmSet.remove(itm);
		itmSet.add(itm);
	    }
	}catch(Exception exc) {System.out.println("Connection terminated");}
    }
}
//////////////End Class CacheMonitorHandler
class CacheMonitorViewer extends Component
{
    CacheMonitorViewer()
    {
	setPreferredSize(new Dimension(500, 2000));
    }
    public void paint(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        Dimension size = getSize();
	int nItems = itmSet.size() - 1;
	Iterator itmIter = itmSet.iterator();
	long maxDim = 1;
	long totDataSize = 0;
	for(int i = 0; i < nItems; i++)
	{
	    ItemDescriptor itmDescr = (ItemDescriptor)itmIter.next();
	    if(itmDescr.isSliced)
	        g2.drawString(itmDescr.cpoPathName+": "+itmDescr.pathName+"("+itmDescr.dataSize+")("+itmDescr.numSlices+" slices)", 0, i * ITEM_HEIGHT + 10);
	    else
	        g2.drawString(itmDescr.cpoPathName+": "+itmDescr.pathName+"("+itmDescr.dataSize+")", 0, i * ITEM_HEIGHT + 10);
	    if(i == 0)
	    {
	    	g2.fillRect(size.width / 2, (i*1) * ITEM_HEIGHT - 1, size.width / 2, ITEM_HEIGHT - 2); 
		maxDim = itmDescr.dataSize;
	    }
	    else
	    {
		double sizeFract = itmDescr.dataSize/(double)maxDim;
	    	g2.fillRect(size.width / 2, (i*1) * ITEM_HEIGHT - 1, (int)((size.width / 2)*sizeFract), ITEM_HEIGHT - 2); 
	    }
	    totDataSize += itmDescr.dataSize;
	}
	if(totDataSize > 2* 1024*1024)
	    totDataSizeLabel.setText("Total allocated cache memory: "+totDataSize/(1024*1024) + " MBytes");
	else if(totDataSize > 2 * 1024)
	    totDataSizeLabel.setText("Total allocated cache memory: "+totDataSize/(1024) + " KBytes");
	else
	    totDataSizeLabel.setText("Total allocated cache memory: "+totDataSize + " Bytes");
    }
}
/////////End class CacheMonitorViewer

    TreeSet<ItemDescriptor>  itmSet = new TreeSet<ItemDescriptor>();
    CacheMonitorViewer viewer;
    JLabel totDataSizeLabel;
    CacheMonitor()
    {
      	setPreferredSize(new Dimension(400, 300));
        setTitle("Cache Allocation Monitor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	getContentPane().add(new JScrollPane(new CacheMonitorViewer()), "Center");
	JPanel jp = new JPanel();
	getContentPane().add(jp, "South");
	jp.add(totDataSizeLabel = new JLabel());
	pack();
	setVisible(true);
    }

    void updateView(TreeSet itmSet)
    {
	Iterator itmIter = itmSet.iterator();
	while(itmIter.hasNext())
	{
	    ((ItemDescriptor)itmIter.next()).print();
	}
	repaint();

    }

    void startServer()
    {
	try {
	    ServerSocket serverS = new ServerSocket(4444);
	    while(true)
	    {
		Socket sock = serverS.accept();
		System.out.println("Connection received!!");
		(new CacheMonitorHandler(sock)).start();
 	    }
	}catch(Exception exc) {System.out.println("Error accepting connections " + exc);}
    }
    public static void main (String args[])
    {
	CacheMonitor cm = new CacheMonitor();
	cm.startServer();
    }
}	
