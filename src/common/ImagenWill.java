package common;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagenWill {
    private BufferedImage imagen;
    private String nombreImagen;
    private float factorCorrelacionCruzadaConOriginal; //para ej1
    private float media; //para ej2
    private float desvio; //para ej2
    private double[] arregloFrecuencia = new double[256];
    private int cantPixeles;

    public ImagenWill(BufferedImage imagen, String nombreImagen) {
        this.imagen = imagen;
        this.nombreImagen = nombreImagen;
        this.setFrecuencia(imagen);
        this.cantPixeles=imagen.getHeight()*imagen.getWidth();
    }


    public BufferedImage getImagen() {
        return imagen;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public float getFactorCorrelacionCruzada() {
        return factorCorrelacionCruzadaConOriginal;
    }

    public void setFactorCorrelacionCruzadaConOriginal(float factorCorrelacionCruzadaConOriginal) {
        this.factorCorrelacionCruzadaConOriginal = factorCorrelacionCruzadaConOriginal;
    }

    public float getMedia() {
        return media;
    }

    public float getDesvio() {
        return desvio;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public void setDesvio(float desvio) {
        this.desvio = desvio;
    }

    public void setFrecuencia(BufferedImage imagen){
        for (int i = 0; i < imagen.getWidth(); i++) { // recorro ancho de la imagen que le paso por parametro
            for (int j = 0; j < imagen.getHeight(); j++) { // recorro alto
                Color simbolo= new Color(imagen.getRGB(i,j));
                arregloFrecuencia[simbolo.getRed()]++; //guardo los valores de imagen en el arreglo
            }
        }
    }
    public double[] getArregloFrecuencia(){ return this.arregloFrecuencia; }

    public int getCantPixeles(){return cantPixeles;}

}
