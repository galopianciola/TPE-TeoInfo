package ejercicio3;

import common.ImagenWill;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Hashtable;


public class Decodificador {
    //clave: color, valor: codigo
    private Hashtable<Integer,String> codigo = new Hashtable<>();
    private static int LONGITUDBUFFER = 8;


    public static char[] bytesDecoding(String inputFilepath, int cantBits) {
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

    public ImagenWill decodificar(String ruta){
        try {
            DataInputStream imgCodificada = new DataInputStream(new FileInputStream(ruta));

            //primero traigo los datos del header
            int alto = imgCodificada.readInt();
            int ancho = imgCodificada.readInt();
            System.out.println("decodificacion" + alto);
            System.out.println(ancho);
            int cantColores = imgCodificada.readInt();

            double[] frecuencias = new double[256];
            //inicializo el arreglo en 0
            for (int i = 0; i < frecuencias.length; i++){
                frecuencias[i] = 0;
            }

            //despues traigo cada color con su frecuencia (de ahi armare su probabilidad)
            for (int i=0; i < cantColores; i++){
                int color = imgCodificada.readInt();
                int frecuencia = imgCodificada.readInt();
                System.out.println(color + ", " + frecuencia);
                frecuencias[color] = frecuencia;
            }

            //genero el codigo mediante huffman (teniendo como dato las frecuencias de cada color, que luego seran probabilidades)
            Huffman huffman = new Huffman(frecuencias);
            this.codigo = huffman.getCodigoImagen();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
