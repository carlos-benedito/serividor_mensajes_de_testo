import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class HiloCliente extends Thread {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public HiloCliente(Socket socket) {
        this.socket = socket;
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String mensaje = null;
                try {
                    mensaje = dis.readUTF();
                } catch (EOFException eof) {
                    // Manejar la desconexión del cliente
                    System.out.println("Cliente " + socket.getInetAddress().getHostName() + " desconectado.");
                    break; // Salir del bucle al detectar EOF
                }

                // Leemos el mensaje y lo ponemos por consola
                System.out.println("Mensaje de " + socket.getInetAddress().getHostName() + " recibido: " + mensaje);

                // Enviar el mensaje a todos los clientes conectados
                enviarMensajeATodos(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            desconectarCliente();
        }
    }

    private void enviarMensajeATodos(String mensaje) {
        // Enviar el mensaje a todos los clientes conectados
        for (HiloCliente cliente : MainServidor.clientesConectados) {
            try {
                if (!cliente.socket.equals(this.socket)) {
                    cliente.dos.writeUTF(mensaje);
                }
            } catch (IOException e) {
                // Manejar errores de envío (puedes desconectar al cliente si ocurre un error)
                System.out.println("Error al enviar mensaje a un cliente.");
                cliente.desconectarCliente();
            }
        }
    }

    public void desconectarCliente() {
        // Método para desconectar el cliente
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Eliminar el hilo del cliente de la lista
        MainServidor.clientesConectados.remove(this);
    }
}

