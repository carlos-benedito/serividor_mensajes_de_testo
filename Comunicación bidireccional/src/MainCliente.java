import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MainCliente {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("192.168.128.140", 6565);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            // Hilo para recibir mensajes del servidor
            Thread recibirMensajes = new Thread(() -> {
                try {
                    while (true) {
                        String mensaje = dis.readUTF();
                        System.out.println("Aguado: " + mensaje);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            recibirMensajes.start();

            // Hilo para enviar mensajes al servidor
            Thread enviarMensajes = new Thread(() -> {
                try {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        System.out.print("Carlos: ");
                        String mensaje = scanner.nextLine();
                        dos.writeUTF(mensaje);
                        System.out.println("");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            enviarMensajes.start();

            // Esperar a que ambos hilos terminen
            recibirMensajes.join();
            enviarMensajes.join();

            // Cierre del socket
            socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
