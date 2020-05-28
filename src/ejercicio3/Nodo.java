package ejercicio3;

public class Nodo {
    private Nodo izq;
    private Nodo der;
    private double valor;


    public Nodo(double valor){
        this.valor=valor;
        this.izq=null;
        this.der=null;
    }

    public void addValor(double valor){
        if(valor>this.valor) {
            if (this.der != null) {
                der.addValor(valor);
            } else {
                der = new Nodo(valor);
            }
        }else{
            if (this.izq!=null){
                izq.addValor(valor);
            }else{
                izq = new Nodo(valor);
            }
        }
    }
}
