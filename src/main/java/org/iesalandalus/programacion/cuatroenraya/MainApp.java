package org.iesalandalus.programacion.cuatroenraya;


import org.iesalandalus.programacion.cuatroenraya.modelo.CuatroEnRaya;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class MainApp {
    public static void main(String[] args) {
        Consola.subrayar("Programa que simula un cuatro en raya");
        Jugador jugador1 = Consola.leerJugador();
        Jugador jugador2 = Consola.leerJugador(jugador1.colorFichas());
        CuatroEnRaya cuatroEnRaya = new CuatroEnRaya(jugador1, jugador2);
        cuatroEnRaya.jugar();
    }
}

