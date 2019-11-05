

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java .io.*;
import javax.swing.*;

public class Client 
{
         public static void main(String[] args)
         {
               String serverName = args[0];
       	       int port = Integer.parseInt(args[1]);
               int Port_Number = 0;
               Scanner scanner = new Scanner(System.in);   //to read text from the console
               Socket socket = null;
               Socket sock = null;
               DataInputStream i = null;
               DataOutputStream outt = null;
               FileInputStream fin = null;
               OutputStream os = null;
               int x= 0;
               
               ObjectInputStream in = null;
               ObjectOutputStream out = null;
               String path = null;
               
               try 
               {
          	  socket = new Socket(serverName , port);          
        	  System.out.println("Connecting to " + serverName + " on port " + port);
                  System.out.println("Just connected to " +   socket.getRemoteSocketAddress());	
               } 
            catch (Exception ex) 
               {
                 System.out.println("Error connecting to server" + ex.getMessage());
                
                }
               
               try
               {

                    i = new DataInputStream(socket.getInputStream());            
                    //System.out.println(in.readUTF());
                    String port_number = i.readUTF();
                    System.out.println(port_number);
                 
                     Port_Number = Integer.parseInt(port_number);
                     System.out.println(Port_Number);
             
               }               
               catch (Exception e) 
               {
                   System.out.println(e.toString());
                   
               }
               
               socket = null;
               i = null;
               String str =null;
               
               
                try 
                {
                    
          	   socket = new Socket(serverName , Port_Number);   
                  
        	   System.out.println("Connecting to " + serverName + " on port " + Port_Number);
                   System.out.println("Just connected to " +   socket.getRemoteSocketAddress());	
                  } 
                catch (Exception ex) 
                {
    	          System.out.println("Error connecting to server" + ex.getMessage());
                }
                
                
              while (true) 
             {
               try
               {        	        	

                if (out == null)
                   {
                     out = new ObjectOutputStream(socket.getOutputStream());
                   }
 
                //read a string
                if(x==0)
                {
                
                System.out.println("Enter a message: --");
                str = scanner.nextLine();
                //send it to server
                out.writeObject(new Message(str));
                out.flush();
                }
 
                //get the reply from the server
                if (in == null) 
                {
                    in = new ObjectInputStream(socket.getInputStream());
                }
                Message message = (Message) in.readObject();
                System.out.println("Server said-----"+ message.showMessage());
                x=0;
                
                if(message.showMessage().equals("send file"))
                    
                {
                    System.out.println("Enter a replaid: --");
                    str = scanner.nextLine();
                        
                    if(str.equals("ok"))
                    {
                         sock = new Socket(serverName , Port_Number);  
                         out.writeObject(new Message(str));
                         out.flush();
                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileSelectionMode	(JFileChooser.FILES_AND_DIRECTORIES);
                        int returnVal = chooser.showOpenDialog(null);
                        if(returnVal == JFileChooser.APPROVE_OPTION) 
                        {			
                            path = chooser.getSelectedFile().getAbsolutePath();
                            System.out.println("You chose to open this directory: " +
                             chooser.getSelectedFile().getAbsolutePath());
                        }  
                        
                          File file = new File(path);
                          fin = new FileInputStream(file);
                          os = sock.getOutputStream();
                          
                          BufferedInputStream bis = new BufferedInputStream(fin);
                           byte[] contents;
                           long fileLength = file.length(); 
                           long current = 0;                            
                                      while(current!=fileLength)
                                      { 
                                           int size = 10000;
                                           if(fileLength - current >= size)
                                             current += size;    
                                               else{ 
                                                      size = (int)(fileLength - current); 
                                                       current = fileLength;
                                                    } 
                                            contents = new byte[size]; 
                                            bis.read(contents, 0, size); 
                                            os.write(contents);
            //System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
                                      }   
        
                                 os.flush(); 
                                 sock.close();                                                
                                 System.out.println("File sent succesfully!");
                                 x=1;
                    }
                    
                }   
                
                
                if(message.showMessage().equals("ok"))
                    {
                                                 sock = new Socket(serverName , Port_Number);                  
                                   		 FileOutputStream fout = new FileOutputStream(new File("D://client"));
                                                  BufferedOutputStream bos = new BufferedOutputStream(fout);
                                                  InputStream is = sock.getInputStream();
                                                  byte[] contents = new byte[10000];
                                                
        //No of bytes read in one read() call
                                   int bytesRead = 0; 
        
                                 while((bytesRead=is.read(contents))!=-1)
                                          bos.write(contents, 0, bytesRead); 
        
                            bos.flush(); 
                            sock.close(); 
        
                          System.out.println("File saved successfully!");
                                           
                    }
                
                
 
                  } catch (Exception ex) 
                     {
                        System.out.println("&quot;Error: &quot; "+ ex);
                     }
               }
              
              
            
             
             
         }
    
    
    
}
