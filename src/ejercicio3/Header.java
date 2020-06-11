package ejercicio3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Header {
    private int alto;
    private int ancho;
    private double[] arregloProbabilidades;
    private int longitud;

    public Header(int alto, int ancho, double[] arregloProbabilidades, int longitud) {
        this.alto = alto;
        this.ancho = ancho;
        this.arregloProbabilidades = arregloProbabilidades;
        this.longitud = longitud;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public double[] getArregloProbabilidades() {
        return arregloProbabilidades;
    }

    public int getLongitud() {
        return longitud;
    }
}
