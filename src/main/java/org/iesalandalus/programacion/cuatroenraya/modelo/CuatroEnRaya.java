package org.iesalandalus.programacion.cuatroenraya.modelo;


import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;


public class CuatroEnRaya {

    private static final int NUMERO_JUGADORES = 2;
    private final Jugador[] jugadores;
    private final Tablero[][] tablero;

    public CuatroEnRaya(Jugador jugador1, Jugador jugador2) {
        jugadores = new Jugador[NUMERO_JUGADORES];
        tablero = new Tablero[Tablero.FILAS][Tablero.COLUMNAS];
        try {
            jugadores[NUMERO_JUGADORES - 2] = jugador1;
            jugadores[NUMERO_JUGADORES - 1] = jugador2;
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        for (int i = 0; i < Tablero.FILAS; i++) {
            for (int j = 0; j < Tablero.COLUMNAS; j++) {
                tablero[i][j] = new Tablero();
            }
        }
    }

    private boolean tirar(Jugador jugador) {


        boolean esJugadaGanadora = false;
        boolean esColumnaValida = true; // Asumo que es una columna válida
        do {
            int columna = Consola.leerColumna(jugador);
            try {
                if (tablero[Tablero.FILAS - 1][columna].introducirFicha(columna, jugador.colorFichas())) {
                    esJugadaGanadora = true;
                }
            } catch (OperationNotSupportedException e) {
                System.out.println("ERROR: " + e.getMessage());
                esColumnaValida = false;
            }
        } while (esColumnaValida && !esJugadaGanadora);
        return esJugadaGanadora;
    }

    public void jugar() {
        for (Jugador player : jugadores) {
            do {
                tirar(player);
                System.out.println(Arrays.deepToString(tablero));
            } while (tablero[Tablero.FILAS][Tablero.COLUMNAS].estaLleno() && !tirar(player));
        }
    }
}
