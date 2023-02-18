import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;;

public class Client{
    public static void main(String[] args){
        try {
            Socket s = new Socket("localhost",6722);
            System.out.println("Connected to Server.");
            Scanner sc = new Scanner(System.in);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            while(true){
                String msg = sc.nextLine();
                if(msg.equals("end")) {s.close();System.exit(0);}
                bw.write(msg);
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}