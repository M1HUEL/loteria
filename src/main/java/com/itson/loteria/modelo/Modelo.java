package com.itson.loteria.modelo;

import java.util.List;

public interface Modelo {

  List<Jugador> obtenerJugadores();

  Carta obtenerCartaActual();

  int obtenerCartasRestantes();

  Patron obtenerPatronSeleccionado();

  String obtenerMensaje();

  boolean obtenerVictoria();

  boolean obtenerCasillaValida();

  boolean obtenerPatronValido();

  boolean obtenerVictoriaValida();

  void seleccionarCasilla(int jugador, int fila, int columna);

  void seleccionarPatron(Patron patron);

  void seleccionarVictoria();
}
