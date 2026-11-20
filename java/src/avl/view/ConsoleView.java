package avl.view;

import avl.model.Node;
import java.util.List;

/**
 * VISTA - Se encarga ÚNICAMENTE de mostrar información por consola.
 * No contiene lógica del árbol ni lee decisiones: eso es del controlador.
 */
public class ConsoleView {

    public void showMenu() {
        System.out.println("\n===== ÁRBOL AVL =====");
        System.out.println("1. Insertar clave");
        System.out.println("2. Eliminar clave");
        System.out.println("3. Buscar clave");
        System.out.println("4. Mostrar árbol");
        System.out.println("5. Recorridos (inorden / preorden)");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    public void showMessage(String message) { System.out.println(message); }

    public void showPrompt(String message) { System.out.print(message); }

    public void showEvents(List<String> events) {
        for (String e : events) System.out.println("  [balanceo] " + e);
    }

    public void showTraversals(List<Integer> inorder, List<Integer> preorder) {
        System.out.println("Inorden  : " + inorder);
        System.out.println("Preorden : " + preorder);
    }

    /**
     * Dibuja el árbol "acostado": la raíz a la izquierda, el hijo derecho arriba
     * y el izquierdo abajo. Cada nodo muestra su clave y su altura (h).
     */
    public void showTree(Node root) {
        if (root == null) { System.out.println("(árbol vacío)"); return; }
        printNode(root, "", true);
    }

    private void printNode(Node node, String prefix, boolean isTail) {
        if (node.getRight() != null)
            printNode(node.getRight(), prefix + (isTail ? "│   " : "    "), false);
        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.getKey() + " (h=" + node.getHeight() + ")");
        if (node.getLeft() != null)
            printNode(node.getLeft(), prefix + (isTail ? "    " : "│   "), true);
    }
}
