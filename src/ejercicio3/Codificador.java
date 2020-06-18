package ejercicio3;

import common.ImagenWill;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Codificador {
    //clave: color, valor: codigo
    private Hashtable<Integer,String> codigo = new Hashtable<>();


    public ArrayList<Byte> codificarEnLista(ImagenWill imagen,Huffman h1){

        codigo=h1.getCodigoImagen();
        ArrayList<String> code = new ArrayList<>(); //a codificar
        ArrayList<Byte> result = new ArrayList<>(); //resultado de codificacion


        for (int i = 0; i < imagen.getImagen().getWidth(); i++) {
            for (int j = 0; j < imagen.getImagen().getHeight(); j++) {
                //primero obtengo el valor a codificar
                Color colorPixel = new Color(imagen.getImagen().getRGB(i, j));
                int valorPixel = colorPixel.getRed();
                code.add(this.codigo.get(new Integer(valorPixel))); //obtengo el codigo y lo agrego a la lista code de String
            }
        }


        byte buffer = 0; //byte temporal que voy armando
        int bufferLength = 8; //size: byte
        int bufferPos = 0;
        int contadorCodigos=0;
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
                    result.add(buffer); //lo agrego a la lista de bytes codificados
                    buffer = 0; //y reinicio el buffer
                    bufferPos = 0;
                }

                //si no se completa el byte temporal porque es el ultimo byte a llenar...
                //no completo el byte, estoy en el ultimo caracter del string, y ademas no hay mas strings

                if ((bufferPos < bufferLength) && (i == data.length()-1) && (contadorCodigos == code.size()-1)){
                    buffer = (byte) (buffer << (bufferLength- bufferPos)); // nos aseguramos que los 0s inservibles queden al final
                    result.add(buffer); //lo agrego a la lista de bytes codificados//lo agrego a la lista de bytes codificados result.add(buffer);

                }
            }
            contadorCodigos++;
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


    public void imprimirCodigo(){
        //obtengo todas las claves del hash
        Enumeration claves = this.codigo.keys();

        //obtengo todos los valores del hash
        Enumeration valores = this.codigo.elements();

        while (claves.hasMoreElements()){
            System.out.println(claves.nextElement().toString() + ": " + valores.nextElement().toString());
        }
    }

    public byte[] codificarImagen (ImagenWill imagen,Huffman h1){
        ArrayList<Byte> lis = codificarEnLista(imagen,h1);
        return convertirAPrimitivo(lis);

    }

    //METODO PRINCIPAL
    public void aplicarCodificacion(ImagenWill imagen,Huffman h1, String rutaBin){
        try {
            FileOutputStream fos= new FileOutputStream(rutaBin); //output.bin
            DataOutputStream dos = new DataOutputStream(fos); //helper para armar el .bin
            byte[] imagenCodificada=codificarImagen(imagen,h1);

            //formalizo el header y lo meto (entero a entero) en el .bin
            Header head = new Header(imagen.getImagen().getHeight(),imagen.getImagen().getWidth(),imagen.getArregloFrecuencia());
            dos.writeInt(head.getAlto());
            dos.writeInt(head.getAncho());
            dos.writeInt(head.getCantColor());

            //meto en el .bin las frecuencias para que el decoder rearme las probabilidades (y despues el codigo)
            for (int i=0;i<imagen.getArregloFrecuencia().length;i++){
                if(imagen.getArregloFrecuencia()[i]!=0) {
                    dos.writeInt(i); //color
                    dos.writeInt(head.getFrecuenciaColor(i)); //frecuencia (cant de repeticiones) de esecolor
                }
            }

            //ahora meto los bytes de la imagen codificada
            dos.write(imagenCodificada);
            dos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
