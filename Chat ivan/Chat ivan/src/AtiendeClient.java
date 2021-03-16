import java.io.*;
import java.net.*;

public class AtiendeClient extends Thread {

	private String usuario;
	private Socket client;
	private ComunHilos ch;

	public AtiendeClient(Socket client, ComunHilos ch) {
		this.usuario = "";
		this.client = client;
		this.ch = ch;
	}

	@Override
	public void run() {
		try {
			DataInputStream in = new DataInputStream(client.getInputStream());

			ch.anadirClient(client);

			ch.showHistory();

			usuario = in.readUTF();

			//nos creamos la estructura del texto que vamos a mandar para que sea siempre de la misma forma [nombreUsuario] mensaje
			//usamos StringBuilder ya que es mas optimo que el + ya que de este modo creamos un unico objeto en lugar de uno por cada +
			String mensajeEnviar = new StringBuilder().append("[").append(usuario).append("] ").append(in.readUTF()).toString();
			// contemplamos el caso del '*' para cerrar el socket en caso de que se introduzca
			while (!mensajeEnviar.equals("[" + usuario + "] " + "*")) {
				System.out.println(mensajeEnviar);
				ch.anadirMensaje(mensajeEnviar);
				mensajeEnviar = new StringBuilder().append("[").append(usuario).append("] ").append(in.readUTF()).toString();
			}

			ch.eliminarClient(client);
			System.out.println(usuario + " desconectado");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}