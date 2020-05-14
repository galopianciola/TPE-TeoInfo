import ejercicio1.Ej1;
import ejercicio2.Ej2;

public class Main {
    public static void main(String[] args) {
        Ej1 ej1 = new Ej1();
        ej1.ejecutar();

        //le paso por constructor la imagen mas parecida que obtuve en la busqueda del ej1
        Ej2 ej2 = new Ej2(ej1.getImagenMasParecida());
        ej2.ejecutar();
    }
}
