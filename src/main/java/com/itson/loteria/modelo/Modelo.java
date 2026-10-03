package com.itson.loteria.modelo;

import java.util.List;

public interface Modelo {

  List<Jugador> obtenerJugadores();

  Carta obtenerCartaActual();

  int obtenerCartasRestantes();

  Patron obtenerPatronSeleccionado();

  Jugador obtenerGanador();

  TipoMensaje obtenerTipoMensaje();

  void gritarCarta();
}
