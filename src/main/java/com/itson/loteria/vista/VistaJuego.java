package com.itson.loteria.vista;

import com.itson.loteria.controlador.ControladorJuego;
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

  private final ControladorJuego controlador;
  private final JPanel panelPuntajes;
  private final JLabel lblCartaGritada;
  private final JLabel lblCartasRestantes;
  private final JLabel lblPatronSeleccionado;
  private final List<PanelJugador> panelesJugadores = new ArrayList<>();
  private final List<JLabel> lblPuntajes = new ArrayList<>();
  private TipoMensaje tipoMensajeAvisado = TipoMensaje.NINGUNO;

  public VistaJuego(Modelo modelo, ControladorJuego controlador) {
    this.controlador = controlador;

    setTitle("Lotería");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel panelJugadores = new JPanel();
    panelJugadores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelJugadores.setLayout(new GridLayout(1, modelo.obtenerJugadores().size()));
    for (int i = 0; i < modelo.obtenerJugadores().size(); i++) {
      PanelJugador panelJugador = new PanelJugador(modelo.obtenerJugadores().get(i), this.controlador);
      this.panelesJugadores.add(panelJugador);
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
      this.panelesJugadores.get(i).actualizar();
      this.lblPuntajes.get(i).setText(jugador.obtenerNombre() + " = " + jugador.obtenerPuntaje());
    }

    panelPuntajes.revalidate();
    panelPuntajes.repaint();

    TipoMensaje tipoMensaje = modelo.obtenerTipoMensaje();
    if (tipoMensaje != tipoMensajeAvisado) {
      tipoMensajeAvisado = tipoMensaje;
      switch (tipoMensaje) {
        case CASILLA_INVALIDA ->
          mostrarMensajeCasillaInvalido();
        case PATRON_VALIDO ->
          mostrarMensajePatronValido(modelo.obtenerPatronSeleccionado());
        case PATRON_INVALIDO ->
          mostrarMensajePatronInvalido();
        case VICTORIA ->
          mostrarMensajeVictoria();
        case VICTORIA_INVALIDA ->
          mostrarMensajeVictoriaInvalido();
        default -> {
        }
      }
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
