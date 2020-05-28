package ejercicio3;

import common.ImagenWill;

import java.util.Hashtable;

public class Huffman {
    //TODO:recalcular el arreglo probabilidades
    public double[] getArregloProbabilidades(ImagenWill imagen){
        int inicio=0;
        int pos=0;
        double[] arreglo= new double[16];
        while (inicio < 16) { // hasta llegar hasta la ultima posicion del arregloIntervalo
            for (int x = 0; x < 16; x++) { //pasa los siguientes 16 valores del arregloDistribucion
                arreglo[inicio] += imagen.getArregloFrecuencia()[pos]/imagen.getCantPixeles();
                pos++;
            }
            inicio ++;
        }

        return arreglo;
    }
    //TODO:hacer huffman (ver como estructurar el arbol)
    public Hashtable<Integer,Integer> calcularHuffman(){
        return null;
    }

    //TODO:crear hash (clave=color;valor=codigo)
    //TODO:aplicar ese hash a todos los pixeles de la imagen y guardarlo en archivo
}
