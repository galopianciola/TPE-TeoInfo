package ejercicio1;

import common.Indicadores;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FactorCorrelacionCruzada {
    private Indicadores calculador = new Indicadores();

    public float calcularFactorCorrelacionCruzada(BufferedImage imgX, BufferedImage imgY){
        //para calcular el Factor/Coeficiente de Correlacion Cruzada entre dos fuentes (dos img en este caso),
        //necesito previamente la Correlacion Cruzada y la Covarianza Cruzada

        // X sera la imagen original
        // Y sera la imagen de busqueda

        float correlacionCruzada = calculador.calcularMedia(imgX, imgY);
        float covarianzaCruzada = correlacionCruzada - calculador.calcularMedia(imgX, null) * calculador.calcularMedia(imgY,null);
        float desvioEstandarX = calculador.calcularDesvio(imgX);
        float desvioEstandarY = calculador.calcularDesvio(imgY);

        return covarianzaCruzada / (desvioEstandarX * desvioEstandarY);
    }
}
