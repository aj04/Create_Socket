
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import  javax.net.ssl.X509TrustManager;

public class Client {

	 
	 public static void main(String[] args) {
				//001932814
		if (args.length < 3){
			System.out.println("insufficient arguements");
			return;
		}
		int port = Integer.parseInt(args[0]);
		String host = args[1];
		String NEU_ID = args[2];
		String S = new String("cs5700spring2015 HELLO "+NEU_ID+ "\n");
		
		try{
			//host = "cs5700sp15.ccs.neu.edu";
			int sol=0;
			{
				InetAddress address = InetAddress.getByName(host);
				

				if (port == 27994){
					//System.out.println("SSL implementation");
					
					try {
					    // Creating a trust manager which will not validate certificate chains
					    final TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
					      
					        @Override
					        public void checkServerTrusted( final X509Certificate[] chainKey, final String auth ) {
					        }
					        @Override
					        public void checkClientTrusted( final X509Certificate[] chainKey, final String auth ) {
					        }
					        @Override
					        public X509Certificate[] getAcceptedIssuers() {
					            return null;
					        }
					    }
					   };
					 // Installing the all-trusting trust manager
					    final SSLContext sslContext = SSLContext.getInstance( "SSL" );
					    sslContext.init( null, trustAllCertificates, new java.security.SecureRandom() );
					    // Create a sslsocketfactory object with our all-trusting manager
					    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
					
					//InetAddress address = InetAddress.getByName(host);
					//SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();
					SSLSocket sslsocket = (SSLSocket) sslSocketFactory.createSocket(address, port);
					
					java.io.OutputStream os = sslsocket.getOutputStream();
		            OutputStreamWriter osw = new OutputStreamWriter(os);
		            BufferedWriter bw = new BufferedWriter(osw);

		            bw.write(S);
		            bw.flush();
		            
		            //      System.out.println(S);
			         /* Get the return message from the server */
			            InputStream is = sslsocket.getInputStream();
			            InputStreamReader isr = new InputStreamReader(is);
			            BufferedReader br = new BufferedReader(isr);
			            String message = br.readLine();
			         //System.out.println("Message received from the SSL server : " +message);
			         
			         Scanner scanner = new Scanner(message);
						String[] V = new String[5];
										
						for (int i=0 ; i<5 ; i++){
							V[i] = scanner.next();
						}
			            
						while (V[1].equals("STATUS")){
						        sol = Calc(message);
						//        System.out.println("sum ="+ sol);
						        S="cs5700spring2015 " + sol+"\n";
						       //System.out.println(S);
						        bw.write(S);
					            bw.flush();
					            message = br.readLine();
					         //System.out.println("Message received from the server : " +message);
					            Scanner scanner1 = new Scanner(message);
					            int count =0;
					            LinkedList<String> tokens = new LinkedList<String>();
					            while (scanner1.hasNext()){
					            	tokens.add(scanner1.next());
					            	count++;
					            }
					          //System.out.println("number of strings in received msg :"+ count);
					            scanner1.close();
					            Scanner scanner2 = new Scanner (message);
					            V = new String[5];
															
												
								for (int i=0 ; i<count ; i++){
									//{if (V[i].equals("BYE")) break;
									V[i] = scanner2.next();
								}
								scanner2.close();
						}
						
						if (V[2].equals("BYE")) { 
							System.out.println(V[1]);
						}
			                     
			            scanner.close();

			         
			         sslsocket.close();
					} catch ( final Exception e ) {
					    e.printStackTrace();
					}	
				}
				
				else {
				Socket socket = new Socket(address, port);
			 /* Send the message to the server  */
	            java.io.OutputStream os = socket.getOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(os);
	            BufferedWriter bw = new BufferedWriter(osw);

	            bw.write(S);
	            bw.flush();
	      //      System.out.println(S);
	         /* Get the return message from the server */
	            InputStream is = socket.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String message = br.readLine();
	        //    System.out.println("Message received from the server : " +message);
	            
	            Scanner scanner = new Scanner(message);
				String[] V = new String[5];
								
				for (int i=0 ; i<5 ; i++){
					V[i] = scanner.next();
				}
	            
				while (V[1].equals("STATUS")){
				        sol = Calc(message);
				//        System.out.println("sum ="+ sol);
				        S="cs5700spring2015 " + sol+"\n";
				       // System.out.println(S);
				        bw.write(S);
			            bw.flush();
			            message = br.readLine();
			      //      System.out.println("Message received from the server : " +message);
			            Scanner scanner1 = new Scanner(message);
			            int count =0;
			            LinkedList<String> tokens = new LinkedList<String>();
			            while (scanner1.hasNext()){
			            	tokens.add(scanner1.next());
			            	count++;
			            }
			        //    System.out.println("number of strings in received msg :"+ count);
			            scanner1.close();
			            Scanner scanner2 = new Scanner (message);
			            V = new String[5];
													
										
						for (int i=0 ; i<count ; i++){
							//{if (V[i].equals("BYE")) break;
							V[i] = scanner2.next();
						}
						scanner2.close();
				}
				
				if (V[2].equals("BYE")) { 
					System.out.println(V[1]);
				}
	                     
	            scanner.close();
	            socket.close();
	        }
		   }		
		}
			
			catch (UnknownHostException e) {
                System.out.println("Invalid Arguements");
				//e.printStackTrace();
            } 
		    catch (SocketException e) {
		    	System.out.println("Connection reset");
		    	e.printStackTrace();
		    }
			catch (IOException e) {
				System.out.println("Improper input");
                e.printStackTrace();
			}
	        catch (Exception exception) 
	        {
	            exception.printStackTrace();
	        }
	        finally
	        {
	            //Closing the socket
	            try
	            {
	               //System.out.println(" ");
	            	 //socket.close();
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
	            }
	        } 
		}
			

			public static int Calc(String Str){
				Scanner scanner = new Scanner(Str);
				String[] T = new String[5];
				
				
				for (int i=0 ; i<5 ; i++){
					T[i] = scanner.next();
				}
				//System.out.println(T[2]);
				//System.out.println(T[4]);
				scanner.close();
				int sum = -1;
				//if (T[2].equals("BYE")) System.out.println("The secret flag is"+ T[1]); 
				if (T[3].equals("*")){ sum = ( Integer.valueOf(T[2]) * Integer.valueOf(T[4]));return sum; } 
				if (T[3].equals("+")){ sum = ( Integer.valueOf(T[2]) + Integer.valueOf(T[4]));return sum; }
				if (T[3].equals("-")){ sum = ( Integer.valueOf(T[2]) - Integer.valueOf(T[4]));return sum; }			 
				if (T[3].equals("/")){ sum = (Integer.valueOf(T[2]) / Integer.valueOf(T[4]));return sum; }
				
				return sum;
				
				}
		
			
}
				
	

