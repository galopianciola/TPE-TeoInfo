package ejercicio2;

import java.awt.image.BufferedImage;

public class MediaDesvio {
    public float calcularMedia(BufferedImage img) {
        float valor = 0;
        for (int i = 0; i < img.getWidth(); i++) { // recorro ancho de la imagen que le paso por parametro
            for (int j = 0; j < img.getHeight(); j++) { // recorro alto
                valor += img.getRGB(i, j) / Math.pow(256, 2);
            }
        }
        return -valor / (img.getHeight() * img.getWidth());
    }

    public float calcularDesvio(BufferedImage img){
        return 0;
    }
}
