
    
import java.io.*;


public class Message implements Serializable
{
 
    private String message;
 
    public Message(String message) {
        this.message = message;
    }
 
    public String showMessage(){
        return message;
    }
}
    

