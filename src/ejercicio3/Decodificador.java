package ejercicio3;

import common.ImagenWill;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Decodificador {
    //clave: color, valor: codigo
    private Hashtable<Integer,String> codigo = new Hashtable<>();
    private static int LONGITUDBUFFER = 8;
    private int alto;
    private int ancho;
    private byte[] inputSequence;
    private double[] frecuencias = new double[256];


    public BufferedImage bytesDecoding() {
        BufferedImage out=new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
        ArrayList<Integer> decode=new ArrayList<>();
        byte mask = (byte) (1 << (LONGITUDBUFFER - 1)); // mask: 10000000
        int bufferPos = 0;
        String temporal="";
        int k=0; // indice en el arreglo de bytes (secuencia codificada)
        while (k<inputSequence.length) { //para todos los bytes a decodificar
            byte buffer = inputSequence[k];
            while (bufferPos < LONGITUDBUFFER) {
                //si es un uno
                if ((buffer & mask) == mask) {  // 10000000
                    temporal = temporal + "1";
                } else { //si es un 0
                    temporal = temporal + "0";
                }

                //si los bits recuperados hasta ahora representan un codigo
                if (codigo.contains(temporal)) {
                    Enumeration claves = this.codigo.keys();
                    Enumeration valores = this.codigo.elements();


                    while (claves.hasMoreElements())  {
                        Integer clave = (Integer) claves.nextElement();
                        String valor = (String) valores.nextElement();
                        if (valor.equals(temporal)) {
                            decode.add(clave);
                            temporal="";
                            break; //corto la busqueda pq ya encontre el color
                        }
                    }
                }

                //para leer el siguiente bit
                buffer = (byte) (buffer << 1);
                bufferPos++;
            }
            k++;
            bufferPos = 0;


        }

        //reconstruccion de la imagen
        k=0;
        for (int i=0;i<ancho;i++){
            for (int j=0;j<alto;j++){
                Color colorPixel = new Color(decode.get(k),decode.get(k),decode.get(k));
                out.setRGB(i,j,colorPixel.getRGB());
                k++;
            }
        }
        return out;
    }

    public void decodificar(String ruta){
        try {
            DataInputStream imgCodificada = new DataInputStream(new FileInputStream(ruta));
            byte[]input = Files.readAllBytes(new File(ruta).toPath());

            int contador=0; //aca ire contando la cantidad de enteros que voy leyendo

            //primero traigo los datos del header
            this.alto = imgCodificada.readInt();
            this.ancho = imgCodificada.readInt();
            int cantColores = imgCodificada.readInt();
            contador = contador + 3; //porque lei 3 enteros


            //inicializo en 0 el arreglo de frecuencias a obtener
            for (int i = 0; i < frecuencias.length; i++){
                frecuencias[i] = 0;
            }

            //despues traigo cada color con su frecuencia (de ahi armare su probabilidad)
            for (int i=0; i < cantColores; i++){
                int color = imgCodificada.readInt();
                int frecuencia = imgCodificada.readInt();
                frecuencias[color] = frecuencia;
                contador = contador + 2; //se leen 2 enteros por iteracion
            }

            //genero el codigo mediante huffman (teniendo como dato las frecuencias de cada color, que luego seran probabilidades)
            Huffman huffman = new Huffman(frecuencias);
            this.codigo = huffman.getCodigoImagen();

            contador=contador*4; //porque cada entero pesa 4 bytes

            //tamaño del arreglo del byte es de la suma total de bytes (includo el header) - el header (en bytes)
            this.inputSequence= new byte[input.length-contador];

            //recorro los bytes restantes del archivo tenido en cuenta por la posicion del contador
            for(int i=0;i<input.length-contador;i++){
                this.inputSequence[i]=input[i+contador];
            }

            imgCodificada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] convertirAPrimitivo(ArrayList<Byte> input) {
        byte[] ret = new byte[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }

        return ret;
    }


    //METODO PRINCIPAL
    public ImagenWill restaurarImagen(String ruta,String nombre){
        decodificar(ruta);
        return new ImagenWill(bytesDecoding(),nombre);
    }

    public void crearImagenBMP(ImagenWill imagenRecuperada){
        File outputfile = new File(imagenRecuperada.getNombreImagen()+" recuperada.bmp");
        try {
            ImageIO.write(imagenRecuperada.getImagen(), "bmp", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
