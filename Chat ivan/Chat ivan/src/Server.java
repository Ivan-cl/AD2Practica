import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException {

		// try-with-resource
		try (ServerSocket server = new ServerSocket(5050)) {

			ComunHilos ch = new ComunHilos();

			System.out.println("SERVIDOR INICIADO");

			while (true) {
					
				Socket client = server.accept();
				System.out.println("Se ha conectado un nuevo cliente al servidor");

				AtiendeClient clientThread = new AtiendeClient(client, ch);
				clientThread.start();
			}
		}
	}
}
