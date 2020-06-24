package ejercicio4;

import common.ImagenWill;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;


public class Ej4 {
    private ImagenWill imagenCanal2;
    private ImagenWill imagenCanal8;
    private ImagenWill imagenCanal10;


    public Ej4(){
        try {
            imagenCanal2=new ImagenWill(ImageIO.read(new File("ImagenesWill/Will_Canal2.bmp")), "Imagen canal 2");
            imagenCanal8=new ImagenWill(ImageIO.read(new File("ImagenesWill/Will_Canal8.bmp")), "Imagen canal 8");
            imagenCanal10=new ImagenWill(ImageIO.read(new File("ImagenesWill/Will_Canal10.bmp")), "Imagen canal 10");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ejecutar(ImagenWill imagenOriginal){
        System.out.println("\n---------------------");
        System.out.println("Ejercicio 4");
        Canal c2=new Canal(imagenOriginal,this.imagenCanal2);
        Canal c8=new Canal(imagenOriginal,this.imagenCanal8);
        Canal c10=new Canal(imagenOriginal,this.imagenCanal10);

        System.out.println("\nCanal 2:");
        for(double[] row :c2.generarMatrizCondicional()){
            printRow(row);
        }

        System.out.println("\nCanal 8:");
        for(double[] row :c8.generarMatrizCondicional()){
            printRow(row);
        }

        System.out.println("\nCanal 10:");
        for(double[] row :c10.generarMatrizCondicional()){
            printRow(row);
        }

        System.out.println("\n---------------------");

        System.out.println("\nRuido del canal 2: "+c2.getRuido());
        System.out.println("Ruido analitico del canal 2: " + c2.getRuidoAnalitico());
        c2.generarGrafico("Grafico de canal 2");
        System.out.println("Ruido del canal 8: "+c8.getRuido());
        System.out.println("Ruido analitico del canal 8: " + c8.getRuidoAnalitico());
        c8.generarGrafico("Grafico de canal 8");
        System.out.println("Ruido del canal 10: "+c10.getRuido());
        System.out.println("Ruido analitico del canal 10: " + c10.getRuidoAnalitico());
        c10.generarGrafico("Grafico de canal 10");



    }

    public static void printRow(double[] row) {
        DecimalFormat df =new DecimalFormat("#.00");
        for (double i : row) {
            System.out.print(df.format(i));
            System.out.print("\t");
        }
        System.out.println();
    }


}
