package modelo;

/**
 * MODELO - Nodo del árbol AVL.
 * Guarda el valor, sus dos hijos y su altura (para no recalcularla cada vez).
 * Una hoja tiene altura 1; un subárbol vacío (null) tiene altura 0.
 */
public class Nodo {
    private int valor;
    private int altura;
    private Nodo izq;
    private Nodo der;

    public Nodo(int valor) {
        this.valor = valor;
        this.altura = 1; // un nodo nuevo siempre entra como hoja
    }

    public int getValor() { return valor; }
    public int getAltura() { return altura; }
    public Nodo getIzq() { return izq; }
    public Nodo getDer() { return der; }
    public void setIzq(Nodo izq) { this.izq = izq; }
    public void setDer(Nodo der) { this.der = der; }

    /** Altura de un nodo que puede ser null. */
    public static int altura(Nodo n) {
        return (n == null) ? 0 : n.altura;
    }

    /** Recalcula la altura de este nodo a partir de sus hijos. */
    public void actualizarAltura() {
        this.altura = 1 + Math.max(altura(izq), altura(der));
    }

    /** Factor de balance = altura del hijo izquierdo - altura del hijo derecho. */
    public int getFactorBalance() {
        return altura(izq) - altura(der);
    }
}
