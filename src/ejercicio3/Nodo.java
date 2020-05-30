package ejercicio3;

public class Nodo {
    private int simbolo;
    private double prob;
    private Nodo izq;
    private Nodo der;


    public Nodo(int simbolo, double prob, Nodo izq, Nodo der){
        this.simbolo=simbolo;
        this.prob=prob;
        this.izq=izq;
        this.der=der;
    }

    public Nodo getIzq() {
        return izq;
    }

    public Nodo getDer() {
        return der;
    }

    public int getSimbolo() {
        return simbolo;
    }

    public double getProb() {
        return prob;
    }

}
