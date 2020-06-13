package ejercicio3;

import common.ImagenWill;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Decodificador {
    //clave: color, valor: codigo
    private Hashtable<Integer,String> codigo = new Hashtable<>();
    private static int LONGITUDBUFFER = 8;
    private int alto;
    private int ancho;
    private byte[] inputSequence;
    private double[] frecuencias = new double[256];


    public BufferedImage bytesDecoding() {
        BufferedImage out=new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);

        byte mask = (byte) (1 << (LONGITUDBUFFER - 1)); // mask: 10000000
        int bufferPos = 0;
        String temporal="";
        int k=0; // indice en el arreglo de bytes (secuencia codificada)
        int i=0; // indice ancho de imagen
        int j=0; // indice alto de imagen
        while ((k<inputSequence.length) && (i*j < ancho*alto)){ //para todos los bytes a decodificar
                byte buffer = inputSequence[k];
                while (bufferPos < LONGITUDBUFFER) {
                    //si es un uno
                    if ((buffer & mask) == mask) {  // 10000000
                        temporal = temporal + "1";
                    } else { //si es un 0
                        temporal = temporal + "0";
                    }

                    //si los bits recuperados hasta ahora representan un codigo
                    if (codigo.contains(temporal)) {
                        Color colorPixel = null;
                        Enumeration claves = this.codigo.keys();
                        Enumeration valores = this.codigo.elements();
                        while (claves.hasMoreElements()) {
                            Integer clave = (Integer) claves.nextElement();
                            String valor = (String) valores.nextElement();

                            if (valor.equals(temporal)) {
                                colorPixel = new Color(clave, clave, clave);
                                break;
                            }
                        }
                        if (i < ancho) { // si no llegue al final del ancho
                            if (j < alto) { //y tampoco del alto
                                out.setRGB(i, j, colorPixel.getRGB());
                                temporal = "";
                                j++; //encontre el color del pixel, avanzo al proximo (pero aun no termine de leer el byte)
                            } else { // llegue al final del alto e inserto el pixel en la siguiente posicion del ancho
                                j = 0; //reiniciando el alto
                                i++; //avanzando en ancho
                                if (i==ancho) {
                                    return out;
                                }else{
                                    out.setRGB(i, j, colorPixel.getRGB());
                                    temporal = "";
                                }
                             }
                        }

                        //para leer el siguiente bit
                        buffer = (byte) (buffer << 1);
                        bufferPos++;
                    }
                }
                k++;
                bufferPos = 0;

        }
        return null;
    }

    public void decodificar(String ruta){
        try {
            DataInputStream imgCodificada = new DataInputStream(new FileInputStream(ruta));
            byte[]input = Files.readAllBytes(new File(ruta).toPath());

            int contador=0; //aca ire contando la cantidad de enteros que voy leyendo

            //primero traigo los datos del header
            this.alto = imgCodificada.readInt();
            this.ancho = imgCodificada.readInt();
            int cantColores = imgCodificada.readInt();
            contador = contador + 3; //porque lei 3 enteros


            //inicializo en 0 el arreglo de frecuencias a obtener
            for (int i = 0; i < frecuencias.length; i++){
                frecuencias[i] = 0;
            }

            //despues traigo cada color con su frecuencia (de ahi armare su probabilidad)
            for (int i=0; i < cantColores; i++){
                int color = imgCodificada.readInt();
                int frecuencia = imgCodificada.readInt();
                frecuencias[color] = frecuencia;
                contador = contador + 2; //se leen 2 enteros por iteracion
            }

            //genero el codigo mediante huffman (teniendo como dato las frecuencias de cada color, que luego seran probabilidades)
            Huffman huffman = new Huffman(frecuencias);
            this.codigo = huffman.getCodigoImagen();

            contador=contador*4; //porque cada entero pesa 4 bytes

            //tamaño del arreglo del byte es de la suma total de bytes (includo el header) - el header (en bytes)
            this.inputSequence= new byte[input.length-contador];

            //recorro los bytes restantes del archivo tenido en cuenta por la posicion del contador
            for(int i=0;i<input.length-contador;i++){
                this.inputSequence[i]=input[i+contador];
            }

            imgCodificada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] convertirAPrimitivo(ArrayList<Byte> input) {
        byte[] ret = new byte[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }

        return ret;
    }


    //METODO PRINCIPAL
    public ImagenWill restaurarImagen(String ruta){
        decodificar(ruta);
        return new ImagenWill(bytesDecoding()," restaurada");
    }

}
