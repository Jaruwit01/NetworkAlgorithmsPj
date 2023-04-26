import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
    public static void main(String[] args){
        final String Host = "192.168.1.106";
        final int PUERTO = 5000;
        DataInputStream input;
        DataOutputStream output;
        try{
            Socket sc  = new Socket(Host, PUERTO);
            input = new DataInputStream(sc.getInputStream());
            output = new DataOutputStream(sc.getOutputStream());

            output.writeUTF("Hello from client");

            String message = input.readUTF();
            System.out.println(message);

            sc.close();

        }catch(IOException ex){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
