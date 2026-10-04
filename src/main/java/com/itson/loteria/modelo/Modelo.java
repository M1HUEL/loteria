package com.itson.loteria.modelo;

import java.util.List;

public interface Modelo {

  List<Jugador> obtenerJugadores();

  Carta obtenerCartaActual();

  int obtenerCartasRestantes();

  Jugador obtenerGanador();

  TipoMensaje obtenerTipoMensaje();

  Jugador obtenerJugadorMensaje();

  Patron obtenerPatronSeleccionado();

  void gritarCarta();
}
