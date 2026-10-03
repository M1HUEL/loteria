package com.itson.loteria.modelo;

public class FachadaJuego implements Fachada {

  private static final int TAMANO_TABLERO = 4;
  private static final int CENTRO_TABLERO = TAMANO_TABLERO / 2;

  @Override
  public boolean validarCasilla(Jugador jugador, int fila, int columna) {
    if (fila < 0 || fila >= TAMANO_TABLERO || columna < 0 || columna >= TAMANO_TABLERO) {
      return false;
    }
    return !jugador.obtenerTablero().obtenerMarcada(fila, columna);
  }

  @Override
  public boolean validarPatron(Patron patron) {
    return true;
  }

  @Override
  public boolean validarVictoria(Jugador jugador, Patron patron) {
    if (patron == null) {
      return false;
    }

    Tablero tablero = jugador.obtenerTablero();
    return switch (patron) {
      case CHORRO -> esChorro(tablero);
      case CRUZ -> esCruz(tablero);
      case DIAGONAL -> esDiagonal(tablero);
      case CARTA_LLENA -> esCartaLlena(tablero);
    };
  }

  private boolean esChorro(Tablero tablero) {
    for (int i = 0; i < TAMANO_TABLERO; i++) {
      if (esFilaMarcada(tablero, i) || esColumnaMarcada(tablero, i)) {
        return true;
      }
    }
    return false;
  }

  private boolean esCruz(Tablero tablero) {
    return esDiagonalPrincipal(tablero)
        || esDiagonalSecundaria(tablero)
        || esFilaMarcada(tablero, CENTRO_TABLERO)
        || esColumnaMarcada(tablero, CENTRO_TABLERO);
  }

  private boolean esDiagonal(Tablero tablero) {
    return esDiagonalPrincipal(tablero) || esDiagonalSecundaria(tablero);
  }

  private boolean esCartaLlena(Tablero tablero) {
    for (int f = 0; f < TAMANO_TABLERO; f++) {
      for (int c = 0; c < TAMANO_TABLERO; c++) {
        if (!tablero.obtenerMarcada(f, c)) {
          return false;
        }
      }
    }
    return true;
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