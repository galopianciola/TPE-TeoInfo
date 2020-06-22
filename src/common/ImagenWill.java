package common;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagenWill {
    private BufferedImage imagen;
    private String nombreImagen;
    private float factorCorrelacionCruzadaConOriginal;
    private double[] arregloFrecuencia = new double[256];


    public ImagenWill(BufferedImage imagen, String nombreImagen) {
        this.imagen = imagen;
        this.nombreImagen = nombreImagen;
        this.setFrecuencia(imagen);
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

    public void setFrecuencia(BufferedImage imagen){
        for (int i = 0; i < imagen.getWidth(); i++) { // recorro ancho de la imagen que le paso por parametro
            for (int j = 0; j < imagen.getHeight(); j++) { // recorro alto
                Color simbolo= new Color(imagen.getRGB(i,j));
                arregloFrecuencia[simbolo.getRed()]++; //guardo los valores de imagen en el arreglo
            }
        }
    }
    public double[] getArregloFrecuencia(){
        return this.arregloFrecuencia;
    }


    public int getCantTonos () {
        int suma = 0;
        for (int i= 0; i< arregloFrecuencia.length; i++){
            if (arregloFrecuencia[i] != 0)
                suma++;
        }
        return suma;
    }

}
