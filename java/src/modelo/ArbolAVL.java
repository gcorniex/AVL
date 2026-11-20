package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * MODELO - Árbol AVL (árbol binario de búsqueda auto-balanceado).
 *
 * IMPORTANTE (MVC): esta clase NO imprime nada ni lee nada del usuario.
 * Solo contiene los datos y la lógica. Cuando ocurre algo interesante
 * (un desbalance, una rotación) lo anota en una lista de "eventos" y el
 * CONTROLADOR decide si se lo pasa a la VISTA para mostrarlo.
 */
public class ArbolAVL {

    private Nodo raiz;
    private final List<String> eventos = new ArrayList<>();

    // ------------------------- Consultas -------------------------

    public Nodo getRaiz() { return raiz; }

    public boolean estaVacio() { return raiz == null; }

    public int altura() { return Nodo.altura(raiz); }

    public void vaciar() {
        raiz = null;
        eventos.clear();
    }

    public boolean buscar(int valor) {
        Nodo actual = raiz;
        while (actual != null) {
            if (valor == actual.getValor()) return true;
            actual = (valor < actual.getValor()) ? actual.getIzq() : actual.getDer();
        }
        return false;
    }

    /** Recorrido en orden: devuelve los valores de menor a mayor. */
    public List<Integer> enOrden() {
        List<Integer> resultado = new ArrayList<>();
        enOrden(raiz, resultado);
        return resultado;
    }

    private void enOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            enOrden(nodo.getIzq(), resultado);
            resultado.add(nodo.getValor());
            enOrden(nodo.getDer(), resultado);
        }
    }

    /** Devuelve los eventos ocurridos desde la última vez y limpia la lista. */
    public List<String> obtenerEventos() {
        List<String> copia = new ArrayList<>(eventos);
        eventos.clear();
        return copia;
    }

    // ------------------------- Inserción -------------------------

    /** Inserta un valor. Devuelve false si ya existía (no se permiten duplicados). */
    public boolean insertar(int valor) {
        if (buscar(valor)) return false;
        raiz = insertar(raiz, valor);
        return true;
    }

    private Nodo insertar(Nodo nodo, int valor) {
        // 1) Inserción normal de un BST
        if (nodo == null) return new Nodo(valor);
        if (valor < nodo.getValor()) {
            nodo.setIzq(insertar(nodo.getIzq(), valor));
        } else {
            nodo.setDer(insertar(nodo.getDer(), valor));
        }
        // 2) Actualizar la altura de este nodo
        nodo.actualizarAltura();
        // 3 y 4) Calcular el factor de balance y rebalancear si hace falta
        return balancear(nodo, valor);
    }

    private Nodo balancear(Nodo nodo, int valor) {
        int fb = nodo.getFactorBalance();

        if (fb > 1 && valor < nodo.getIzq().getValor()) {            // Izquierda-Izquierda
            registrarDesbalance(nodo, fb, "Izquierda-Izquierda");
            return rotacionDerecha(nodo);
        }
        if (fb < -1 && valor > nodo.getDer().getValor()) {           // Derecha-Derecha
            registrarDesbalance(nodo, fb, "Derecha-Derecha");
            return rotacionIzquierda(nodo);
        }
        if (fb > 1 && valor > nodo.getIzq().getValor()) {            // Izquierda-Derecha
            registrarDesbalance(nodo, fb, "Izquierda-Derecha (rotación doble)");
            nodo.setIzq(rotacionIzquierda(nodo.getIzq()));
            return rotacionDerecha(nodo);
        }
        if (fb < -1 && valor < nodo.getDer().getValor()) {           // Derecha-Izquierda
            registrarDesbalance(nodo, fb, "Derecha-Izquierda (rotación doble)");
            nodo.setDer(rotacionDerecha(nodo.getDer()));
            return rotacionIzquierda(nodo);
        }
        return nodo; // ya estaba balanceado
    }

    private void registrarDesbalance(Nodo nodo, int fb, String caso) {
        eventos.add("Desbalance en el nodo " + nodo.getValor() + " (factor " + fb + ") -> caso " + caso);
    }

    // ------------------------- Rotaciones -------------------------

    private Nodo rotacionDerecha(Nodo y) {
        eventos.add("   Rotación a la DERECHA sobre el nodo " + y.getValor());
        Nodo x = y.getIzq();
        Nodo t2 = x.getDer();

        x.setDer(y);
        y.setIzq(t2);

        y.actualizarAltura(); // primero y (quedó abajo)
        x.actualizarAltura(); // luego x (quedó arriba)
        return x;
    }

    private Nodo rotacionIzquierda(Nodo x) {
        eventos.add("   Rotación a la IZQUIERDA sobre el nodo " + x.getValor());
        Nodo y = x.getDer();
        Nodo t2 = y.getIzq();

        y.setIzq(x);
        x.setDer(t2);

        x.actualizarAltura();
        y.actualizarAltura();
        return y;
    }
}
