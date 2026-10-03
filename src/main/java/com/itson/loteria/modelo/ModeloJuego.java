package com.itson.loteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class ModeloJuego implements Modelo {

  private final Fachada fachada;
  private List<Observador> observadores = new ArrayList<>();
  private List<Jugador> jugadores;
  private Carta cartaActual;
  private int cartasRestantes;
  private Patron patronSeleccionado;
  private TipoMensaje tipoMensaje = TipoMensaje.NINGUNO;

  public ModeloJuego(Fachada fachada, List<Jugador> jugadores, Carta cartaActual, int cartasRestantes) {
    this.fachada = fachada;
    this.jugadores = jugadores;
    this.cartaActual = cartaActual;
    this.cartasRestantes = cartasRestantes;
  }

  public void agregarObservador(Observador observador) {
    this.observadores.add(observador);
  }

  public void eliminarObservador(Observador observador) {
    this.observadores.remove(observador);
  }

  public void notificarObservadores() {
    for (Observador observador : this.observadores) {
      observador.actualizar(this);
    }
  }

  @Override
  public List<Jugador> obtenerJugadores() {
    return this.jugadores;
  }

  @Override
  public Carta obtenerCartaActual() {
    return this.cartaActual;
  }

  @Override
  public int obtenerCartasRestantes() {
    return this.cartasRestantes;
  }

  @Override
  public Patron obtenerPatronSeleccionado() {
    return this.patronSeleccionado;
  }

  @Override
  public TipoMensaje obtenerTipoMensaje() {
    return this.tipoMensaje;
  }

  public void seleccionarCasilla(Jugador jugador, int fila, int columna) {
    boolean casillaValida = this.fachada.validarCasilla(jugador, fila, columna);
    if (casillaValida) {
      jugador.obtenerTablero().seleccionarCasilla(fila, columna);
      this.tipoMensaje = TipoMensaje.NINGUNO;
    } else {
      this.tipoMensaje = TipoMensaje.CASILLA_INVALIDA;
    }

    notificarObservadores();
  }

  public void seleccionarPatron(Patron patron) {
    if (this.fachada.validarPatron(patron)) {
      this.patronSeleccionado = patron;
      this.tipoMensaje = TipoMensaje.PATRON_VALIDO;
    } else {
      this.tipoMensaje = TipoMensaje.PATRON_INVALIDO;
    }

    notificarObservadores();
  }

  public void seleccionarVictoria() {
    this.tipoMensaje = this.fachada.validarVictoria()
      ? TipoMensaje.VICTORIA
      : TipoMensaje.VICTORIA_INVALIDA;

    notificarObservadores();
  }
}
