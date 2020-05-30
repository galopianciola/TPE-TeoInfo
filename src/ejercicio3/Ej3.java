package ejercicio3;

import common.ImagenWill;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Ej3 {

    public void ejecutar(ImagenWill imagenOriginal,ImagenWill imagenPolicia,ImagenWill imagenMasParecida) {
        Huffman h1=new Huffman();

        System.out.println("\n---------------------");
        System.out.println("Ejercicio 3 \n");

        h1.generarCodigo(h1.calcularHuffman(imagenOriginal), "");
        h1.imprimirCodigo();

        System.out.println("\n -------");

        try {
            // Obtener bytes que contienen la secuencia original codificada
            byte[] byteArray = h1.codificar(imagenOriginal);

            // Guardar la codificación en un archivo binario
            FileOutputStream fos = new FileOutputStream("output.bin");
            fos.write(byteArray);
            //fos.write (anchoimagen)
            //fos.write(altoimagagen(
            //fos.write(tamaño en bits)
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}


