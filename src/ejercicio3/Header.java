package ejercicio3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Header {
    private int alto;
    private int ancho;
    private double[] arregloFrecuencias;


    public Header(int alto, int ancho, double[] arregloFrecuencias) {
        this.alto = alto;
        this.ancho = ancho;
        this.arregloFrecuencias = arregloFrecuencias;

    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int getCantColor() {
        int out=0;
        for (int i=0;i<arregloFrecuencias.length;i++)
            if(arregloFrecuencias[i]!=0)
                out++;
        return out;
    }


    public int getFrecuenciaColor(int posicion){
        return (int)arregloFrecuencias[posicion];
    }

}
