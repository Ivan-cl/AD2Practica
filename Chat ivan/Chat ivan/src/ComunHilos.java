import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ComunHilos {

    private ArrayList<String> historialDeMensajes = new ArrayList<>();
    private ArrayList<Socket> clientes = new ArrayList<>();

    // metodo para añadir a cola cada uno de los mensajes que llegan de cualquiera de los sockets conectados
    public void anadirMensaje(String mensaje) throws IOException {
        historialDeMensajes.add(mensaje);

        for (Socket sc : clientes) {
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(mensaje);
        }
    }

    //metodo mediante el cual enviamos a cada uno de los sockets conectados la lista de mensajes que tenemos guardada
    public void showHistory() throws IOException {
        for (Socket socket : clientes) {
            for (String mensaje : historialDeMensajes) {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(mensaje);
            }
        }
    }

    //metodo para añadir a la lista de clientes uno nuevo
    public synchronized void anadirClient(Socket cliente) throws IOException {

        DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

        //comprobamos que no se ha llegado al maximo de clientes si es asi entra en el while y se queda a la espera de que quede un hueco libre
        while (clientes.size() == 10) {
            try {
                out.writeUTF("Se ha alcanzado el numero maximo de conexiones se encuentra en cola a la espera de que se pueda conectar");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }

        clientes.add(cliente);
        out.writeUTF("Se ha unido con exito al chat bienvenido");
        notifyAll();
    }

    public synchronized void eliminarClient(Socket client) {
        clientes.remove(client);
        notifyAll();
    }
}