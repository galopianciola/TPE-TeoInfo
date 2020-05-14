package common;

import java.awt.image.BufferedImage;

public class ImagenWill {
    private BufferedImage imagen;
    private String nombreImagen;
    private float factorCorrelacionCruzadaConOriginal; //para ej1
    private float media; //para ej2
    private float desvio; //para ej2

    public ImagenWill(BufferedImage imagen, String nombreImagen) {
        this.imagen = imagen;
        this.nombreImagen = nombreImagen;
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
}
