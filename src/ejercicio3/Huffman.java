package ejercicio3;

import common.ImagenWill;

import java.awt.*;
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

    public void getNodosIniciales(ImagenWill imagen){

        for (int i = 0; i < imagen.getArregloFrecuencia().length; i++){
            //solo voy a considerar los colores que aparecen mas de 0 veces
            if (imagen.getArregloFrecuencia()[i] != 0){
                //agrego a la lista el color, junto con su probabilidad de aparicion en la imagen
                double probFrecuencia = imagen.getArregloFrecuencia()[i]/imagen.getCantPixeles();
                this.simbolos.add(new Nodo(i, probFrecuencia, null, null));
            }
        }

    }

    public Nodo calcularHuffman(ImagenWill imagenOriginal){
        //en este metodo obtendre el arbol de codificacion, para luego codificar recorriendolo con 0s y 1s

        //ejecuto este metodo para obtener en la lista this.simbolos los objetos Nodo iniciales (pares simbolo - probabilidad)
        this.getNodosIniciales(imagenOriginal);

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
            // *17 de ruta para retomar los valores de colores originales
            this.codigo.put(new Integer(arbol.getSimbolo()), codigo);
        } else { //si no es hoja
            //voy con recursion a izquierda (pongo 1 a la izq pq ahi esta el mayor , agregando 0 al codigo parcial
            generarCodigo(arbol.getIzq(), codigo + "1");

            //y voy con recursion a derecha, agregando 0 al codigo parcial pq ahi esta el menor
            generarCodigo(arbol.getDer(), codigo + "0");
        }
    }

    public ArrayList<Byte> codificar(ImagenWill imagen){
        ArrayList<String> code = new ArrayList<>(); //a codificar
        ArrayList<Byte> result = new ArrayList<>(); //resultado de codificacion


        for (int i = 0; i < imagen.getImagen().getWidth(); i++) {
            for (int j = 0; j < imagen.getImagen().getHeight(); j++) {
                //primero obtengo el valor a codificar
                Color colorPixel = new Color(imagen.getImagen().getRGB(i, j));
                int valorPixel = colorPixel.getRed();

                //obtengo el codigo y lo agrego a la lista code de Strings
                code.add(this.codigo.get(new Integer(valorPixel)));

                break;
            }
            break;
        }

        //todo: aca debo pasarlo a Byte y agregarlo a result
        byte buffer = 0; //byte temporal que voy armando
        int bufferLength = 8;
        int bufferPos = 0;

        //para todos los elementos String de la lista code
        for (String data: code){

            //si se completo el byte temporal actual
            if (bufferPos == bufferLength) {

            }

        }

        return null;
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
