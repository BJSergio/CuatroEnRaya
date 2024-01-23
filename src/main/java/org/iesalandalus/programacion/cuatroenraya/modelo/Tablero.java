package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private final Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public boolean estaVacio() {
        boolean estaVacio = true;
        for (int i = 0; i < COLUMNAS && estaVacio; i++) {
            // Si devuelve false significa que hay una fila que contiene una ficha y no itera más
            estaVacio = columnaVacia(i);
        }
        return estaVacio;
    }

    private boolean columnaVacia(int columna) {
        return (!casillas[0][columna].estaOcupada());
    }

    public boolean estaLleno() {
        boolean estaLleno = true;
        for (int i = 0; i < COLUMNAS && estaLleno; i++) {
            // Si hay una casilla que no contiene una ficha romperá el bucle y devolverá false
            estaLleno = columnaLlena(i);
        }
        return estaLleno;
    }

    private boolean columnaLlena(int columna) {
        return (casillas[FILAS - 1][columna].estaOcupada()); // Devuelve true si está ocupada
    }

    private void comprobarFicha(Ficha ficha) {
        if (ficha == null) {
            throw new NullPointerException("La ficha no puede ser nula.");
        }
    }

    private void comprobarColumna(int columna) {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) {
        int fila = 0;
        while (casillas[fila][columna].estaOcupada() && fila < FILAS) {
            fila++;
        }
        return fila;
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas) {
        return (fichasIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS);
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int contador = 0;
        boolean esGanador = false;
        for (int i = 0; i < COLUMNAS && !esGanador; i++) {
            if (casillas[fila][i].estaOcupada() && casillas[fila][i].getFicha().equals(ficha)) {
                contador++;
            } else {
                contador = 0;
            }
            if (objetivoAlcanzado(contador)) {
                esGanador = true;
            }
        }
        return esGanador;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int contador = 0;
        boolean esGanador = false;
        for (int i = FILAS - 1; i >= 0 && !esGanador; i--) {
            if (casillas[i][columna].estaOcupada() && casillas[i][columna].getFicha().equals(ficha)) {
                contador++;
            } else {
                contador = 0;
            }
            if (objetivoAlcanzado(contador)) {
                esGanador = true;
            }
        }
        return esGanador;
    }

    private int menor(int fila, int columna) {
        return Math.min(fila, columna);
    }

    private boolean comprobarDiagonalNE(int filaSemilla, int columnaSemilla, Ficha ficha) {
        int desplazamiento = menor(filaSemilla, columnaSemilla);
        int filaInicial = filaSemilla - desplazamiento;
        int columnaInicial = columnaSemilla - desplazamiento;
        int contador = 0;
        boolean esGanador = false;

        for (int i = filaInicial, j = columnaInicial; i < FILAS && j < COLUMNAS && !esGanador; i++, j++) {
            if (casillas[i][j].estaOcupada() && casillas[i][j].getFicha().equals(ficha)) {
                contador++;
            } else {
                contador = 0;
            }
            if (objetivoAlcanzado(contador)) {
                esGanador = true;
            }
        }
        return esGanador;
    }

    private boolean comprobarDiagonalNO(int filaSemilla, int columnaSemilla, Ficha ficha) {
        int desplazamiento = menor(filaSemilla, (COLUMNAS - 1) - columnaSemilla);
        int filaInicial = filaSemilla - desplazamiento;
        int columnaInicial = columnaSemilla + desplazamiento;
        int contador = 0;
        boolean esGanador = false;

        for (int i = filaInicial, j = columnaInicial; i < FILAS && j >= 0 && !esGanador; i++, j--) {
            if (casillas[i][j].estaOcupada() && casillas[i][j].getFicha().equals(ficha)) {
                contador++;
            } else {
                contador = 0;
            }
            if (objetivoAlcanzado(contador)) {
                esGanador = true;
            }
        }
        return esGanador;
    }

    private boolean comprobarTirada(int fila, int columna) {
        return comprobarHorizontal(fila, casillas[fila][columna].getFicha()) ||
                comprobarVertical(columna, casillas[fila][columna].getFicha()) ||
                comprobarDiagonalNE(fila, columna, casillas[fila][columna].getFicha()) ||
                comprobarDiagonalNO(fila, columna, casillas[fila][columna].getFicha());
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws OperationNotSupportedException {
        comprobarColumna(columna);
        comprobarFicha(ficha);
        if (columnaLlena(columna)) {
            throw new OperationNotSupportedException("Columna llena.");
        }
        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);
        return comprobarTirada(fila, columna);
    }

    @Override
    public String toString() {

        StringBuilder tablero = new StringBuilder();
        for (int i = FILAS - 1; i >= 0; i--) {
            tablero.append('|');
            for (int j = 0; j < COLUMNAS; j++) {
                tablero.append(casillas[i][j]); // Ya sabe representarse por sí solo
            }
            tablero.append('|').append("\n");
        }
        for (int j = 0; j < COLUMNAS; j++) {
            if (j == 0) {
                tablero.append(' ');
            }
            tablero.append('-');
        }
        tablero.append("\n");
        return tablero.toString();
    }
}
