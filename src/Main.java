import common.ImagenWill;
import ejercicio1.Ej1;
import ejercicio2.Ej2;
import ejercicio3.Ej3;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ImagenWill imagenOriginal = null;
        ImagenWill imagenPolicia = null;

        try {
            //guardo la imagen de will original
            imagenOriginal=new ImagenWill(ImageIO.read(new File("ImagenesWill/Will(Original).bmp")), "Imagen original");

            //guardo la imagen del policia
            imagenPolicia =new ImagenWill(ImageIO.read(new File("ImagenesWill/Will_ej2.bmp")), "Imagen policia");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        Ej1 ej1 = new Ej1();
        ej1.ejecutar(imagenOriginal.getImagen());

        //guardo la imagen mas parecida segun el sistema de busqueda para realizar los otros puntos
        ImagenWill imagenMasParecida=ej1.getImagenMasParecida();

        Ej2 ej2 = new Ej2();
        ej2.ejecutar(imagenOriginal,imagenPolicia,imagenMasParecida);

        Ej3 ej3 = new Ej3();
        ej3.ejecutar(imagenOriginal,imagenPolicia,imagenMasParecida);

    }
}
