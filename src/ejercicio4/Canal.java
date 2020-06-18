package ejercicio4;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import common.ImagenWill;

import java.awt.*;
import java.util.Random;

public class Canal {

    static final double EPSILON = 0.1;

    private double[][] matrizFrecuencias = new double[256][256];  //ver si puedo tenerla de 16x16




    public int getTotalColumna(int columna){
        int suma = 0;
        for(int fila=0; fila<256;fila++) {
            suma += matrizFrecuencias[columna][fila];
        }
        return suma;

    }

    public Canal(ImagenWill imgX, ImagenWill imgY) {
        for(int i=0;i<imgX.getImagen().getWidth();i++){
            for(int j=0;j< imgX.getImagen().getHeight();j++){

                Color colorPixelX = new Color(imgX.getImagen().getRGB(i, j));
                int valorPixelX = colorPixelX.getRed();

                Color colorPixelY = new Color(imgY.getImagen().getRGB(i, j));
                int valorPixelY = colorPixelY.getRed();

                matrizFrecuencias[valorPixelX][valorPixelY] ++;
            }
        }
    }


    public double [] [] getMatrizCondicional() {
        double [][] ret = new double[256][256];
        for (int columnaActual = 0; columnaActual < 256; columnaActual++) {
            int sumaTotal = this.getTotalColumna(columnaActual);
            for (int filaActual = 0; filaActual < 256; filaActual++) {
                ret [columnaActual][filaActual] = ((matrizFrecuencias[columnaActual][filaActual]) / sumaTotal);
            }
        }
        return ret;
    }


    public double [] [] getMatrizConjunta() {
        double [][] ret = new double[256][256];
        int total = 4000;                   //buscar forma de tener el ancho por alto asi no recorro matriz (si es de 256)
        for (int columnaActual = 0; columnaActual < 256; columnaActual++) {
            for (int filaActual = 0; filaActual < 256; filaActual++) {
                ret [columnaActual][filaActual] = ((matrizFrecuencias[columnaActual][filaActual]) / total);
            }
        }
        return ret;
    }

    public double [] getProbAcumuladaX(){
        double [] ret = new double[256];
        double [][] matrizConjunta = this.getMatrizConjunta();
        int suma=0;
        for (int c = 0; c <256; c++){
            for (int f =0; f<256 ; f++){
                suma += matrizConjunta[c][f];
            }
           ret[c] = suma;
        }
        return ret;
    }


    public double getRuido(){
        double ruidoAnterior = 0;
        double ruidoActual =0;
        int pruebas =0;

        while(!converge(ruidoActual,ruidoAnterior) && (pruebas < 10000)){

        }

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
        return i;
    }

    //public int generarSalida(){


    public boolean converge(double actual, double anterior){
        return ((Math.abs(actual-anterior)) <EPSILON);
    }



}






