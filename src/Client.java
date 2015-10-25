import java.io.*;
import java.net.Socket;

/**
 * Created by Admin on 17.09.2015.
 */
public class Client extends Thread{
    public static void main(String args[])
    {
        try
        {
            // открываем сокет и коннектимся к localhost:3128
            // получаем сокет сервера
            Socket s = new Socket("localhost", 3128);
            System.out.println("Client started");

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            try {
                Matrix matr1 = Matrix.loadMatrixFromFile(args[0]);

                dos.writeInt(matr1.getHeight());
                dos.writeInt(matr1.getWidth());
                for (int i = 0; i < matr1.getHeight(); i++) {
                    for (int k = 0; k < matr1.getWidth(); k++) {
                        dos.writeDouble(matr1.getElementAt(i, k));
                    }
                }
                System.out.println("First matrix sent");
            }catch (FileNotFoundException e){
                System.out.println("File not found: "+ args[0]);
                s.close();
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("No element with such index");
                s.close();
            }


            try{
                Matrix matr2 = Matrix.loadMatrixFromFile(args[1]);

                dos.writeInt(matr2.getHeight());
                dos.writeInt(matr2.getWidth());
                for (int i = 0; i < matr2.getHeight(); i++) {
                    for (int k = 0; k < matr2.getWidth(); k++) {
                        dos.writeDouble(matr2.getElementAt(i, k));
                    }
                }
                System.out.println("Second matrix sent");
            }
            catch (FileNotFoundException e){
                System.out.println("File not found: "+ args[1]);
                s.close();
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("No element with such index");
                s.close();
            }

                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

             try {
                 Matrix matr3 = (Matrix) ois.readObject();
                 Matrix.writeMatrixToFile(args[2], matr3);
             }catch (StreamCorruptedException e){
                 DataInputStream dis = new DataInputStream(s.getInputStream());
                 FileWriter fw = new FileWriter(new File(args[2]));
                 fw.write(dis.readUTF());
                 dis.close();
                 fw.close();
             }
            dos.close();
            ois.close();
            System.out.println("No errors");
        }
        catch(Exception e)
        {System.out.println("init error: "+e);} // вывод исключений
    }



}
