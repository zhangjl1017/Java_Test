// �ļ��� GreetingClient.java
// java GreetingClient localhost 6066

import java.net.*;
import java.io.*;
import java.util.Scanner;
 
public class GreetingClient
{
   public static void main(String [] args)
   {
      String serverName = "localhost"; // args[0];
      int port = 6066;  // Integer.parseInt(args[1]);
      try
      {
         System.out.println("Connecting to " + serverName
                             + " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to "
                      + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
		 Scanner scanner=new Scanner(System.in);
		 while(true){
			 String str=scanner.next();
			 out.writeUTF(str);
			 InputStream inFromServer = client.getInputStream();
			 DataInputStream in = new DataInputStream(inFromServer);
             System.out.println("Server says " + in.readUTF());
		 }
		 //client.close();
         
      }catch(IOException e)
      {
         e.printStackTrace();
      }finally{
		
	  }

   }
}