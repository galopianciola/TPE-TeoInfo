package ejercicio3;

import common.ImagenWill;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Ej3 {

    public void ejecutar(ImagenWill imagenOriginal,ImagenWill imagenPolicia,ImagenWill imagenMasParecida) {

        System.out.println("\n---------------------");
        System.out.println("Ejercicio 3 \n");
        Codificador codifica=new Codificador();
        codifica.aplicarCodificacion(imagenOriginal);

        System.out.println("\n -------");


    }

}


