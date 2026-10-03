package com.itson.loteria;

import com.itson.loteria.controlador.ControladorJuego;
import com.itson.loteria.modelo.Carta;
import com.itson.loteria.modelo.Fachada;
import com.itson.loteria.modelo.FachadaJuego;
import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.ManejadorSocket;
import com.itson.loteria.modelo.ModeloJuego;
import com.itson.loteria.modelo.Socket;
import com.itson.loteria.modelo.Tablero;
import com.itson.loteria.vista.VistaJuego;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;

public class Loteria {

  private static final int NUMERO_CLIENTES = 2;
  private static final long INTERVALO_SOCKET_MS = 5000;

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
    for (int i = 1; i <= NUMERO_CLIENTES; i++) {
      List<Carta> repartidas = new ArrayList<>(mazo);
      Collections.shuffle(repartidas);
      Carta[][] casillas = new Carta[4][4];
      for (int casilla = 0; casilla < 16; casilla++) {
        casillas[casilla / 4][casilla % 4] = repartidas.get(casilla);
      }
      jugadores.add(new Jugador("Jugador " + i, 0, "jugador" + i + ".png", new Tablero(casillas)));
    }

    Fachada fachada = new FachadaJuego();
    ModeloJuego modelo = new ModeloJuego(fachada, jugadores, mazo);

    SwingUtilities.invokeLater(() -> {
      ControladorJuego controlador = new ControladorJuego(modelo);
      for (int i = 0; i < jugadores.size(); i++) {
        VistaJuego vista = new VistaJuego(modelo, controlador, jugadores.get(i));
        modelo.agregarObservador(vista);
      }
      modelo.notificarObservadores();

      recibirMensajesPeriodicamente(new ManejadorSocket(modelo));
    });
  }

  private static void recibirMensajesPeriodicamente(Socket socket) {
    Thread hilo = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          Thread.sleep(INTERVALO_SOCKET_MS);
        } catch (InterruptedException excepcion) {
          Thread.currentThread().interrupt();
          return;
        }

        socket.recibirMensaje();
      }
    }, "manejador-socket");
    hilo.setDaemon(true);
    hilo.start();
  }
}
