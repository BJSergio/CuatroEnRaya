package org.iesalandalus.programacion.cuatroenraya.modelo;


import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

import javax.naming.OperationNotSupportedException;

import java.util.Objects;

public class CuatroEnRaya {

    private static final int NUMERO_JUGADORES = 2;
    private final Jugador[] jugadores;
    private final Tablero tablero;

    public CuatroEnRaya(Jugador jugador1, Jugador jugador2) {

        jugadores = new Jugador[NUMERO_JUGADORES];
        jugadores[NUMERO_JUGADORES - 2] = Objects.requireNonNull(jugador1, "El primer jugador no puede ser nulo.");
        jugadores[NUMERO_JUGADORES - 1] = Objects.requireNonNull(jugador2, "El segundo jugador no puede ser nulo.");
        tablero = new Tablero();
    }

    private boolean tirar(Jugador jugador) {
        boolean esGanador = false;
        boolean esColumnaValida = false;
        // Antes, el bucle que tenía repetía la tirada hasta que sea ganadora
        do {
            int columna = Consola.leerColumna(jugador);
            try {
                esGanador = tablero.introducirFicha(columna, jugador.colorFichas());
                esColumnaValida = true;
            } catch (OperationNotSupportedException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (!esColumnaValida);
        return esGanador;
    }

    public void jugar() {
        int indice = 0;
        boolean esJugadaGanadora;
        Jugador jugador;
        do {
            esJugadaGanadora = tirar(jugadores[indice]);
            jugador = jugadores[indice];
            System.out.println(tablero);
            indice++;
            if (indice > 1) {
                indice = 0;
            }
        } while (!tablero.estaLleno() && !esJugadaGanadora);
        if (tablero.estaLleno()) {
            System.out.println("No se pueden introducir más fichas, el tablero está lleno.");
        } else {
            System.out.println("ENHORABUENA, " + jugador.nombre() + " has ganado!!!");
        }
    }
}
