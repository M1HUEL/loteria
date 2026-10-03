package com.itson.loteria.vista;

import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.TipoMensaje;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DialogoGanador extends JDialog {

  public DialogoGanador(JFrame padre, TipoMensaje tipoMensaje, Jugador ganador, List<Jugador> jugadores) {
    super(padre, "Fin de la partida", true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    getRootPane().setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

    String motivo = tipoMensaje == TipoMensaje.VICTORIA ? "¡Lotería!" : "Se acabaron las cartas";
    JLabel lblMotivo = new JLabel(motivo);
    lblMotivo.setFont(lblMotivo.getFont().deriveFont(Font.BOLD, 16f));
    lblMotivo.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lblGanador = new JLabel("Ganó " + ganador.obtenerNombre());
    lblGanador.setFont(lblGanador.getFont().deriveFont(Font.BOLD, 20f));
    lblGanador.setAlignmentX(Component.CENTER_ALIGNMENT);

    add(lblMotivo);
    add(Box.createVerticalStrut(4));
    add(lblGanador);
    add(Box.createVerticalStrut(12));

    for (Jugador jugador : jugadores) {
      if (jugador == ganador) {
        continue;
      }
      JLabel lblPuntaje = new JLabel(jugador.obtenerNombre() + " = " + jugador.obtenerPuntaje());
      lblPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(lblPuntaje);
    }

    JButton botonSalir = new JButton("Salir");
    botonSalir.addActionListener(evento -> seleccionarSalir());
    botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

    add(Box.createVerticalStrut(12));
    add(botonSalir);

    pack();
    setLocationRelativeTo(padre);
  }

  public void mostrarDialogo() {
    setVisible(true);
  }

  public void seleccionarSalir() {
    System.exit(0);
  }
}
