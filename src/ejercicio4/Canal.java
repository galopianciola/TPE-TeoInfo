package ejercicio4;


import common.ImagenWill;

import java.awt.*;
import java.util.ArrayList;


public class Canal {

    static final double MAXVALORMUESTRAS=100000;
    static final double EPSILON = 1e-4;
    private int tamanioX;
    private int tamanioY;
    private double[][] matrizConjunta;
    private int ancho;
    private int alto;
    private ArrayList<Double> muestraGrafico;


    public Canal(ImagenWill imgX, ImagenWill imgY) {
        this.ancho = imgX.getImagen().getWidth();
        this.alto = imgY.getImagen().getHeight();
        this.tamanioX=imgX.getCantTonos();
        this.tamanioY=imgY.getCantTonos();
        this.matrizConjunta=new double[this.tamanioY][this.tamanioX];
        cargarMatrizConjunta(imgX,imgY);
    }

    public void cargarMatrizConjunta(ImagenWill imgX,ImagenWill imgY){
        //tomo las imagenes por el cual voy a analizar el canal
        //y obtengo una matriz de frecuencia de colores
        for(int i=0; i < imgX.getImagen().getWidth(); i++){
            for(int j=0; j < imgX.getImagen().getHeight(); j++){

                Color colorPixelX = new Color(imgX.getImagen().getRGB(i, j));
                int valorPixelX = colorPixelX.getRed();

                Color colorPixelY = new Color(imgY.getImagen().getRGB(i, j));
                int valorPixelY = colorPixelY.getRed();

                //se dividen por la cantidad de colores que tienen las imagenes
                int columna = (int)(Math.floor(valorPixelX/this.tamanioX));
                int fila = (int)(Math.floor(valorPixelY/this.tamanioY));
                this.matrizConjunta[columna][fila] ++;
            }
        }
        //luego a la matriz de frecuencias las divido por el total de pixeles
        //para generar una matriz de probabilidades conjuntas
        for (int i=0;i<this.matrizConjunta.length;i++) {
            for (int j = 0; j < this.matrizConjunta[1].length; j++) {
                this.matrizConjunta[i][j]=this.matrizConjunta[i][j]/(this.ancho*this.alto);
            }
        }
    }


    public double getTotalColumna(int columna){
        double suma = 0;
        for(int fila = 0; fila < tamanioY; fila++) {
             suma += matrizConjunta[columna][fila];
        }
        return suma;
    }

    //este metodo genera la matriz condicional Canal(Y/X) a partir de la matriz conjunta
    public double [] [] generarMatrizCondicional() {
        double [][] ret = new double[tamanioX][tamanioY];
        for (int columnaActual = 0; columnaActual < tamanioX; columnaActual++) {
            //sumaColumna= marginal de X
            double sumaColumna = this.getTotalColumna(columnaActual);
            for (int filaActual = 0; filaActual < tamanioY; filaActual++) {
                if (matrizConjunta[columnaActual][filaActual] != 0) {
                    ret[columnaActual][filaActual] = matrizConjunta[columnaActual][filaActual]/ sumaColumna; //bayes
                }
            }
        }
        return ret;
    }


    public double getRuido(){
        double [][]matrizCondicional=generarMatrizCondicional();
        double [][] matrizConjuntaMuestreo=new double[tamanioX][tamanioY];
        double [] probabilidadX=new double[tamanioX];
        double ruidoAnt = -1;
        double ruidoActual =0;
        int muestras =0;
        int entrada;
        int salida;
        muestraGrafico=new ArrayList<>();
        while (!converge(ruidoAnt, ruidoActual) || muestras < 100000) {
            entrada = this.generarEntrada();
            salida = this.sig_dado_ant(entrada,matrizCondicional);
            matrizConjuntaMuestreo[salida][entrada]++;
            probabilidadX[entrada]++;
            ruidoAnt=ruidoActual;
            muestras++;
            ruidoActual=0;
            for (int i =0; i < this.tamanioX; i++) {
                double probMarginalX = probabilidadX[i]/muestras;
                for (int j =0; j < this.tamanioY; j++) {
                    if (matrizConjuntaMuestreo[j][i]>0) {
                        double probYdadoX = (matrizConjuntaMuestreo[j][i]/muestras)/probMarginalX;
                        ruidoActual += probMarginalX * (- probYdadoX * (Math.log10(probYdadoX)/Math.log10(2)));
                    }
                }
            }

        }
        return ruidoActual;
    }

    public double getRuidoAnalitico(){
        double out = 0;
        for (int columnaActual = 0; columnaActual < this.tamanioX; columnaActual++){
            double sumaColumna = this.getTotalColumna(columnaActual); //sumaColumna = marginal de X

            double aux = 0;
            for (int filaActual = 0; filaActual < this.tamanioY; filaActual++){
                double probYdadoX = this.generarMatrizCondicional()[columnaActual][filaActual];
                if (probYdadoX > 0) {
                    aux += probYdadoX * (Math.log10(probYdadoX) / Math.log10(2));
                }
            }
            out += sumaColumna * (-aux);
        }
        return out;
    }

    public double [] getProbAcumuladaX(){
        double [] ret = new double[tamanioX];
        double suma=0;
        for (int i = 0; i <tamanioX;i++){
            for (int j= 0; j< tamanioY ; j++){
                suma += this.matrizConjunta[i][j];
            }
            ret[i] = suma;
        }
        return ret;
    }

    public double [][] getMatrizProbAcumulada(double [][] matrizCondicional){
        double [][]ret=new double[tamanioX][tamanioY];
        double suma;
        for (int i=0;i<tamanioX;i++){
            suma=0;
            for(int j=0;j<tamanioY;j++){
                suma +=matrizCondicional[i][j];
                ret[i][j]=suma;
            }
        }
        return ret;
    }

    public int generarEntrada(){
        double r =  Math.random();
        for(int i = 0;i<tamanioX;i++) {
            if (r < getProbAcumuladaX()[i]){
                return i;
            }
        }
        return -1;
    }

    public int sig_dado_ant(int s_ant,double [][] matrizCondicional) {
        double r =  Math.random();
        for (int i = 0; i < tamanioY; i++) {
            // busco en la columna dado el anterior
            // el q coincida con la prob randomeada
            if (r < getMatrizProbAcumulada(matrizCondicional)[s_ant][i]) {
                return i;
            }
        }
        return -1;
    }


    public boolean converge(double anterior, double actual){
        muestraGrafico.add(actual-anterior);
        return ((Math.abs(actual-anterior)) <EPSILON);
    }

    public void generarGrafico(String nombre,String titulo){
        Grafico grafico=new Grafico(nombre,titulo,this.muestraGrafico);
    }

}







