package ejercicio4;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import common.ImagenWill;

import java.awt.*;
import java.util.Random;

public class Canal {

    static final double EPSILON = 0.1;
    static final int tamanio=256;
    private double[][] matrizFrecuencias = new double[tamanio][tamanio];  //ver si puedo tenerla de 16x16
    private int ancho;
    private int alto;



    public int getTotalColumna(int columna){
        int suma = 0;
        for(int fila = 0; fila < tamanio; fila++) {
            suma += matrizFrecuencias[columna][fila];
        }
        return suma;
    }

    public Canal(ImagenWill imgX, ImagenWill imgY) {
        for(int i=0; i < imgX.getImagen().getWidth(); i++){
            for(int j=0; j < imgX.getImagen().getHeight(); j++){

                Color colorPixelX = new Color(imgX.getImagen().getRGB(i, j));
                int valorPixelX = colorPixelX.getRed();

                Color colorPixelY = new Color(imgY.getImagen().getRGB(i, j));
                int valorPixelY = colorPixelY.getRed();

                matrizFrecuencias[valorPixelX][valorPixelY] ++;
            }
        }
        this.ancho = imgX.getImagen().getWidth();
        this.alto = imgY.getImagen().getHeight();
    }



    public double [] [] getMatrizCondicional() {
        double [][] ret = new double[256][256];
        for (int columnaActual = 0; columnaActual < tamanio; columnaActual++) {
            int sumaTotal = this.getTotalColumna(columnaActual);
            for (int filaActual = 0; filaActual < tamanio; filaActual++) {
                if (matrizFrecuencias[columnaActual][filaActual] != 0) {
                    ret[columnaActual][filaActual] = ((matrizFrecuencias[columnaActual][filaActual]) / sumaTotal);
                }
            }
        }
        return ret;
    }





    public double [] [] getMatrizConjunta() {
        double [][] ret = new double[tamanio][tamanio];
        int total = this.ancho * this.alto;
        for (int columnaActual = 0; columnaActual < tamanio; columnaActual++) {
            for (int filaActual = 0; filaActual < tamanio; filaActual++) {
                if (matrizFrecuencias[columnaActual][filaActual] != 0) {
                    ret[columnaActual][filaActual] = ((matrizFrecuencias[columnaActual][filaActual]) / total);
                }
            }
        }
        return ret;
    }

    public double [] getProbAcumuladaX(){
        double [] ret = new double[tamanio];
        int suma=0;
        for (int c = 0; c <tamanio; c++){
            for (int f =0; f<tamanio ; f++){
                suma += this.getMatrizConjunta()[c][f];
            }
           ret[c] = suma;
        }
        return ret;
    }


    public double getRuido(){
        double ruidoAnterior = -1;
        double ruidoActual =0;
        int pruebas =0;

        while(!converge(ruidoActual,ruidoAnterior) && (pruebas < 10000)){
            int entrada = this.generarEntrada();
            pruebas++;

            //verificar condicion favorable (exitos++)

            //actualizar ruidoAnterior
            //actualizar ruidoActual
        }

        //devolver ruidoActual

        return 0;
    }

    public int generarEntrada(){
        double [] fAcumulada = this.getProbAcumuladaX();
        Random rand = new Random();
        double dRandom = rand.nextInt(1);
        int i = 0;
        while (i<fAcumulada.length){
            if (dRandom < fAcumulada[i]){
                return i;
            }
        }
        return -1;
    }


    public boolean converge(double actual, double anterior){
        return ((Math.abs(actual-anterior)) <EPSILON);
    }



}






