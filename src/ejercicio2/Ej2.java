package ejercicio2;

import common.ImagenWill;
import ejercicio1.Ej1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ej2 {
    private ImagenWill imagenOriginal;
    private ImagenWill imagenMasParecida;
    private ImagenWill imagenPolicia;

    public Ej2(ImagenWill imagenMasParecida) {
        //obtengo la imagen mas parecida arrojada por la busqueda del ej1
        this.imagenMasParecida = imagenMasParecida;
    }

    public void ejecutar(){
        //primero cargo las imagenes

        try{
            //cargo la original
            this.imagenOriginal = new ImagenWill(ImageIO.read(new File("ImagenesWill/Will(Original).bmp")), "Imagen original");

            //la mas parecida ya esta cargada pq vino en el constructor

            this.imagenPolicia = new ImagenWill(ImageIO.read(new File("ImagenesWill/Will_ej2.bmp")), "Imagen policia");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n---------------------");
        System.out.println("Ejercicio 2\n");

        MediaDesvio calculador = new MediaDesvio();
        System.out.println("Media de " + this.imagenOriginal.getNombreImagen() + ": " + calculador.calcularMedia(this.imagenOriginal.getImagen()));
        System.out.println("Media de " + this.imagenMasParecida.getNombreImagen() + ": " + calculador.calcularMedia(this.imagenMasParecida.getImagen()));
        System.out.println("Media de " + this.imagenPolicia.getNombreImagen() + ": " + calculador.calcularMedia(this.imagenPolicia.getImagen()));

        System.out.println("\nDesvio estandar de " + this.imagenOriginal.getNombreImagen() + ": " + calculador.calcularDesvio(this.imagenOriginal.getImagen()));
        System.out.println("Desvio estandar " + this.imagenMasParecida.getNombreImagen() + ": " + calculador.calcularDesvio(this.imagenMasParecida.getImagen()));
        System.out.println("Desvio estandar " + this.imagenPolicia.getNombreImagen() + ": " + calculador.calcularDesvio(this.imagenPolicia.getImagen()));
    }

}
