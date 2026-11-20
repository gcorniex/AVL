package avl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * MODELO - Árbol AVL (árbol binario de búsqueda auto-balanceado).
 *
 * Invariante: para todo nodo, |altura(izq) - altura(der)| <= 1.
 * Si una inserción o eliminación rompe el invariante, se restaura con
 * rotaciones. Complejidad: búsqueda, inserción y eliminación en O(log n).
 *
 * El modelo NO imprime nada: solo registra "eventos" (rotaciones) para que
 * la vista pueda mostrarlos. Así se respeta la separación MVC.
 */
public class AVLTree {
    private Node root;
    private final List<String> events = new ArrayList<>();

    // ------------------------------------------------------------------
    // API pública
    // ------------------------------------------------------------------

    /** Inserta una clave. Ignora duplicados. */
    public void insert(int key) {
        events.clear();
        root = insert(root, key);
    }

    /** Elimina una clave si existe. */
    public void delete(int key) {
        events.clear();
        root = delete(root, key);
    }

    /** Busca una clave en el árbol. */
    public boolean contains(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.getKey()) return true;
            current = key < current.getKey() ? current.getLeft() : current.getRight();
        }
        return false;
    }

    public Node getRoot() { return root; }
    public int getHeight() { return height(root); }
    public boolean isEmpty() { return root == null; }

    /** Rotaciones realizadas en la última operación (para la vista). */
    public List<String> getEvents() { return new ArrayList<>(events); }

    /** Recorrido inorden: devuelve las claves ordenadas. */
    public List<Integer> inorder() {
        List<Integer> out = new ArrayList<>();
        inorder(root, out);
        return out;
    }

    /** Recorrido preorden: útil para ver la forma del árbol. */
    public List<Integer> preorder() {
        List<Integer> out = new ArrayList<>();
        preorder(root, out);
        return out;
    }

    /** Verifica el invariante AVL en todo el árbol (útil para pruebas). */
    public boolean isBalanced() { return isBalanced(root); }

    // ------------------------------------------------------------------
    // Utilidades de altura y balance
    // ------------------------------------------------------------------

    private int height(Node n) { return n == null ? 0 : n.getHeight(); }

    private void updateHeight(Node n) {
        n.setHeight(1 + Math.max(height(n.getLeft()), height(n.getRight())));
    }

    /** Factor de balance = altura(izq) - altura(der). Válido: -1, 0, 1. */
    private int balanceFactor(Node n) {
        return n == null ? 0 : height(n.getLeft()) - height(n.getRight());
    }

    // ------------------------------------------------------------------
    // Rotaciones
    // ------------------------------------------------------------------

    /**
     * Rotación simple a la derecha (caso Left-Left).
     *       y                x
     *      / \              / \
     *     x   C    -->     A   y
     *    / \                  / \
     *   A   B                B   C
     */
    private Node rotateRight(Node y) {
        Node x = y.getLeft();
        Node b = x.getRight();
        x.setRight(y);
        y.setLeft(b);
        updateHeight(y); // primero y (quedó más abajo)
        updateHeight(x);
        return x;        // nueva raíz del subárbol
    }

    /** Rotación simple a la izquierda (caso Right-Right). Espejo de la anterior. */
    private Node rotateLeft(Node x) {
        Node y = x.getRight();
        Node b = y.getLeft();
        y.setLeft(x);
        x.setRight(b);
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /** Actualiza la altura de n y lo rebalancea si hace falta. */
    private Node rebalance(Node n) {
        updateHeight(n);
        int bf = balanceFactor(n);

        if (bf > 1) { // Desbalanceado hacia la izquierda
            if (balanceFactor(n.getLeft()) < 0) { // Caso Left-Right
                events.add("Rotación doble Izquierda-Derecha (LR) en nodo " + n.getKey());
                n.setLeft(rotateLeft(n.getLeft()));
            } else {                              // Caso Left-Left
                events.add("Rotación simple Derecha (LL) en nodo " + n.getKey());
            }
            return rotateRight(n);
        }
        if (bf < -1) { // Desbalanceado hacia la derecha
            if (balanceFactor(n.getRight()) > 0) { // Caso Right-Left
                events.add("Rotación doble Derecha-Izquierda (RL) en nodo " + n.getKey());
                n.setRight(rotateRight(n.getRight()));
            } else {                               // Caso Right-Right
                events.add("Rotación simple Izquierda (RR) en nodo " + n.getKey());
            }
            return rotateLeft(n);
        }
        return n; // Ya estaba balanceado
    }

    // ------------------------------------------------------------------
    // Inserción / eliminación recursivas
    // ------------------------------------------------------------------

    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);          // Caso base: posición encontrada
        if (key < node.getKey())      node.setLeft(insert(node.getLeft(), key));
        else if (key > node.getKey()) node.setRight(insert(node.getRight(), key));
        else return node;                                // Duplicado: no se inserta
        return rebalance(node);                          // Se rebalancea al "volver" de la recursión
    }

    private Node delete(Node node, int key) {
        if (node == null) return null;                   // No existe
        if (key < node.getKey())      node.setLeft(delete(node.getLeft(), key));
        else if (key > node.getKey()) node.setRight(delete(node.getRight(), key));
        else {
            // Nodo encontrado: 0 o 1 hijo -> se reemplaza por el hijo
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();
            // 2 hijos: se usa el sucesor inorden (mínimo del subárbol derecho)
            Node successor = minNode(node.getRight());
            node.setKey(successor.getKey());
            node.setRight(delete(node.getRight(), successor.getKey()));
        }
        return rebalance(node);
    }

    private Node minNode(Node n) {
        while (n.getLeft() != null) n = n.getLeft();
        return n;
    }

    // ------------------------------------------------------------------
    // Recorridos y verificación
    // ------------------------------------------------------------------

    private void inorder(Node n, List<Integer> out) {
        if (n == null) return;
        inorder(n.getLeft(), out);
        out.add(n.getKey());
        inorder(n.getRight(), out);
    }

    private void preorder(Node n, List<Integer> out) {
        if (n == null) return;
        out.add(n.getKey());
        preorder(n.getLeft(), out);
        preorder(n.getRight(), out);
    }

    private boolean isBalanced(Node n) {
        if (n == null) return true;
        return Math.abs(balanceFactor(n)) <= 1 && isBalanced(n.getLeft()) && isBalanced(n.getRight());
    }
}
