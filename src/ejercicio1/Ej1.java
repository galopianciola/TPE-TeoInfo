package ejercicio1;

import common.ImagenWill;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Ej1 {
    private ArrayList<ImagenWill> listaWillBusqueda = new ArrayList<>();
    private ImagenWill imagenMasParecida;

    public void ejecutar(BufferedImage willOriginal){
        //genero el calculador de factor de correlacion cruzada
        FactorCorrelacionCruzada calculador = new FactorCorrelacionCruzada();
        try {
            for (int i = 1; i <= 5; i++) {
                BufferedImage will = ImageIO.read(new File("ImagenesWill/Will_" + i + ".bmp"));

                //se agregara en los indices de 0 a 4
                listaWillBusqueda.add(new ImagenWill(will,"Imagen " + i));
                listaWillBusqueda.get(i-1).setFactorCorrelacionCruzadaConOriginal(calculador.calcularFactorCorrelacionCruzada(willOriginal, will));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //ahora genero un comparador que me ayude a ordenar la lista de info de las imagenes, segun su factor de correlacion
        Comparator comparadorPorFactor = new Comparator<ImagenWill>(){
            @Override
            public int compare(ImagenWill o1, ImagenWill o2) {
                //uso clase Wrapper de Double para poder usar el metodo compareTo
                return new Float(o2.getFactorCorrelacionCruzada()).compareTo(o1.getFactorCorrelacionCruzada());
            }
        };

        //ordeno la lista de info imagenes, de mayor a menor por Factor
        listaWillBusqueda.sort(comparadorPorFactor);

        //ahora que la lista esta ordenada, devuelvo el 1er elemento
        //como esta ordenada de mayor a menor, va a ser la mas parecida
        //se necesita para el ej2
        this.imagenMasParecida = listaWillBusqueda.get(0);

        System.out.println("\nEjercicio 1 \n");

        //imprimo la lista de la busqueda, ahora ordenada
        for (int i = 0; i < 5; i++) {
            System.out.println(listaWillBusqueda.get(i).getNombreImagen() + ": " + listaWillBusqueda.get(i).getFactorCorrelacionCruzada());
        }
    }

    public ImagenWill getImagenMasParecida(){
        return this.imagenMasParecida;
    }

}
