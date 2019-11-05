import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.DataOutputStream;
import java.io.*;

public class Server_X_Client 
{

    public static void main(String[] args)
    {
        int count = 1000;
        int a = Integer.parseInt(args[0]);
        Socket s = null;
        ServerSocket ss2 = null;
        System.out.println("Server is Listing-----");
        try{
            ss2 = new ServerSocket(a);
            
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
        DataOutputStream out = null;

        while (true) 
        {
            try {
                s=ss2.accept();
	System.out.println("connecting---");
                
            } catch (IOException e) 
            {
                System.out.println(e.toString());
            }           
            String port = String.valueOf(count);
            count = count+1;
            
            try {
                 if (out == null) 
           {

                out = new DataOutputStream(s.getOutputStream());
                out.writeUTF(port);
                System.out.println("transfer---");
	
          }
            } 
            catch (Exception e) 
            {
                System.out.println(e.toString());
                
            }
            
           out = null;
           
           
           
         try 
         {           
           String[] command = {"cmd.exe" , "/c", "start" , "cmd.exe" , "/k" , "\" java Server_Client  \"" , port };
           Runtime runtime = Runtime.getRuntime();
           Process proc = runtime.exec(command);
           
        } 
        catch (Exception e)
        {
            System.out.println(e.toString()); 
        }     
                      
        }
  
    }
    
}


