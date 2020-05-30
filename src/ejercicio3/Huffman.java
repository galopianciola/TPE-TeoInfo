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
    //clave: color, valor: codigo
    private Hashtable<Integer,String> codigo = new Hashtable<>();
    //aqui tendre la lista inicial de cada simbolo con su probabilidad
    private ArrayList<Nodo> simbolos = new ArrayList<>();
    private static int LONGITUDBUFFER = 8;


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


    public Nodo calcularHuffman(double[] frecuencias){
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


        //inicializo el arbol de codificacion con un nodo raiz
        Nodo raiz = null;

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
            raiz = nuevo;

            //agrego el nodo nuevo a la lista de simbolos
            //para que entre en "competencia", y volver a ordenar de mayor a menor
            this.simbolos.add(nuevo);
        }

        return raiz;
    }

    //todo: codificar a nivel bit
    public void generarCodigo(Nodo arbol, String codigo){
        //este metodo recorrera el arbol de codificacion, contando 0 o 1 segun la rama,
        //y asignando el codigo recorrido al simbolo en caso de llegar a una hoja

        //si es un nodo hoja (osea, un simbolo a codificar)
        if ((arbol.getIzq() == null) && (arbol.getDer() == null)){
            this.codigo.put(new Integer(arbol.getSimbolo()), codigo);
        } else { //si no es hoja
            //voy con recursion a izquierda (pongo 1 a la izq pq ahi esta el mayor), agregando 1 al codigo parcial
            generarCodigo(arbol.getIzq(), codigo + "1");

            //y voy con recursion a derecha, agregando 0 al codigo parcial pq ahi esta el menor
            generarCodigo(arbol.getDer(), codigo + "0");
        }
    }

    public ArrayList<Byte> codificarEnLista(ImagenWill imagen){
        ArrayList<String> code = new ArrayList<>(); //a codificar
        ArrayList<Byte> result = new ArrayList<>(); //resultado de codificacion
        int suma = 0;

        for (int i = 0; i < imagen.getImagen().getWidth(); i++) {
            for (int j = 0; j < imagen.getImagen().getHeight(); j++) {
                //primero obtengo el valor a codificar
                Color colorPixel = new Color(imagen.getImagen().getRGB(i, j));
                int valorPixel = colorPixel.getRed();
                suma =+ this.codigo.get(new Integer(valorPixel)).length(); //sumamos el tamanio de cada codigo de cada pixel
                code.add(this.codigo.get(new Integer(valorPixel))); //obtengo el codigo y lo agrego a la lista code de String
            }
        }
        imagen.setBitsCodificado(suma); //agregamos el tamanio calculado a la imagen
        //todo: aca debo pasarlo a Byte y agregarlo a result
        byte buffer = 0; //byte temporal que voy armando
        int bufferLength = 8; //size: byte
        int bufferPos = 0;

        for (String data: code){ //para todos los elementos String de la lista code
            for (int i = 0; i < data.length(); i++) { //para cada caracter de ese String
                char actual = data.charAt(i); //obtengo el char

                // La operación de corrimiento pone un '0'
                buffer = (byte) (buffer << 1);
                bufferPos++;

                //si era un 0 ya queda,
                //y si era un 1 (osea que necesito cambiarlo)
                if (actual == '1'){
                    //al hacer OR con mascara 1 se va a volver 1 ese ultimo bit
                    buffer = (byte) (buffer | 1);  // 0 0 0 0 0 0 0 1
                }

                //si se completo el byte temporal actual
                if (bufferPos == bufferLength) {
                    //Byte aux = new Byte(buffer);
                    //System.out.println(aux.toString());
                    result.add(buffer); //lo agrego a la lista de bytes codificados
                    buffer = 0; //y reinicio el buffer
                    bufferPos = 0;
                }

                //si no se completa el byte temporal porque es el ultimo byte a llenar...
                //no completo el byte, estoy en el ultimo caracter del string, y ademas no hay mas strings
                if ((bufferPos != bufferLength) && (i == data.length()-1) && (code.indexOf(data)+1 == code.size())){
                    buffer = (byte) (buffer << (bufferLength- bufferPos)); // nos aseguramos que los 0s inservibles queden al final
                    //CUESTION DEL DECODIFICADOR BRO
                    //porque siempre inicializo el buffer en 0
                }


                //esos bits que me faltan para llenar el byte, los represento con 0s que sean discernibles para el decoder.
                //el decodificador va a recibir el tamanio del codigo a decodificar y dividirlo por 8.
                //el resto de la division sera cuantos bits invalidos debera desconsiderar.
            }
        }

        return result;
    }

    private static byte[] convertirAPrimitivo(ArrayList<Byte> input) {
        byte[] ret = new byte[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }

        return ret;
    }

    public byte[] codificar (ImagenWill imagen){
        ArrayList<Byte> lis = codificarEnLista(imagen);
        return convertirAPrimitivo(lis);

    }

    public static char[] extraerArchivo(String inputFilepath, int cantBits) {
        char[] restoredSequence = new char[cantBits];

        try {
            byte[] inputSequence = Files.readAllBytes(new File(inputFilepath).toPath());

            int globalIndex = 0;
            byte mask = (byte) (1 << (LONGITUDBUFFER - 1)); // mask: 10000000
            int bufferPos = 0;

            int i = 0; // indice en la lista de bytes (secuencia codificada)
            while (globalIndex < cantBits)
            {
                byte buffer = inputSequence[i];
                while (bufferPos < LONGITUDBUFFER) {

                    if ((buffer & mask) == mask) {  // 10000000
                        restoredSequence[globalIndex] = '1';
                    } else {
                        restoredSequence[globalIndex] = '0';
                    }

                    buffer = (byte) (buffer << 1);
                    bufferPos++;
                    globalIndex++;

                    if (globalIndex == cantBits) {
                        break;
                    }
                }

                i++;
                bufferPos = 0;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return restoredSequence;
    }

    public ImagenWill decodificar(String archivo, int cantBits, int ancho, int alto, double[] probabilidades){
        char[] secuencia = extraerArchivo(archivo,cantBits);
        if ((secuencia.length % 8) != 0){
            int aDescartar = secuencia.length % 8;
        }

        //calculo Huffman (pasandole las probabilidades)
        //llamo a generarCodigo para ir recorriendo el arbol y obtener el codigo (recibe el arbol obtenido y "")
        //todo: como necesito otra hashtable propia del decodificador, SEPARAME TODO PERRI
    }


    public void imprimirCodigo(){
        //obtengo todas las claves del hash
        Enumeration claves = this.codigo.keys();

        //obtengo todos los valores del hash
        Enumeration valores = this.codigo.elements();

        while (claves.hasMoreElements()){
            System.out.println(claves.nextElement().toString() + ": " + valores.nextElement().toString());
        }
    }


    //TODO:aplicar ese hash a todos los pixeles de la imagen y guardarlo en archivo
}