package com.itson.loteria.modelo;

import javax.swing.SwingUtilities;

public class ManejadorSocket {

  private static final long INTERVALO_MS = 5000;

  private final Modelo modelo;
  private final long intervaloMs;

  public ManejadorSocket(Modelo modelo) {
    this(modelo, INTERVALO_MS);
  }

  public ManejadorSocket(Modelo modelo, long intervaloMs) {
    this.modelo = modelo;
    this.intervaloMs = intervaloMs;
  }

  public void iniciar() {
    Thread hilo = new Thread(this::escuchar, "manejador-socket");
    hilo.setDaemon(true);
    hilo.start();
  }

  private void escuchar() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(this.intervaloMs);
      } catch (InterruptedException excepcion) {
        Thread.currentThread().interrupt();
        return;
      }

      System.out.println("Socket: peticion recibida, cantando carta...");
      SwingUtilities.invokeLater(this.modelo::gritarCarta);
    }
  }
}
