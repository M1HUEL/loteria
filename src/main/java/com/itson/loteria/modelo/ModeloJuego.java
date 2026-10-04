package com.itson.loteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class ModeloJuego implements Modelo {

  private static final int PUNTOS_CASILLA = 1;
  private static final int PUNTOS_PATRON = 5;
  private static final int PUNTOS_VICTORIA_INVALIDA = 10;

  private final Fachada fachada;
  private List<Observador> observadores = new ArrayList<>();
  private List<Jugador> jugadores;
  private List<Carta> mazo;
  private int cartasCantadas;
  private Carta cartaActual;
  private int cartasRestantes;
  private Jugador ganador;
  private TipoMensaje tipoMensaje = TipoMensaje.NINGUNO;
  private Jugador jugadorMensaje;

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
  public Jugador obtenerGanador() {
    return this.ganador;
  }

  @Override
  public TipoMensaje obtenerTipoMensaje() {
    return this.tipoMensaje;
  }

  @Override
  public Jugador obtenerJugadorMensaje() {
    return this.jugadorMensaje;
  }

  @Override
  public Patron obtenerPatronSeleccionado() {
    if (this.jugadorMensaje == null) {
      return null;
    }
    List<Patron> patrones = this.jugadorMensaje.obtenerPatrones();
    return patrones.isEmpty() ? null : patrones.get(patrones.size() - 1);
  }

  @Override
  public void gritarCarta() {
    if (this.cartasCantadas >= this.mazo.size()) {
      return;
    }

    this.cartaActual = this.mazo.get(this.cartasCantadas);
    this.cartasCantadas++;
    this.cartasRestantes = this.mazo.size() - this.cartasCantadas;

    if (this.cartasRestantes == 0 && this.ganador == null) {
      Jugador mejorJugador = this.jugadores.get(0);
      for (Jugador jugador : this.jugadores) {
        if (jugador.obtenerPuntaje() > mejorJugador.obtenerPuntaje()) {
          mejorJugador = jugador;
        }
      }
      this.ganador = mejorJugador;
      this.tipoMensaje = TipoMensaje.FIN_CARTAS;
    } else {
      this.tipoMensaje = TipoMensaje.NINGUNO;
    }

    notificarObservadores();
  }

  public void seleccionarCasilla(Jugador jugador, int fila, int columna) {
    boolean casillaValida = this.fachada.validarCasilla(jugador, this.cartaActual, fila, columna);
    if (casillaValida) {
      jugador.sumarPuntos(PUNTOS_CASILLA);
      jugador.obtenerTablero().seleccionarCasilla(fila, columna);
      this.tipoMensaje = TipoMensaje.NINGUNO;
    } else {
      this.jugadorMensaje = jugador;
      this.tipoMensaje = TipoMensaje.CASILLA_INVALIDA;
    }

    notificarObservadores();
  }

  public void seleccionarPatron(Jugador jugador, Patron patron) {
    if (this.fachada.validarPatron(this.jugadores, jugador, patron)) {
      jugador.agregarPatron(patron);
      jugador.sumarPuntos(PUNTOS_PATRON);
      this.jugadorMensaje = jugador;
      this.tipoMensaje = TipoMensaje.PATRON_VALIDO;
    } else {
      jugador.restarPuntos(PUNTOS_PATRON);
      this.jugadorMensaje = jugador;
      this.tipoMensaje = TipoMensaje.PATRON_INVALIDO;
    }

    notificarObservadores();
  }

  public void seleccionarVictoria(Jugador jugador) {
    if (this.fachada.validarVictoria(jugador, this.ganador)) {
      this.ganador = jugador;
      this.jugadorMensaje = jugador;
      this.tipoMensaje = TipoMensaje.VICTORIA;
    } else {
      jugador.restarPuntos(PUNTOS_VICTORIA_INVALIDA);
      this.jugadorMensaje = jugador;
      this.tipoMensaje = jugador.obtenerPatrones().isEmpty() ? TipoMensaje.SIN_PATRON : TipoMensaje.VICTORIA_INVALIDA;
    }

    notificarObservadores();
  }
}
