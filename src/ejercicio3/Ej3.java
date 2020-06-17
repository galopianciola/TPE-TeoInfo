package ejercicio3;

import common.ImagenWill;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Ej3 {

    public void ejecutar(ImagenWill imagenOriginal,ImagenWill imagenPolicia,ImagenWill imagenMasParecida){

        System.out.println("\n---------------------");
        System.out.println("Ejercicio 3 \n");
        Codificador codifica=new Codificador();
        //codifica.imprimirCodigo();
        //codifica.aplicarCodificacion(imagenOriginal);

        System.out.println("\n -------");

        Decodificador decodifica = new Decodificador();
        ImagenWill imagenRecuperada= decodifica.restaurarImagen("output.bin");


       File outputfile = new File("imagenRecuperada.bmp");
        try {
            ImageIO.write(imagenRecuperada.getImagen(), "bmp", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


