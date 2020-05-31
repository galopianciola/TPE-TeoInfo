package ejercicio3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Header {
    private int alto;
    private int ancho;
    private double[] arregloProbabilidades;
    private int longitud;

    public Header(int alto, int ancho, double[] arregloProbabilidades, int longitud) {
        this.alto = alto;
        this.ancho = ancho;
        this.arregloProbabilidades = arregloProbabilidades;
        this.longitud = longitud;
    }

    public byte[] convertirAByte() throws IOException { // De objeto a byte[]
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os;

        os = new ObjectOutputStream(bs);
        os.writeObject(this); // convierte este objeto header a un arreglo de bytes.
        os.close();

        return bs.toByteArray();
    }

}
