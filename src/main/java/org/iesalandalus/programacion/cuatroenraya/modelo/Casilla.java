package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;

public class Casilla {

    private Ficha ficha;

    public Casilla() {
        ficha = null;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) throws OperationNotSupportedException {
        if (ficha == null) {
            throw new NullPointerException("No se puede poner una ficha nula.");
        }
        if (estaOcupada()) {
            throw new OperationNotSupportedException("La casilla ya contiene una ficha.");
        }
        this.ficha = ficha;
    }

    public boolean estaOcupada() {
        return (ficha != null);
    }

    @Override
    public String toString() {

        String cadena = null;

        if (estaOcupada()) {
            if (ficha.equals(Ficha.AZUL)) {
                cadena = String.format("%c", 'A');
            }
            if (ficha.equals(Ficha.VERDE)) {
                cadena = String.format("%c", 'V');
            }
        } else {
            cadena = String.format("%c", ' ');
        }
        return cadena;
    }
}
