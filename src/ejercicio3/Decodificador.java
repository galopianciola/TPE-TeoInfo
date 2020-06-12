package ejercicio3;

import common.ImagenWill;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private static byte[] inputSequence;
    private double[] frecuencias = new double[256];


    public BufferedImage bytesDecoding() {
        BufferedImage out=new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
        byte mask = (byte) (1 << (LONGITUDBUFFER - 1)); // mask: 10000000
        int bufferPos = 0;
        String temporal="";
        int k=0; // indice en el arreglo de bytes (secuencia codificada)
        //para todos los pixeles a recuperar
        for(int i=0;i<ancho;i++) {
            for (int j = 0; j < alto; j++) {
                byte buffer = inputSequence[k];

                while (bufferPos < LONGITUDBUFFER) {
                    //si es un uno
                    if ((buffer & mask) == mask) {  // 10000000
                        temporal = temporal+"1";
                    } else { //si es un 0
                        temporal = temporal+"0";
                    }
                    //si los bits recuperados hasta ahora representan un codigo
                    if(codigo.contains(temporal)){
                        Color colorPixel = null;
                        Enumeration claves = this.codigo.keys();
                        Enumeration valores = this.codigo.elements();
                        while (claves.hasMoreElements()){
                            Integer clave= (Integer) claves.nextElement();
                            String valor= (String) valores.nextElement();
                            if (valor.equals(temporal)){
                                colorPixel = new Color(clave,clave,clave);
                            }
                        }
                        out.setRGB(i,j,colorPixel.getRGB());
                        temporal="";
                    }

                    //para leer el siguiente bit
                    buffer = (byte) (buffer << 1);
                    bufferPos++;
                }

                k++;
                bufferPos = 0;
            }
        }
        return out;
    }

    public void decodificar(String ruta){
        try {
            DataInputStream imgCodificada = new DataInputStream(new FileInputStream(ruta));

            //primero traigo los datos del header
            this.alto = imgCodificada.readInt();
            this.ancho = imgCodificada.readInt();
            int cantColores = imgCodificada.readInt();


            //inicializo el arreglo en 0
            for (int i = 0; i < frecuencias.length; i++){
                frecuencias[i] = 0;
            }

            //despues traigo cada color con su frecuencia (de ahi armare su probabilidad)
            for (int i=0; i < cantColores; i++){
                int color = imgCodificada.readInt();
                int frecuencia = imgCodificada.readInt();
                frecuencias[color] = frecuencia;
            }

            //genero el codigo mediante huffman (teniendo como dato las frecuencias de cada color, que luego seran probabilidades)
            Huffman huffman = new Huffman(frecuencias);
            this.codigo = huffman.getCodigoImagen();

            //TODO:terminar de pasar por archivo el arreglo de bytes
            ArrayList<Byte> bytes = new ArrayList<>();
            while (imgCodificada.available()>0) {
                bytes.add(imgCodificada.readByte());
            }

            this.inputSequence = convertirAPrimitivo(bytes);
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


    //metodo principal
    public ImagenWill restaurarImagen(String ruta){
        decodificar(ruta);
        return new ImagenWill(bytesDecoding(),ruta+" restaurada");
    }

}
