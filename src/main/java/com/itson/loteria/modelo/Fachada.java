package com.itson.loteria.modelo;

import java.util.List;

public interface Fachada {

  boolean validarCasilla(Jugador jugador, Carta cartaGritada, int fila, int columna);

  boolean validarPatron(List<Jugador> jugadores, Jugador jugador, Patron patron);

  boolean validarVictoria(Jugador jugador, Jugador ganador);
}
