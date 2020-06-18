package ejercicio4;

import common.ImagenWill;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Ej4 {
    ImagenWill imagenCanal2;
    ImagenWill imagenCanal8;
    ImagenWill imagenCanal10;

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


        //for(double[] row :c2.getMatrizCondicional()){
          //  printRow(row);
        //}
        c2.getMatrizCondicional();
        c2.getMatrizConjunta();
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
