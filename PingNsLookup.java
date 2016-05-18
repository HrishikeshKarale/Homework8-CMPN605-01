/**
 * PingNsLookup.java
 * @author Hrishikesh Karale
 * @id hhk9433
 * @date 05/13/2015
 * @version v1.2
 * 
 * Takes a commandLine parameter as ip address. 
 * > javac PingNsLookup.java
 * > java PingNsLookup yahoo.com
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class pings the ip provided at commandline and shows its result for
 * nslookup.
 *
 */
public class PingNsLookup {
	private Runtime runtimeObject= null;
	private Process processObject= null;
	private BufferedReader buffRdrObject= null;
	
	/**
	 * This is the Parameterized Constructor for this class. It takes
	 * two parameters whic hare then used as parameters for calling nsping()
	 * 
	 * @param ping
	 * @param nslookup
	 */
	PingNsLookup(String ping, String nslookup) {
		System.out.println("*******NSLOOKUP*******");
		nsPing(nslookup);
		System.out.println("\n\n*******PING*******");
		nsPing(ping);
	}
	
	
	
	/**
	 * This is the nsping() which takes in a String parameter which is the 
	 * command to be executed. This method executes the string command at 
	 * runtime and stores its result and displayes its result.
	 * @param nsLookup
	 */
	private void nsPing(String nsLookup) {
		try {
			//creates a Runtime object which is initialized to an instance of runtime
            Runtime r = Runtime.getRuntime();
            //executes the String command and stores it in a process.
            Process p = r.exec(nsLookup);

            // buffered reader object initialized by inputstream from process
            buffRdrObject = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine, Result= null;
            while ((inputLine = buffRdrObject.readLine()) != null) {
                System.out.println(inputLine);
                Result += inputLine;
            }
            buffRdrObject.close();

        } catch (IOException e) {
            System.out.println(e);
        }
	}
	
	/**
	 * This is the main method of our class. This checks for presence of a command
	 * line parameter and if a command line parameter is found then class object 
	 * is created with commands as parameters.
	 * @param args
	 */
	public static void main(String[] args) {
		//check if a commandline parameter is entered.
		if(args.length==1){
			//command for nsLookup
	        String nsLookup= "nslookup " + args[0];
	        //command for ping
	        String pingCmd = "ping " + args[0];
	        
	        //create class object and send both commands as parameters.
	        PingNsLookup classObject= new PingNsLookup(pingCmd, nsLookup);
		}
		//if command line parameter not entered correctly exit.
		else{
			System.out.println("Enter Command Line Parameter.\n Ex: java PingNsLookup yahoo.com");
		}
    }
}
