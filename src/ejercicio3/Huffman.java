package ejercicio3;

import common.ImagenWill;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;

public class Huffman {
    //aqui estara el codigo resultante de ejecutar Huffman a una imagen.
    private Nodo arbol;
    //aqui tendre la lista inicial de cada simbolo con su probabilidad
    private ArrayList<Nodo> simbolos = new ArrayList<>();
    private double[]frecuencias;
    public Huffman(double[]arregloFrecuencias){
        this.frecuencias=arregloFrecuencias;
    }



    public double[] getProbabilidades(double[] frecuencias){
        int sumaTotal = 0;
        for(int i=0;i <frecuencias.length;i++){
            sumaTotal += frecuencias[i];
        }

        double[] output = new double[256];

        for (int i = 0; i < frecuencias.length; i++){
            double probFrecuencia = frecuencias[i]/sumaTotal;
            output[i] = probFrecuencia;
        }

        return output;
    }


    public void getNodosIniciales(double[] arregloFrecuencias){
        for (int i = 0; i < getProbabilidades(arregloFrecuencias).length; i++){
            //solo voy a considerar los colores que aparecen mas de 0 veces
            if (getProbabilidades(arregloFrecuencias)[i] != 0){
                //agrego a la lista el color, junto con su probabilidad de aparicion en la imagen
                this.simbolos.add(new Nodo(i, getProbabilidades(arregloFrecuencias)[i], null, null));
            }
        }
    }


    public void calcularHuffman(double[] frecuencias){
        //en este metodo obtendre el arbol de codificacion, para luego codificar recorriendolo con 0s y 1s

        //ejecuto este metodo para obtener en la lista this.simbolos los objetos Nodo iniciales (pares simbolo - probabilidad)
        this.getNodosIniciales(frecuencias);

        //genero un comparador que luego me ayude a ordenar la lista de simbolo-probabilidad segun probabilidad
        Comparator comparaProbabilidades = new Comparator<Nodo>(){
            @Override
            public int compare(Nodo o1, Nodo o2) {
                return new Double(o1.getProb()).compareTo(o2.getProb());
            }
        };

        this.simbolos.sort(comparaProbabilidades);




        //mientras tenga mas de un simbolo en la lista
        //es decir, que no termine de codificar (debo llegar finalmente a un solo nodo con prob=1)
        while (this.simbolos.size() > 1){
            //ordeno la lista de mayor a menor
            this.simbolos.sort(comparaProbabilidades);

            //tomo los dos nodos de menor probabilidad
            //aclaracion: al usar remove(0), se toma el primer elemento y se reacomoda la lista (corriendo a izq)
            Nodo x = this.simbolos.remove(0);
            Nodo y = this.simbolos.remove(0);

            //creo un nuevo nodo intermedio, sumando los dos tomados.
            //por ser intermedio, pongo el simbolo en null.
            //los hijos de este nodo seran los que sumé para formar su probabilidad.
            //aclaracion: que un nodo tenga simbolo -1 significa que es un intermedio
            //otra aclaracion: en la rama izquierda siempre va el nodo mayor (como en la hoja)
            Nodo nuevo = new Nodo(-1, x.getProb()+y.getProb(), y, x);

            //el nodo nuevo sera la raiz del arbol de codificacion ahora
            this.arbol = nuevo;

            //agrego el nodo nuevo a la lista de simbolos
            //para que entre en "competencia", y volver a ordenar de mayor a menor
            this.simbolos.add(nuevo);
        }
    }


    //todo: codificar a nivel bit
    public void generarCodigo(Nodo arbol, String codigo,Hashtable<Integer,String>hash){
        //este metodo recorrera el arbol de codificacion, contando 0 o 1 segun la rama,
        //y asignando el codigo recorrido al simbolo en caso de llegar a una hoja

        //si es un nodo hoja (osea, un simbolo a codificar)
        if ((arbol.getIzq() == null) && (arbol.getDer() == null)){
            hash.put(new Integer(arbol.getSimbolo()), codigo);
        } else { //si no es hoja
            //voy con recursion a izquierda (pongo 1 a la izq pq ahi esta el mayor), agregando 1 al codigo parcial
            generarCodigo(arbol.getIzq(), codigo + "1",hash);

            //y voy con recursion a derecha, agregando 0 al codigo parcial pq ahi esta el menor
            generarCodigo(arbol.getDer(), codigo + "0",hash);
        }
    }

    public Hashtable getCodigoImagen(){
        Hashtable<Integer,String>output=new Hashtable<>();
        calcularHuffman(this.frecuencias);
        generarCodigo(this.arbol,"",output);
        return output;
    }

}
