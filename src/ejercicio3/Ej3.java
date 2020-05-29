package ejercicio3;

import common.ImagenWill;


public class Ej3 {

    public void ejecutar(ImagenWill imagenOriginal,ImagenWill imagenPolicia,ImagenWill imagenMasParecida) {
        Huffman h1=new Huffman();

        System.out.println("\n---------------------");
        System.out.println("Ejercicio 3 \n");

        h1.generarCodigo(h1.calcularHuffman(imagenOriginal), "");
        h1.imprimirCodigo();
    }

}


