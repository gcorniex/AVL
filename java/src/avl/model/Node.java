package avl.model;

/**
 * MODELO - Nodo de un árbol AVL.
 * Además de la clave y los hijos, guarda su altura para poder calcular
 * el factor de balance en O(1).
 */
public class Node {
    private int key;      // Valor almacenado
    private int height;   // Altura del nodo (una hoja tiene altura 1)
    private Node left;    // Hijo izquierdo
    private Node right;   // Hijo derecho

    public Node(int key) {
        this.key = key;
        this.height = 1;  // Un nodo nuevo siempre es hoja
    }

    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public Node getLeft() { return left; }
    public void setLeft(Node left) { this.left = left; }
    public Node getRight() { return right; }
    public void setRight(Node right) { this.right = right; }
}
