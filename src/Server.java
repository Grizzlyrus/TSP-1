import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Admin on 17.09.2015.
 */
public class Server extends Thread{

    Socket s;
    int num;


    public static void main(String [] args){
        try
        {
            int i = 0;

            ServerSocket server = new ServerSocket(3128, 0,
                    InetAddress.getByName("localhost"));

            System.out.println("server is started");

            while(true)
            {
                new Server(i, server.accept());
                i++;
            }
        }
        catch(Exception e)
        {System.out.println("init error: "+e);}

    }

    public Server(int num, Socket s)
    {
        this.num = num;
        this.s = s;

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void run()
    {
        try
        {
            DataInputStream is = new DataInputStream(s.getInputStream());

            Matrix matr1 = new Matrix(is.readInt(),is.readInt());
            for(int i=0;i<matr1.getHeight();i++){
                for (int k=0;k<matr1.getWidth();k++){
                    matr1.editElementAt(i, k, is.readDouble());
                }
            }

            Matrix matr2 = new Matrix(is.readInt(),is.readInt());
            for(int i=0;i<matr2.getHeight();i++){
                for (int k=0;k<matr2.getWidth();k++){
                    matr2.editElementAt(i, k, is.readDouble());
                }
            }



            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            try {
                oos.writeObject(Matrix.SumMatrix(matr1, matr2));
            }catch (Exception e){
                System.out.println(e.getMessage());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeChars(e.getMessage());
                dos.close();
            }

            is.close();
            oos.close();
            s.close();
        }
        catch(Exception e)
        {System.out.println("init error: "+e);}
    }

}
