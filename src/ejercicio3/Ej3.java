package ejercicio3;

import common.ImagenWill;


public class Ej3 {

    public void ejecutar(ImagenWill imagenOriginal,ImagenWill imagenPolicia,ImagenWill imagenMasParecida){

        System.out.println("\n---------------------");
        System.out.println("Ejercicio 3");
        Codificador codificaH1=new Codificador();

        Huffman h1=new Huffman(imagenOriginal.getArregloFrecuencia());
        //inciso a
        codificaH1.aplicarCodificacion(imagenOriginal, h1, imagenOriginal.getNombreImagen()+".bin");
        System.out.println("Se ha comprimido la imagen original exitosamente indicando el inciso a)");


        Huffman h1incisob = new Huffman(imagenOriginal.getArregloFrecuencia());
        //inciso b
        codificaH1.aplicarCodificacion(imagenMasParecida,h1incisob, imagenMasParecida.getNombreImagen()+".bin");
        System.out.println("Se ha comprimido la imagen mas parecida (con codigo de imagen original) exitosamente indicando el inciso b)");


        Huffman h1incisoc = new Huffman(imagenOriginal.getArregloFrecuencia());
        //inciso c
        codificaH1.aplicarCodificacion(imagenPolicia,h1incisoc,imagenPolicia.getNombreImagen()+".bin");
        System.out.println("Se ha comprimido la imagen traida por el policia (con codigo de imagen original) exitosamente indicando el inciso c)");
        System.out.println("\n -------");


        //descompresion de las 3 imagenes
        Decodificador decodificaH1 = new Decodificador();
        ImagenWill imagenOriginalRecuperada= decodificaH1.restaurarImagen("ArchivosBinarios/"+imagenOriginal.getNombreImagen()+".bin",imagenOriginal.getNombreImagen());
        decodificaH1.crearImagenBMP(imagenOriginalRecuperada);

        ImagenWill imagenMasParecidaRecuperada= decodificaH1.restaurarImagen("ArchivosBinarios/"+imagenMasParecida.getNombreImagen()+".bin",imagenMasParecida.getNombreImagen());
        decodificaH1.crearImagenBMP(imagenMasParecidaRecuperada);

        ImagenWill imagenPoliciaRecuperada= decodificaH1.restaurarImagen("ArchivosBinarios/"+imagenPolicia.getNombreImagen()+".bin",imagenPolicia.getNombreImagen());
        decodificaH1.crearImagenBMP(imagenPoliciaRecuperada);


        //inciso d
        Codificador codificaH2 = new Codificador();
        Huffman h2 = new Huffman(imagenPolicia.getArregloFrecuencia());

        codificaH2.aplicarCodificacion(imagenPolicia, h2, imagenPolicia.getNombreImagen()+" - propio codigo.bin");
        System.out.println("Se ha comprimido la imagen traida por el policia (con codigo propio) exitosamente indicando el inciso d)");

        //descompresion inciso d
        Decodificador decodificaH2 = new Decodificador();

        ImagenWill imagenPoliciaRecuperadaH2=decodificaH2.restaurarImagen("ArchivosBinarios/"+imagenPolicia.getNombreImagen()+" - propio codigo.bin",imagenPolicia.getNombreImagen()+" - propio codigo");
        decodificaH2.crearImagenBMP(imagenPoliciaRecuperadaH2);
    }

}


