package Ventana;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Ventana extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextArea mensajes = new JTextArea();
    private JTextField prompt = new JTextField();
    private JButton boton = new JButton();
    private DataOutputStream dos; // Agregamos esta variable

    public Ventana() {
        setLayout(new BorderLayout());

        mensajes.setPreferredSize(new Dimension(400, 200));
        prompt.setPreferredSize(new Dimension(200, 30));
        boton.setPreferredSize(new Dimension(20, 30));
        prompt.setToolTipText("Escribe aquí tu mensaje...");
        boton.setText("Enviar");
        boton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                enviar();
            }
        });

        add(new JScrollPane(mensajes), BorderLayout.NORTH);
        add(prompt, BorderLayout.CENTER);
        add(boton, BorderLayout.SOUTH);

        pack();

        setTitle("Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setOutputStream(DataOutputStream dos) {
        this.dos = dos;
    }

    public void enviar() {
        String mensaje = prompt.getText();

        if (!mensaje.isEmpty()) { // Verificación de mensaje no vacío
            mensajes.append("Yo: " + mensaje + "\n");
            prompt.setText("");

            try {
                if (dos != null) {
                    dos.writeUTF(mensaje);
                    dos.flush(); // Asegurarse de que los datos se envíen inmediatamente
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public void actualizarMensaje(String mensaje) {
		// TODO Auto-generated method stub
		
	}
}
