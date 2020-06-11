package ejercicio3;

import common.ImagenWill;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Codificador {
    //clave: color, valor: codigo
    private Hashtable<Integer,String> codigo = new Hashtable<>();
    private int bitsValidos;

    public ArrayList<Byte> codificarEnLista(ImagenWill imagen,double[]arregloFrecuencia){
        Huffman h1=new Huffman(arregloFrecuencia);
        codigo=h1.getCodigoImagen();
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
        int longitudBits = 0;
        for (String data: code){ //para todos los elementos String de la lista code
            for (int i = 0; i < data.length(); i++) { //para cada caracter de ese String
                char actual = data.charAt(i); //obtengo el char

                // La operación de corrimiento pone un '0'
                buffer = (byte) (buffer << 1);
                bufferPos++;
                longitudBits++;
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
                }


                //esos bits que me faltan para llenar el byte, los represento con 0s que sean discernibles para el decoder.
                //el decodificador va a recibir el tamanio del codigo a decodificar y dividirlo por 8.
                //el resto de la division sera cuantos bits invalidos debera desconsiderar.
            }
        }
        this.bitsValidos = longitudBits;
        return result;
    }

    private static byte[] convertirAPrimitivo(ArrayList<Byte> input) {
        byte[] ret = new byte[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }

        return ret;
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

    public byte[] codificarImagen (ImagenWill imagen,double[] arregloFrecuencia){
        ArrayList<Byte> lis = codificarEnLista(imagen,arregloFrecuencia);
        return convertirAPrimitivo(lis);

    }


    public FileOutputStream aplicarCodificacion(ImagenWill imagen){

        byte[] imagenCodificada=codificarImagen(imagen,imagen.getArregloFrecuencia());

        Header head= new Header(imagen.getImagen().getHeight(),imagen.getImagen().getWidth(),imagen.getArregloFrecuencia(),bitsValidos);
        //codificarHead(head);

        return null;
    }


}
