import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Juego extends Canvas implements Runnable {

    private static final int ANCHO = 800;
    private static final int ALTO = 600;

    private volatile boolean enfuncionamiento = false;
    private static final String NOMBRE = "Head On The Game";

    private static int aps = 0;
    private static int fps = 0;

    private static JFrame ventana;
    private Thread thread1;

    public Juego() {

        setPreferredSize(new Dimension(ANCHO, ALTO));
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        Juego main = new Juego();
        main.inicio();
    }

    private synchronized void inicio() {
        enfuncionamiento = true;

        thread1 = new Thread(this, "Graficos");
        thread1.start();
    }

    private synchronized void detener() {
        enfuncionamiento = false;

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void actualizar() {
        aps++;
    }

    private void mostrar() {
        fps++;
    }

    @Override
    public synchronized void run() {
        final int NS_POR_SEGUNDO = 1000000000;
        final byte APS_OBJETIVO = 120;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

        long referenciaAcutalizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();

        double tiempoTranscurrido;
        double delta = 0;

        while (enfuncionamiento) {
            final long inicioBucle = System.nanoTime();
            tiempoTranscurrido = inicioBucle - referenciaAcutalizacion;
            referenciaAcutalizacion = inicioBucle;

            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

            while (delta >= 1) {
                actualizar();
                delta--;
            }

            mostrar();

            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
                ventana.setTitle(NOMBRE + " || APS: " + aps + " || " + "FPS: " + fps);
                referenciaContador = System.nanoTime();
                aps = 0;
                fps = 0;
            }
        }
    }
}
