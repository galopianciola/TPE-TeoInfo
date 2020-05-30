package ejercicio3;

import common.ImagenWill;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;


public class Decodificador {
    //clave: color, valor: codigo
    private Hashtable<Integer,String> codigo = new Hashtable<>();
    private static int LONGITUDBUFFER = 8;


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
        Huffman h1=new Huffman(probabilidades);
        codigo=h1.getCodigoImagen();
        //aca genera el codigo huffman y lo guarda en el hash :)
        return null;
    }

}
