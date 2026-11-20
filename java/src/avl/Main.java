package avl;

import avl.controller.AVLController;
import avl.model.AVLTree;
import avl.view.ConsoleView;

/**
 * Punto de entrada. Ensambla Modelo-Vista-Controlador.
 * Uso:  java avl.Main          -> menú interactivo
 *       java avl.Main --demo   -> demostración automática
 */
public class Main {
    public static void main(String[] args) {
        AVLController controller = new AVLController(new AVLTree(), new ConsoleView());
        if (args.length > 0 && args[0].equals("--demo")) controller.runDemo();
        else controller.run();
    }
}
