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

    Tablero tablero = jugador.obtenerTablero();
    return !tablero.obtenerMarcada(fila, columna) && tablero.obtenerCarta(fila, columna).esMisma(cartaGritada);
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
    return tableroCumplePatron(jugador.obtenerTablero(), patron);
  }

  @Override
  public boolean validarVictoria(Jugador jugador, Jugador ganador) {
    if (ganador != null) {
      return false;
    }

    for (Patron patron : jugador.obtenerPatrones()) {
      if (tableroCumplePatron(jugador.obtenerTablero(), patron)) {
        return true;
      }
    }
    return false;
  }

  private boolean tableroCumplePatron(Tablero tablero, Patron patron) {
    return switch (patron) {
      case CHORRO -> {
        boolean chorro = false;
        for (int i = 0; i < TAMANO_TABLERO && !chorro; i++) {
          chorro = esFilaMarcada(tablero, i) || esColumnaMarcada(tablero, i);
        }
        yield chorro;
      }
      case CRUZ ->
        esDiagonalPrincipal(tablero)
        || esDiagonalSecundaria(tablero)
        || esFilaMarcada(tablero, CENTRO_TABLERO)
        || esColumnaMarcada(tablero, CENTRO_TABLERO);
      case DIAGONAL ->
        esDiagonalPrincipal(tablero) || esDiagonalSecundaria(tablero);
      case ESQUINAS ->
        tablero.obtenerMarcada(0, 0)
        && tablero.obtenerMarcada(0, TAMANO_TABLERO - 1)
        && tablero.obtenerMarcada(TAMANO_TABLERO - 1, 0)
        && tablero.obtenerMarcada(TAMANO_TABLERO - 1, TAMANO_TABLERO - 1);
      case CARTA_LLENA -> {
        boolean cartaLlena = true;
        for (int f = 0; f < TAMANO_TABLERO && cartaLlena; f++) {
          for (int c = 0; c < TAMANO_TABLERO && cartaLlena; c++) {
            cartaLlena = tablero.obtenerMarcada(f, c);
          }
        }
        yield cartaLlena;
      }
    };
  }

  private boolean esFilaMarcada(Tablero tablero, int fila) {
    for (int c = 0; c < TAMANO_TABLERO; c++) {
      if (!tablero.obtenerMarcada(fila, c)) {
        return false;
      }
    }
    return true;
  }

  private boolean esColumnaMarcada(Tablero tablero, int columna) {
    for (int f = 0; f < TAMANO_TABLERO; f++) {
      if (!tablero.obtenerMarcada(f, columna)) {
        return false;
      }
    }
    return true;
  }

  private boolean esDiagonalPrincipal(Tablero tablero) {
    for (int i = 0; i < TAMANO_TABLERO; i++) {
      if (!tablero.obtenerMarcada(i, i)) {
        return false;
      }
    }
    return true;
  }

  private boolean esDiagonalSecundaria(Tablero tablero) {
    for (int i = 0; i < TAMANO_TABLERO; i++) {
      if (!tablero.obtenerMarcada(i, TAMANO_TABLERO - 1 - i)) {
        return false;
      }
    }
    return true;
  }
}
