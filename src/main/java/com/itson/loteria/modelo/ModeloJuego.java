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
  private String mensaje;
  private boolean victoria;
  private boolean casillaValida = true;
  private boolean patronValido = true;
  private boolean victoriaValida = true;

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
  public String obtenerMensaje() {
    return this.mensaje;
  }

  @Override
  public boolean obtenerVictoria() {
    return this.victoria;
  }

  @Override
  public boolean obtenerCasillaValida() {
    return this.casillaValida;
  }

  @Override
  public boolean obtenerPatronValido() {
    return this.patronValido;
  }

  @Override
  public boolean obtenerVictoriaValida() {
    return this.victoriaValida;
  }

  @Override
  public void seleccionarCasilla(int jugador, int fila, int columna) {
    this.patronValido = true;
    this.victoriaValida = true;

    Jugador jugadorSeleccionado = this.jugadores.get(jugador);
    this.casillaValida = this.fachada.validarCasilla(jugadorSeleccionado, fila, columna);
    if (this.casillaValida) {
      jugadorSeleccionado.obtenerTablero().seleccionarCasilla(fila, columna);
    }

    notificarObservadores();
  }

  @Override
  public void seleccionarPatron(Patron patron) {
    this.casillaValida = true;
    this.victoriaValida = true;

    this.patronValido = this.fachada.validarPatron(patron);
    if (this.patronValido) {
      this.patronSeleccionado = patron;
      this.mensaje = "El patrón " + patron.obtenerNombre() + " fue seleccionado correctamente";
    }

    notificarObservadores();
  }

  @Override
  public void seleccionarVictoria() {
    this.casillaValida = true;
    this.patronValido = true;

    this.victoriaValida = this.fachada.validarVictoria();
    if (this.victoriaValida) {
      this.victoria = true;
    }

    notificarObservadores();
  }
}
