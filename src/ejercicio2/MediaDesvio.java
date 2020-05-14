package ejercicio2;

import java.awt.image.BufferedImage;

public class MediaDesvio {
    public float calcularMedia(BufferedImage img){
        int sumatoria = 0;

        for (int i = 0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++){
                sumatoria += img.getRGB(i,j);
            }
        }

        float media = (float) sumatoria / (img.getWidth() * img.getHeight());

        return media;
    }

    public float calcularDesvio(BufferedImage img){
        return 0;
    }
}
