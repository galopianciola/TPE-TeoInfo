package ejercicio1;

import java.awt.image.BufferedImage;

public class FactorCorrelacionCruzada {

    public float calcularFactorCorrelacionCruzada(BufferedImage imgX, BufferedImage imgY){
        //para calcular el Factor/Coeficiente de Correlacion Cruzada entre dos fuentes (dos img en este caso),
        //necesito previamente la Correlacion Cruzada y la Covarianza Cruzada

        // X sera la imagen original
        // Y sera la imagen de busqueda

        int n = imgX.getWidth() * imgX.getHeight(); //en n tendre el tamanio de la imagen a analizar

        float sumatoriaXY = 0; //la necesito para correlacion cruzada
        float sumatoriaX = 0; //la necesito para covarianza cruzada
        float sumatoriaY = 0; //idem
        float sumatoriaXcuadrado = 0; //la necesito para los desvios estandar
        float sumatoriaYcuadrado = 0; //idem

        for (int i =  0; i < imgX.getWidth(); i++) { //recorro ancho
            for (int j = 0; j < imgX.getHeight(); j++) { //recorro alto

                float x = (float) imgX.getRGB(i, j); //obtengo el valor del pixel (i,j) en la img original
                float y = (float) imgY.getRGB(i, j); //obtengo el valor del pixel (i,j) en la img analizada
                //float x = 1;
                //float y = 1;

                sumatoriaX += x;
                sumatoriaY += y;
                sumatoriaXY += x * y;
                sumatoriaXcuadrado += Math.pow(x, 2);
                sumatoriaYcuadrado += Math.pow(y, 2);

            }
        }

        //ahora que tengo todos los datos que necesito,
        //uso la formula de Factor de Correlacion Cruzada

        float correlacionCruzada = (float) n * sumatoriaXY;
        float covarianzaCruzada = (float) correlacionCruzada - sumatoriaX * sumatoriaY;
        float desvioEstandarX = (float) Math.sqrt(n * sumatoriaXcuadrado - Math.pow(sumatoriaX, 2));
        float desvioEstandarY = (float) Math.sqrt(n * sumatoriaYcuadrado - Math.pow(sumatoriaY, 2));

        return (float) covarianzaCruzada / (desvioEstandarX * desvioEstandarY);
    }
}
