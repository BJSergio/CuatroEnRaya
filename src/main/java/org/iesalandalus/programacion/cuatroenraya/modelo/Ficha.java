package org.iesalandalus.programacion.cuatroenraya.modelo;

public enum Ficha {

    AZUL("A"),
    VERDE("V");

    private final String cadenaAMostrar;

    Ficha(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}
