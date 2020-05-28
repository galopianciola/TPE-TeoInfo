package ejercicio3;

import common.ImagenWill;


public class Ej3 {

    public void ejecutar(ImagenWill imagenOriginal,ImagenWill imagenPolicia,ImagenWill imagenMasParecida) {
        Huffman h1=new Huffman();
        for (int i=0;i<16;i++)
            System.out.println(h1.getArregloProbabilidades(imagenOriginal)[i]);

    }

}


