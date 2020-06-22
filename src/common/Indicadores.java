package common;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Indicadores {
    public float calcularMedia(BufferedImage imgX, BufferedImage imgY){
        //este metodo siempre recibe dos imagenes
        //si recibe una sola y la otra en null, calcula su media
        //si recibe dos diferentes, calcula la media de x*y
        //si recibe dos iguales, calcula la media elevando cada valor al cuadrado (multiplicandolo por si mismo, x*x)

        int n = imgX.getWidth() * imgX.getHeight(); //en n tendre el tamanio de la imagen a analizar
        float sumador = 0;

        for (int i =  0; i < imgX.getWidth(); i++) { //recorro ancho
            for (int j = 0; j < imgX.getHeight(); j++) { //recorro alto
                if (imgY == null){ //si vino una sola imagen, calculo solo su media
                    Color color = new Color(imgX.getRGB(i,j));
                    float valor = (float) color.getRed();
                    sumador += valor;
                } else { //si vinieron dos imagenes (X e Y), calculo la media de X*Y
                    Color colorX = new Color(imgX.getRGB(i,j));
                    Color colorY = new Color(imgY.getRGB(i,j));

                    float valorX = (float) colorX.getRed();
                    float valorY = (float) colorY.getRed();

                    sumador += valorX * valorY;
                }
            }
        }

        return sumador/n;
    }

    public float calcularDesvio(BufferedImage img){
        //al pasarle a calcularMedia(img,img) calcula la media habiendo sumado cada valor^2
        return (float) Math.sqrt(calcularMedia(img, img) - Math.pow(calcularMedia(img,null), 2));
    }
}
