

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;
 

 class Server_Client
{
 
  
    public static void main(String[] args)
 {
        int port = Integer.parseInt(args[0]);
        Scanner scanner = new Scanner(System.in);
        ServerSocket serverSocket = null;
        String str = null;
        String st = null;
         String path = null;
         FileInputStream fin = null;
         OutputStream os = null;
         int x= 0;
        try {
            //Creates a new server socket with the given port number
             serverSocket = new ServerSocket(port);
             } catch (IOException ex) 
             {
                 System.out.println("Error occured while creating the server socket");
                 return;
              }
 
        Socket socket = null;
         Socket sock = null;
        try
        {          
                  System.out.println("Waiting for client on port " +serverSocket.getLocalPort() + "...");
                  socket = serverSocket.accept();
                 
        } 
	catch (IOException ex) 
	{
            		System.out.println("Error occured while accepting the socket");
   	                   return;
        }
     
       		 System.out.println("Just connected to " + socket.getRemoteSocketAddress());
     		   ObjectInputStream in = null;
   		     ObjectOutputStream out = null;
        while (true) 
        {
               try 
              {
                  if (in == null) 
                  {
                    in = new ObjectInputStream(socket.getInputStream());
                  }
                     Message message = (Message) in.readObject();
                   System.out.println("client said-----"+ message.showMessage());   
                    x=0;                
               
                     
                         if(message.showMessage().equals("send file"))
                    
                         {
                                System.out.println("Enter a replaid: --");
                                String sr = scanner.nextLine();
                        if(sr.equals("ok"))
                        {
                        out.writeObject(new Message(sr));
                        out.flush();                       
                        sock = serverSocket.accept();   
                         
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
                                                 sock = serverSocket.accept();                        
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
                    
                    
                           if (out == null) 
                { 
                    out = new ObjectOutputStream(socket.getOutputStream());	
                }
                
               if(x==0)
               {             
         
                 	System.out.println("Enter a message");
                        str = scanner.nextLine();	
                       out.writeObject(new Message(str));
                        out.flush();
               }
                     
               
                    
                    
               
                    
               
 
                
                
            } catch (Exception ex) 
            {
                System.out.println("Error" + ex);
            }
        }
    }
}