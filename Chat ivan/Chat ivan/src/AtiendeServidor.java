import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class AtiendeServidor extends Thread {

    private Socket sc;

    public AtiendeServidor(Socket sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        try {
            while (true) {
                DataInputStream in = new DataInputStream(sc.getInputStream());
                System.out.println(in.readUTF());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
