package avl.controller;

import avl.model.AVLTree;
import avl.view.ConsoleView;
import java.util.Scanner;

/**
 * CONTROLADOR - Conecta la vista con el modelo.
 * Lee la entrada del usuario, llama al modelo y le pide a la vista que muestre el resultado.
 */
public class AVLController {
    private final AVLTree tree;
    private final ConsoleView view;

    public AVLController(AVLTree tree, ConsoleView view) {
        this.tree = tree;
        this.view = view;
    }

    /** Menú interactivo. */
    public void run() {
        Scanner sc = new Scanner(System.in);
        int option;
        do {
            view.showMenu();
            option = readInt(sc);
            switch (option) {
                case 1 -> { view.showPrompt("Clave a insertar: "); insert(readInt(sc)); }
                case 2 -> { view.showPrompt("Clave a eliminar: "); delete(readInt(sc)); }
                case 3 -> {
                    view.showPrompt("Clave a buscar: ");
                    int k = readInt(sc);
                    view.showMessage(tree.contains(k) ? k + " SÍ está en el árbol." : k + " NO está en el árbol.");
                }
                case 4 -> view.showTree(tree.getRoot());
                case 5 -> view.showTraversals(tree.inorder(), tree.preorder());
                case 0 -> view.showMessage("¡Hasta luego!");
                default -> view.showMessage("Opción inválida.");
            }
        } while (option != 0);
    }

    /**
     * Demostración automática pensada para grabar el video:
     * muestra los 4 casos de rotación (RR, LL, LR, RL) y una eliminación.
     */
    public void runDemo() {
        view.showMessage("--- Caso RR: insertar 10, 20, 30 ---");
        for (int k : new int[]{10, 20, 30}) insert(k);
        view.showTree(tree.getRoot());

        view.showMessage("\n--- Caso LL: insertar 5, 1 ---");
        for (int k : new int[]{5, 1}) insert(k);
        view.showTree(tree.getRoot());

        view.showMessage("\n--- Caso LR: insertar 15, 12 ---");
        for (int k : new int[]{15, 12}) insert(k);
        view.showTree(tree.getRoot());

        view.showMessage("\n--- Caso RL: insertar 40, 35 ---");
        for (int k : new int[]{40, 35}) insert(k);
        view.showTree(tree.getRoot());

        view.showMessage("\n--- Eliminar 1 y 5 ---");
        delete(1);
        delete(5);
        view.showTree(tree.getRoot());
        view.showTraversals(tree.inorder(), tree.preorder());
        view.showMessage("¿Árbol balanceado? " + tree.isBalanced());
    }

    private void insert(int key) {
        tree.insert(key);
        view.showMessage("Insertado: " + key);
        view.showEvents(tree.getEvents());
    }

    private void delete(int key) {
        if (!tree.contains(key)) { view.showMessage(key + " no existe."); return; }
        tree.delete(key);
        view.showMessage("Eliminado: " + key);
        view.showEvents(tree.getEvents());
    }

    private int readInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); view.showPrompt("Ingrese un número entero: "); }
        return sc.nextInt();
    }
}
