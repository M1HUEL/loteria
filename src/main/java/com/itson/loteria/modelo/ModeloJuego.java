package com.itson.loteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class ModeloJuego implements Modelo {

  private static final int PUNTOS_PATRON = 5;

  private final Fachada fachada;
  private List<Observador> observadores = new ArrayList<>();
  private List<Jugador> jugadores;
  private List<Carta> mazo;
  private int cartasCantadas;
  private Carta cartaActual;
  private int cartasRestantes;
  private Patron patronSeleccionado;
  private Jugador ganador;
  private TipoMensaje tipoMensaje = TipoMensaje.NINGUNO;

  public ModeloJuego(Fachada fachada, List<Jugador> jugadores, List<Carta> mazo) {
    this.fachada = fachada;
    this.jugadores = jugadores;
    this.mazo = mazo;
    this.cartasRestantes = mazo.size();
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
  public Jugador obtenerGanador() {
    return this.ganador;
  }

  @Override
  public TipoMensaje obtenerTipoMensaje() {
    return this.tipoMensaje;
  }

  @Override
  public void gritarCarta() {
    if (this.cartasCantadas >= this.mazo.size()) {
      return;
    }

    this.cartaActual = this.mazo.get(this.cartasCantadas);
    this.cartasCantadas++;
    this.cartasRestantes = this.mazo.size() - this.cartasCantadas;
    this.tipoMensaje = TipoMensaje.NINGUNO;

    notificarObservadores();
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

  public void seleccionarPatron(Jugador jugador, Patron patron) {
    if (this.fachada.validarPatron(patron)) {
      jugador.sumarPuntos(PUNTOS_PATRON);
      this.patronSeleccionado = patron;
      this.tipoMensaje = TipoMensaje.PATRON_VALIDO;
    } else {
      jugador.restarPuntos(PUNTOS_PATRON);
      this.tipoMensaje = TipoMensaje.PATRON_INVALIDO;
    }

    notificarObservadores();
  }

  public void seleccionarVictoria(Jugador jugador) {
    if (this.patronSeleccionado == null) {
      this.tipoMensaje = TipoMensaje.SIN_PATRON;
    } else if (this.ganador == null && this.fachada.validarVictoria(jugador, this.patronSeleccionado)) {
      this.ganador = jugador;
      this.tipoMensaje = TipoMensaje.VICTORIA;
    } else {
      this.tipoMensaje = TipoMensaje.VICTORIA_INVALIDA;
    }

    notificarObservadores();
  }
}
