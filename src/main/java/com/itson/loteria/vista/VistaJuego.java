package com.itson.loteria.vista;

import com.itson.loteria.controlador.ControladorJuego;
import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.Modelo;
import com.itson.loteria.modelo.Observador;
import com.itson.loteria.modelo.Patron;
import com.itson.loteria.modelo.Tablero;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VistaJuego extends JFrame implements Observador {

  private static final int FILAS_TABLERO = 4;
  private static final int COLUMNAS_TABLERO = 4;

  private final ControladorJuego controlador;
  private final JPanel panelJugadores;
  private final JPanel panelPuntajes;
  private final JLabel lblCartaGritada;
  private final JLabel lblCartasRestantes;
  private final JLabel lblPatronSeleccionado;
  private final List<List<JButton>> botonesCasillas = new ArrayList<>();
  private final List<JLabel> lblPuntajes = new ArrayList<>();
  private Patron patronAvisado;
  private boolean victoriaAvisada;

  public VistaJuego(Modelo modelo, ControladorJuego controlador) {
    this.controlador = controlador;

    setTitle("Lotería");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    panelJugadores = new JPanel();
    panelJugadores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelJugadores.setLayout(new GridLayout(1, modelo.obtenerJugadores().size()));
    for (int i = 0; i < modelo.obtenerJugadores().size(); i++) {
      final int numero = i;
      Jugador jugador = modelo.obtenerJugadores().get(numero);
      JPanel panelJugador = new JPanel(new BorderLayout());
      panelJugador.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
      panelJugador.add(new JLabel(jugador.obtenerNombre()), BorderLayout.NORTH);
      JPanel tablero = new JPanel(new GridLayout(FILAS_TABLERO, COLUMNAS_TABLERO));
      List<JButton> botones = new ArrayList<>();
      for (int f = 0; f < FILAS_TABLERO; f++) {
        final int fila = f;
        for (int c = 0; c < COLUMNAS_TABLERO; c++) {
          final int columna = c;
          JButton casilla = new JButton(jugador.obtenerTablero().obtenerCarta(fila, columna).obtenerNombre());
          casilla.addActionListener(evento -> seleccionarCasilla(numero, fila, columna));
          tablero.add(casilla);
          botones.add(casilla);
        }
      }
      this.botonesCasillas.add(botones);
      panelJugador.add(tablero, BorderLayout.CENTER);
      panelJugadores.add(panelJugador);
    }
    add(panelJugadores, BorderLayout.CENTER);

    panelPuntajes = new JPanel();
    panelPuntajes.setLayout(new BoxLayout(panelPuntajes, BoxLayout.Y_AXIS));
    for (int i = 0; i < modelo.obtenerJugadores().size(); i++) {
      JLabel lblPuntaje = new JLabel();
      this.lblPuntajes.add(lblPuntaje);
      panelPuntajes.add(lblPuntaje);
    }

    lblCartaGritada = new JLabel();
    lblCartasRestantes = new JLabel();
    lblPatronSeleccionado = new JLabel();

    JPanel panelLateral = new JPanel();
    panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
    panelLateral.add(lblCartaGritada);
    panelLateral.add(lblCartasRestantes);
    panelLateral.add(lblPatronSeleccionado);

    for (Patron patron : Patron.values()) {
      JButton botonPatron = new JButton(patron.obtenerNombre());
      botonPatron.addActionListener(evento -> seleccionarPatron(patron));
      panelLateral.add(botonPatron);
    }
    JButton botonVictoria = new JButton("Cantar victoria");
    botonVictoria.addActionListener(evento -> seleccionarVictoria());
    panelLateral.add(botonVictoria);
    panelLateral.add(panelPuntajes);

    JButton botonSalir = new JButton("Salir");
    botonSalir.addActionListener(evento -> System.exit(0));
    panelLateral.add(botonSalir);

    add(panelLateral, BorderLayout.EAST);

    setSize(1200, 500);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void seleccionarCasilla(int jugador, int fila, int columna) {
    this.controlador.seleccionarCasilla(jugador, fila, columna);
  }

  private void seleccionarPatron(Patron patron) {
    this.controlador.seleccionarPatron(patron);
  }

  private void seleccionarVictoria() {
    this.controlador.seleccionarVictoria();
  }

  @Override
  public void actualizar(Modelo modelo) {
    lblCartaGritada.setText("Carta gritada: " + (modelo.obtenerCartaActual() == null ? "-" : modelo.obtenerCartaActual().obtenerNombre()));
    lblCartasRestantes.setText("Cartas restantes: " + modelo.obtenerCartasRestantes());
    lblPatronSeleccionado.setText("Patrón: " + (modelo.obtenerPatronSeleccionado() == null ? "-" : modelo.obtenerPatronSeleccionado().obtenerNombre()));

    for (int i = 0; i < modelo.obtenerJugadores().size(); i++) {
      Jugador jugador = modelo.obtenerJugadores().get(i);
      Tablero tableroJugador = jugador.obtenerTablero();
      for (int f = 0; f < FILAS_TABLERO; f++) {
        for (int c = 0; c < COLUMNAS_TABLERO; c++) {
          this.botonesCasillas.get(i).get(f * COLUMNAS_TABLERO + c).setBackground(tableroJugador.obtenerMarcada(f, c) ? Color.RED : Color.WHITE);
        }
      }
      this.lblPuntajes.get(i).setText(jugador.obtenerNombre() + " = " + jugador.obtenerPuntaje());
    }

    panelPuntajes.revalidate();
    panelPuntajes.repaint();
    if (!modelo.obtenerCasillaValida()) {
      mostrarMensajeCasillaInvalido();
    }

    if (modelo.obtenerPatronSeleccionado() != null && modelo.obtenerPatronSeleccionado() != patronAvisado) {
      patronAvisado = modelo.obtenerPatronSeleccionado();
      mostrarMensajePatronValido(patronAvisado);
    } else if (!modelo.obtenerPatronValido()) {
      mostrarMensajePatronInvalido();
    }

    if (modelo.obtenerVictoria() && !victoriaAvisada) {
      victoriaAvisada = true;
      mostrarMensajeVictoria();
    } else if (!modelo.obtenerVictoriaValida()) {
      mostrarMensajeVictoriaInvalido();
    }
  }

  public void mostrarMensajePatronValido(Patron patron) {
    JOptionPane.showMessageDialog(this, "El patrón " + patron.obtenerNombre() + " fue seleccionado correctamente");
  }

  public void mostrarMensajePatronInvalido() {
    JOptionPane.showMessageDialog(this, "El patrón no es válido");
  }

  public void mostrarMensajeVictoria() {
    JOptionPane.showMessageDialog(this, "¡Lotería! Se cantó victoria");
  }

  public void mostrarMensajeVictoriaInvalido() {
    JOptionPane.showMessageDialog(this, "No se puede cantar victoria");
  }

  public void mostrarMensajeCasillaInvalido() {
    JOptionPane.showMessageDialog(this, "La casilla no se puede seleccionar");
  }
}
