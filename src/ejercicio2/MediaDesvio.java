package ejercicio2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MediaDesvio {
<<<<<<< HEAD
    public float calcularMedia(BufferedImage img) {
        float media = 0;
        for (int i = 0; i < img.getWidth(); i++) { // recorro ancho de la imagen que le paso por parametro
            for (int j = 0; j < img.getHeight(); j++) { // recorro alto
                Color simbolo = new Color(img.getRGB(i,j));
                media += simbolo.getRed();
            }
        }
        return media / (img.getHeight() * img.getWidth());
    }

    public float calcularDesvio(BufferedImage img){
        float desvio=0;
        float sumador=0;
        float sumaDiferencia=0;
        int muestra=0;
        for (int i = 0; i < img.getWidth(); i++) { // recorro ancho de la imagen que le paso por parametro
            for (int j = 0; j < img.getHeight(); j++) { // recorro alto
                Color simbolo = new Color(img.getRGB(i, j));
                muestra++;
                sumador += simbolo.getRed();
                sumaDiferencia += (float) Math.pow(simbolo.getRed() - (sumador / muestra), 2);
                desvio=sumaDiferencia/muestra;
            }
        }
        return (float) Math.sqrt(desvio);
=======
    public float calcularMedia(BufferedImage img){
        int sumatoria = 0;

        for (int i = 0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++){
                sumatoria += img.getRGB(i,j);
            }
        }

        int n = img.getWidth() * img.getHeight(); //cant total de pixeles en la imagen

        return (float) sumatoria / n;
    }

    public float calcularDesvio(BufferedImage img){
        float sumatoria = 0; //aca ire sumando el (valor de cada pixel) al cuadrado

        for (int i = 0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++){
                sumatoria += (float) Math.pow(img.getRGB(i,j), 2);
            }
        }

        int n = img.getWidth() * img.getHeight(); //cant total de pixeles en la imagen

        float media_x_cuadrado = sumatoria / n;
        float mediaAlCuadrado = (float) Math.pow(this.calcularMedia(img), 2);

        return (float) Math.sqrt(media_x_cuadrado - mediaAlCuadrado);
>>>>>>> master
    }
}
