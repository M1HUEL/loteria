package com.itson.loteria.modelo;

import java.util.List;

public class FachadaJuego implements Fachada {

  private static final int TAMANO_TABLERO = 4;
  private static final int CENTRO_TABLERO = TAMANO_TABLERO / 2;

  @Override
  public boolean validarCasilla(Jugador jugador, Carta cartaGritada, int fila, int columna) {
    if (fila < 0 || fila >= TAMANO_TABLERO || columna < 0 || columna >= TAMANO_TABLERO) {
      return false;
    }

    return !jugador.obtenerMarcada(fila, columna) && jugador.obtenerCarta(fila, columna).esMisma(cartaGritada);
  }

  @Override
  public boolean validarPatron(List<Jugador> jugadores, Jugador jugador, Patron patron) {
    if (patron == null || jugador.obtenerPatrones().contains(patron)) {
      return false;
    }

    for (Jugador otro : jugadores) {
      if (otro != jugador && otro.obtenerPatrones().contains(patron)) {
        return false;
      }
    }
    return tableroCumplePatron(jugador, patron);
  }

  @Override
  public boolean validarVictoria(Jugador jugador, Jugador ganador) {
    if (ganador != null) {
      return false;
    }

    for (Patron patron : jugador.obtenerPatrones()) {
      if (tableroCumplePatron(jugador, patron)) {
        return true;
      }
    }
    return false;
  }

  private boolean tableroCumplePatron(Jugador jugador, Patron patron) {
    return switch (patron) {
      case CHORRO -> {
        boolean chorro = false;
        for (int i = 0; i < TAMANO_TABLERO && !chorro; i++) {
          chorro = esFilaMarcada(jugador, i) || esColumnaMarcada(jugador, i);
        }
        yield chorro;
      }
      case CRUZ ->
        esDiagonalPrincipal(jugador)
        || esDiagonalSecundaria(jugador)
        || esFilaMarcada(jugador, CENTRO_TABLERO)
        || esColumnaMarcada(jugador, CENTRO_TABLERO);
      case DIAGONAL ->
        esDiagonalPrincipal(jugador) || esDiagonalSecundaria(jugador);
      case ESQUINAS ->
        jugador.obtenerMarcada(0, 0)
        && jugador.obtenerMarcada(0, TAMANO_TABLERO - 1)
        && jugador.obtenerMarcada(TAMANO_TABLERO - 1, 0)
        && jugador.obtenerMarcada(TAMANO_TABLERO - 1, TAMANO_TABLERO - 1);
      case CARTA_LLENA -> {
        boolean cartaLlena = true;
        for (int f = 0; f < TAMANO_TABLERO && cartaLlena; f++) {
          for (int c = 0; c < TAMANO_TABLERO && cartaLlena; c++) {
            cartaLlena = jugador.obtenerMarcada(f, c);
          }
        }
        yield cartaLlena;
      }
    };
  }

  private boolean esFilaMarcada(Jugador jugador, int fila) {
    for (int c = 0; c < TAMANO_TABLERO; c++) {
      if (!jugador.obtenerMarcada(fila, c)) {
        return false;
      }
    }
    return true;
  }

  private boolean esColumnaMarcada(Jugador jugador, int columna) {
    for (int f = 0; f < TAMANO_TABLERO; f++) {
      if (!jugador.obtenerMarcada(f, columna)) {
        return false;
      }
    }
    return true;
  }

  private boolean esDiagonalPrincipal(Jugador jugador) {
    for (int i = 0; i < TAMANO_TABLERO; i++) {
      if (!jugador.obtenerMarcada(i, i)) {
        return false;
      }
    }
    return true;
  }

  private boolean esDiagonalSecundaria(Jugador jugador) {
    for (int i = 0; i < TAMANO_TABLERO; i++) {
      if (!jugador.obtenerMarcada(i, TAMANO_TABLERO - 1 - i)) {
        return false;
      }
    }
    return true;
  }
}
