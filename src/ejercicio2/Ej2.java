package ejercicio2;

import common.ImagenWill;
import common.Indicadores;


public class Ej2 {

    public void ejecutar(ImagenWill imagenOriginal,ImagenWill imagenPolicia,ImagenWill imagenMasParecida){

        Indicadores calculador = new Indicadores();

        Histograma hImagenOriginal = new Histograma("Histograma imagen original",imagenOriginal);
        Histograma hImagenMasParecida=new Histograma("Histograma imagen mas parecida",imagenMasParecida);
        Histograma hImagenPolicia=new Histograma("Histograma imagen del policia",imagenPolicia);
        //h.pack();
        //h.setVisible(true);
        //visualizacion del histograma

        System.out.println("\n---------------------");
        System.out.println("Ejercicio 2\n");

        System.out.println("Media de " + imagenOriginal.getNombreImagen() + ": " + calculador.calcularMedia(imagenOriginal.getImagen(), null));
        System.out.println("Media de " + imagenMasParecida.getNombreImagen() + ": " + calculador.calcularMedia(imagenMasParecida.getImagen(), null));
        System.out.println("Media de " + imagenPolicia.getNombreImagen() + ": " + calculador.calcularMedia(imagenPolicia.getImagen(), null));

        System.out.println("\nDesvio estandar " + imagenOriginal.getNombreImagen() + ": " + calculador.calcularDesvio(imagenOriginal.getImagen()));
        System.out.println("Desvio estandar " + imagenMasParecida.getNombreImagen() + ": " + calculador.calcularDesvio(imagenMasParecida.getImagen()));
        System.out.println("Desvio estandar " + imagenPolicia.getNombreImagen() + ": " + calculador.calcularDesvio(imagenPolicia.getImagen()));

    }

}
