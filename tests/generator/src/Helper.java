import java.util.Arrays;
import java.util.Random;
import imasjava.*;


public class Helper {
    private static final int TESTSHOT = 9999;
    private static final int TESTRUN = 9999;
    private static final Random RANDOM = new Random();
    private static final String PRINTABLE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~\t\n\r";
    private static int idx;
    private static long seed = RANDOM.nextInt();
    private enum Types { DOUBLE, INTEGER, STRING, COMPLEX };



    private static String userName = null;
    private static String dataVersion = null;
    private static String tokamak = "test";
    
    static String getUserName() throws Exception
    { 
        if(userName != null) 
            return userName;
        
        userName = System.getenv("USER");
        if(userName == null) 
            throw new Exception( "PANIC: $USER not found! Exiting...");
        
    
        return userName;
    }
    
    
    static String getDataVersion()  throws Exception
    { 
        if(dataVersion != null) 
            return dataVersion;
        
        dataVersion = System.getenv("IMAS_VERSION");
        if(dataVersion == null) 
            throw new Exception( "PANIC: $IMAS_VERSION not found! Exiting...");
    
        return dataVersion;
    }
    
    
    static String getTokamak() 
    { 
    
        return tokamak;
    }


    public static int initPut() throws ALException, Exception
    {
        String user = Helper.getUserName();
        String tokamak = Helper.getTokamak(); 
        String version = Helper.getDataVersion();
        
        idx = imas.createEnv(TESTSHOT, TESTRUN, user, tokamak, version);
        
        return idx;
    }

    public static int initGet() throws ALException , Exception
    {
        String user = Helper.getUserName();
        String tokamak = Helper.getTokamak(); 
        String version =Helper.getDataVersion();
        idx = imas.openEnv(TESTSHOT, TESTRUN, user, tokamak, version);

        return idx;
    }

    public static void finish(int idx) throws ALException {
        imas.close(idx);
    }

}