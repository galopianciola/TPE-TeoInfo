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
        System.out.println("Ejercicio 3");
        Codificador codifica=new Codificador();
        Huffman h1=new Huffman(imagenOriginal.getArregloFrecuencia());

        //inciso a
        codifica.aplicarCodificacion(imagenOriginal,h1);
        System.out.println("Se ha comprimido la imagen original exitosamente indicando el inciso a)");

//in
       ciso b codifica.aplicarCodificacion(imagenMasParecida,h1);
        System.out.println("Se ha comprimido la imagen original exitosamente indicando el inciso b)");


        //inciso c codifica.aplicarCodificacion(imagenPolicia,h1);
        System.out.println("Se ha comprimido la imagen original exitosamente indicando el inciso c)");
        System.out.println("\n -------");

        //descompresion de las 3 imagenes
        Decodificador decodifica = new Decodificador();
        ImagenWill imagenOriginalRecuperada= decodifica.restaurarImagen(imagenOriginal.getNombreImagen()+".bin",imagenOriginal.getNombreImagen());
        decodifica.crearImagenBMP(imagenOriginalRecuperada);

        ImagenWill imagenMasParecidaRecuperada= decodifica.restaurarImagen(imagenMasParecida.getNombreImagen()+".bin",imagenMasParecida.getNombreImagen());
        decodifica.crearImagenBMP(imagenMasParecidaRecuperada);

        ImagenWill imagenPoliciaRecuperada= decodifica.restaurarImagen(imagenPolicia.getNombreImagen()+".bin",imagenPolicia.getNombreImagen());
        decodifica.crearImagenBMP(imagenPoliciaRecuperada);

        /////////


 //inciso d       Huffman h2 = new Huffman(imagenPolicia.getArregloFrecuencia());
        codifica.aplicarCodificacion(imagenPolicia, h2);

        System.out.println("Se ha comprimido la imagen original exitosamente indicando el inciso d)");

           }

}


