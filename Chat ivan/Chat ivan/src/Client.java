import java.io.DataOutputStream;
import java.util.*;
import java.io.IOException;
import java.net.Socket;

public class Client {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {

		// try-with-resource
		try (Socket socket = new Socket("localhost", 5050)) {

			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			System.out.println("Nombre de usuario?");
			String usuario = sc.nextLine();
			output.writeUTF(usuario);

			AtiendeServidor threadServer = new AtiendeServidor(socket);
			threadServer.start();

			String msg = sc.nextLine();

			while (!msg.equals("*")) {
				output.writeUTF(msg);
				msg = sc.nextLine();
			}

			output.writeUTF(msg);
			System.out.println("Se ha desconectado del chat");
		}
	}
}