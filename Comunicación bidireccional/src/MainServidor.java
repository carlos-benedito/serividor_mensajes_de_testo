import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainServidor {
	
	public static List<HiloCliente> clientesConectados = new ArrayList<>();

	public static void main(String[] args) {
		
	

		try (ServerSocket serverSocket = new ServerSocket(6565)) {

			while (true) {
				// accept() es bloqueante
				Socket socketCliente = serverSocket.accept();
				
				HiloCliente hiloCliente = new HiloCliente(socketCliente);
				
				clientesConectados.add(hiloCliente);
				
				hiloCliente.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
