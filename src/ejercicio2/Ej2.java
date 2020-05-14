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
            System.out.println(this.imagenOriginal.getNombreImagen());

            //la mas parecida ya esta cargada
            System.out.println(this.imagenMasParecida.getNombreImagen());

            this.imagenPolicia = new ImagenWill(ImageIO.read(new File("ImagenesWill/Will_ej2.bmp")), "Imagen policia");
            System.out.println(this.imagenPolicia.getNombreImagen());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
