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
        Codificador codificaH1=new Codificador();
        Huffman h1=new Huffman(imagenOriginal.getArregloFrecuencia());

        //inciso a
        codificaH1.aplicarCodificacion(imagenOriginal, h1, imagenOriginal.getNombreImagen()+".bin");
        System.out.println("Se ha comprimido la imagen original exitosamente indicando el inciso a)");

        //inciso b
        codificaH1.aplicarCodificacion(imagenMasParecida,h1, imagenMasParecida.getNombreImagen()+".bin");
        System.out.println("Se ha comprimido la imagen mas parecida (con codigo de imagen original) exitosamente indicando el inciso b)");


        //inciso c
        codificaH1.aplicarCodificacion(imagenPolicia,h1,imagenPolicia.getNombreImagen()+".bin");
        System.out.println("Se ha comprimido la imagen traida por el policia (con codigo de imagen original) exitosamente indicando el inciso c)");
        System.out.println("\n -------");

        //descompresion de las 3 imagenes
        Decodificador decodificaH1 = new Decodificador();
        ImagenWill imagenOriginalRecuperada= decodificaH1.restaurarImagen(imagenOriginal.getNombreImagen()+".bin",imagenOriginal.getNombreImagen());
        decodificaH1.crearImagenBMP(imagenOriginalRecuperada);

        ImagenWill imagenMasParecidaRecuperada= decodificaH1.restaurarImagen(imagenMasParecida.getNombreImagen()+".bin",imagenMasParecida.getNombreImagen());
        decodificaH1.crearImagenBMP(imagenMasParecidaRecuperada);

        ImagenWill imagenPoliciaRecuperada= decodificaH1.restaurarImagen(imagenPolicia.getNombreImagen()+".bin",imagenPolicia.getNombreImagen());
        decodificaH1.crearImagenBMP(imagenPoliciaRecuperada);


        //inciso d
        Codificador codificaH2 = new Codificador();
        Huffman h2 = new Huffman(imagenPolicia.getArregloFrecuencia());

        codificaH2.aplicarCodificacion(imagenPolicia, h2, imagenPolicia.getNombreImagen()+" - propio codigo.bin");
        System.out.println("Se ha comprimido la imagen traida por el policia (con codigo propio) exitosamente indicando el inciso d)");

        //descompresion inciso d
        Decodificador decodificaH2 = new Decodificador();

        ImagenWill imagenPoliciaRecuperadaH2=decodificaH2.restaurarImagen(imagenPolicia.getNombreImagen()+" - propio codigo.bin",imagenPolicia.getNombreImagen()+" - propio codigo");
        decodificaH2.crearImagenBMP(imagenPoliciaRecuperadaH2);
    }

}


