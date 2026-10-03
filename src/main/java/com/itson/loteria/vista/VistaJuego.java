package com.itson.loteria.vista;

import com.itson.loteria.controlador.ControladorJuego;
import com.itson.loteria.modelo.Carta;
import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.Modelo;
import com.itson.loteria.modelo.Observador;
import com.itson.loteria.modelo.Patron;
import com.itson.loteria.modelo.TipoMensaje;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VistaJuego extends JFrame implements Observador {

  private static final int ANCHO_VENTANA = 1000;
  private static final int ALTO_VENTANA = 520;

  private final Jugador jugador;
  private final ControladorJuego controlador;
  private final List<PanelJugador> panelesJugadores = new ArrayList<>();
  private final JPanel panelLateral;
  private final JLabel lblCartaGritada;
  private final JLabel lblCartasRestantes;
  private final JLabel lblPatronSeleccionado;
  private final JLabel lblPuntaje;
  private final JLabel lblGanador;
  private TipoMensaje tipoMensajeAvisado = TipoMensaje.NINGUNO;

  public VistaJuego(Modelo modelo, ControladorJuego controlador, Jugador jugador) {
    this.jugador = jugador;
    this.controlador = controlador;

    setTitle("Lotería - " + jugador.obtenerNombre());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel panelJugadores = new JPanel(new GridLayout(1, modelo.obtenerJugadores().size()));
    panelJugadores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    for (int i = 0; i < modelo.obtenerJugadores().size(); i++) {
      Jugador jugadorDelTablero = modelo.obtenerJugadores().get(i);
      PanelJugador panel = new PanelJugador(jugadorDelTablero, controlador, jugadorDelTablero == this.jugador);
      this.panelesJugadores.add(panel);
      panelJugadores.add(panel);
    }
    add(panelJugadores, BorderLayout.CENTER);

    lblCartaGritada = new JLabel();
    lblCartasRestantes = new JLabel();
    lblPatronSeleccionado = new JLabel();
    lblPuntaje = new JLabel();
    lblGanador = new JLabel();

    panelLateral = new JPanel();
    panelLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
    panelLateral.add(lblCartaGritada);
    panelLateral.add(lblCartasRestantes);
    panelLateral.add(lblPatronSeleccionado);
    panelLateral.add(lblPuntaje);
    panelLateral.add(lblGanador);

    for (Patron patron : Patron.values()) {
      JButton botonPatron = new JButton(patron.obtenerNombre());
      botonPatron.addActionListener(evento -> seleccionarPatron(patron));
      panelLateral.add(botonPatron);
    }

    JButton botonVictoria = new JButton("Cantar victoria");
    botonVictoria.addActionListener(evento -> seleccionarVictoria());
    panelLateral.add(botonVictoria);

    JButton botonSalir = new JButton("Salir");
    botonSalir.addActionListener(evento -> System.exit(0));
    panelLateral.add(botonSalir);

    add(panelLateral, BorderLayout.EAST);

    setSize(ANCHO_VENTANA, ALTO_VENTANA);
    setLocationByPlatform(true);
    setVisible(true);
  }

  private void seleccionarPatron(Patron patron) {
    this.controlador.seleccionarPatron(this.jugador, patron);
  }

  private void seleccionarVictoria() {
    this.controlador.seleccionarVictoria(this.jugador);
  }

  @Override
  public void actualizar(Modelo modelo) {
    for (int i = 0; i < this.panelesJugadores.size(); i++) {
      boolean esPropio = modelo.obtenerJugadores().get(i) == this.jugador;
      Carta cartaGritada = esPropio ? modelo.obtenerCartaActual() : null;
      this.panelesJugadores.get(i).actualizar(cartaGritada);
    }

    lblCartaGritada.setText("Carta gritada: " + (modelo.obtenerCartaActual() == null ? "-" : modelo.obtenerCartaActual().obtenerNombre()));
    lblCartasRestantes.setText("Cartas restantes: " + modelo.obtenerCartasRestantes());
    lblPatronSeleccionado.setText("Patrón: " + (modelo.obtenerPatronSeleccionado() == null ? "-" : modelo.obtenerPatronSeleccionado().obtenerNombre()));
    lblPuntaje.setText(jugador.obtenerNombre() + " = " + jugador.obtenerPuntaje());
    lblGanador.setText("Ganador: " + (modelo.obtenerGanador() == null ? "-" : modelo.obtenerGanador().obtenerNombre()));

    panelLateral.revalidate();
    panelLateral.repaint();

    TipoMensaje tipoMensaje = modelo.obtenerTipoMensaje();
    if (tipoMensaje != tipoMensajeAvisado) {
      tipoMensajeAvisado = tipoMensaje;
      switch (tipoMensaje) {
        case SIN_PATRON ->
          mostrarMensajeSinPatron();
        case CASILLA_INVALIDA ->
          mostrarMensajeCasillaInvalido();
        case PATRON_VALIDO ->
          mostrarMensajePatronValido(modelo.obtenerPatronSeleccionado());
        case PATRON_INVALIDO ->
          mostrarMensajePatronInvalido();
        case VICTORIA ->
          mostrarMensajeVictoria(modelo.obtenerGanador());
        case VICTORIA_INVALIDA ->
          mostrarMensajeVictoriaInvalido();
        default -> {
        }
      }
    }
  }

  public void mostrarMensajeSinPatron() {
    JOptionPane.showMessageDialog(this, "Primero se debe seleccionar un patrón");
  }

  public void mostrarMensajePatronValido(Patron patron) {
    JOptionPane.showMessageDialog(this, "El patrón " + patron.obtenerNombre() + " fue seleccionado correctamente, +5 puntos");
  }

  public void mostrarMensajePatronInvalido() {
    JOptionPane.showMessageDialog(this, "El patrón no es válido, -5 puntos");
  }

  public void mostrarMensajeVictoria(Jugador ganador) {
    JOptionPane.showMessageDialog(this, "¡Lotería! Ganó " + ganador.obtenerNombre());
  }

  public void mostrarMensajeVictoriaInvalido() {
    JOptionPane.showMessageDialog(this, "No se puede cantar victoria");
  }

  public void mostrarMensajeCasillaInvalido() {
    JOptionPane.showMessageDialog(this, "La casilla no se puede seleccionar");
  }
}
