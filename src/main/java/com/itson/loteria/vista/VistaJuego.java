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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VistaJuego extends JFrame implements Observador {

  private final Jugador jugador;
  private final List<PanelJugador> panelesJugadores = new ArrayList<>();
  private final PanelAcciones panelAcciones;
  private TipoMensaje tipoMensajeAvisado = TipoMensaje.NINGUNO;

  public VistaJuego(Modelo modelo, ControladorJuego controlador, Jugador jugador) {
    this.jugador = jugador;

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

    panelAcciones = new PanelAcciones(controlador, jugador, modelo.obtenerJugadores());
    add(panelAcciones, BorderLayout.EAST);

    setSize(1300, 750);
    setLocationByPlatform(true);
    setVisible(true);
  }

  @Override
  public void actualizar(Modelo modelo) {
    for (int i = 0; i < this.panelesJugadores.size(); i++) {
      boolean esPropio = modelo.obtenerJugadores().get(i) == this.jugador;
      Carta cartaGritada = esPropio ? modelo.obtenerCartaActual() : null;
      this.panelesJugadores.get(i).actualizar(cartaGritada);
    }

    panelAcciones.actualizar(modelo);

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
        case VICTORIA, FIN_CARTAS ->
          new DialogoGanador(this, tipoMensaje, modelo.obtenerGanador(), modelo.obtenerJugadores()).mostrarDialogo();
        case VICTORIA_INVALIDA ->
          mostrarMensajeVictoriaInvalido();
        default -> {
        }
      }
    }
  }

  public void mostrarMensajeSinPatron() {
    JOptionPane.showMessageDialog(this, "Primero se debe seleccionar un patrón, -10 puntos");
  }

  public void mostrarMensajePatronValido(Patron patron) {
    JOptionPane.showMessageDialog(this, "El patrón " + patron.obtenerNombre() + " fue seleccionado correctamente, +5 puntos");
  }

  public void mostrarMensajePatronInvalido() {
    JOptionPane.showMessageDialog(this, "El patrón no es válido, -5 puntos");
  }

  public void mostrarMensajeVictoriaInvalido() {
    JOptionPane.showMessageDialog(this, "No se puede cantar victoria, -10 puntos");
  }

  public void mostrarMensajeCasillaInvalido() {
    JOptionPane.showMessageDialog(this, "La casilla no se puede seleccionar");
  }
}
