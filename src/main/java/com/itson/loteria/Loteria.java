package com.itson.loteria;

import com.itson.loteria.controlador.ControladorJuego;
import com.itson.loteria.modelo.Carta;
import com.itson.loteria.modelo.Fachada;
import com.itson.loteria.modelo.FachadaJuego;
import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.ModeloJuego;
import com.itson.loteria.modelo.Tablero;
import com.itson.loteria.vista.VistaJuego;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;

public class Loteria {

  public static void main(String[] args) {
    String[] nombresCartas = {
      "El gallo", "El diablito", "La dama", "El catrín", "El paraguas", "La sirena",
      "La escalera", "La botella", "El barril", "El árbol", "El melón", "El valiente",
      "El gorrito", "La muerte", "La pera", "La bandera", "El bandolón", "El violoncello",
      "La garza", "El pájaro", "La mano", "La bota", "La luna", "El cotorro",
      "El borracho", "El negrito", "El corazón", "La sandía", "El tambor", "El camarón",
      "Las jaras", "El músico", "La araña", "El soldado", "La estrella", "El cazo",
      "El mundo", "El Apache", "El nopal", "El alacrán", "La rosa", "La calavera",
      "La campana", "El cantarito", "El venado", "El Sol", "La corona", "La chalupa",
      "El pino", "El pescado", "La palma", "La maceta", "El arpa", "La rana"
    };

    List<Carta> mazo = new ArrayList<>();
    for (int i = 0; i < nombresCartas.length; i++) {
      mazo.add(new Carta(nombresCartas[i], "carta" + (i + 1) + ".png"));
    }

    List<Jugador> jugadores = new ArrayList<>();
    for (int i = 1; i <= 4; i++) {
      List<Carta> repartidas = new ArrayList<>(mazo);
      Collections.shuffle(repartidas);
      Carta[][] casillas = new Carta[4][4];
      for (int casilla = 0; casilla < 16; casilla++) {
        casillas[casilla / 4][casilla % 4] = repartidas.get(casilla);
      }
      jugadores.add(new Jugador("Jugador " + i, 0, "jugador" + i + ".png", new Tablero(casillas)));
    }

    Fachada fachada = new FachadaJuego();
    ModeloJuego modelo = new ModeloJuego(fachada, jugadores, mazo.get(0), mazo.size());

    SwingUtilities.invokeLater(() -> {
      ControladorJuego controlador = new ControladorJuego(modelo);
      VistaJuego vista = new VistaJuego(modelo, controlador);
      modelo.agregarObservador(vista);
      modelo.notificarObservadores();
    });
  }
}
