package vista;

import java.util.List;
import java.util.Scanner;

import modelo.Nodo;

/**
 * VISTA - Todo lo que el usuario ve y escribe en la consola.
 * No contiene lógica del árbol: solo muestra datos y pide datos.
 */
public class VistaConsola {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarBienvenida() {
        System.out.println("==========================================");
        System.out.println("   ARBOL AVL  -  Patron MVC (Java)");
        System.out.println("==========================================");
    }

    public void mostrarMenu() {
        System.out.println();
        System.out.println("--------------- MENU ---------------");
        System.out.println("1. Insertar un valor");
        System.out.println("2. Buscar un valor");
        System.out.println("3. Mostrar el arbol");
        System.out.println("4. Recorrido en orden");
        System.out.println("5. Cargar ejemplo (10, 20, 30, 40, 50, 25)");
        System.out.println("6. Vaciar el arbol");
        System.out.println("0. Salir");
    }

    /** Pide la opción del menú. Devuelve -1 si no es un número. Si se acaba la entrada, devuelve 0 (salir). */
    public int pedirOpcion() {
        System.out.print("Elige una opcion: ");
        if (!scanner.hasNextLine()) return 0;
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Pide un número entero. Devuelve null si lo escrito no es un número. */
    public Integer pedirNumero(String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) return null;
        try {
            return Integer.valueOf(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarEventos(List<String> eventos) {
        for (String e : eventos) {
            System.out.println(e);
        }
    }

    public void mostrarEnOrden(List<Integer> valores) {
        System.out.println("En orden: " + valores);
    }

    /** Dibuja el árbol "acostado": la raíz a la izquierda, hijos derechos arriba e izquierdos abajo. */
    public void mostrarArbol(Nodo raiz, int altura) {
        if (raiz == null) {
            System.out.println("(el arbol esta vacio)");
            return;
        }
        System.out.println("Arbol (de lado, entre parentesis el factor de balance):");
        dibujar(raiz, 0);
        System.out.println("Altura del arbol: " + altura);
    }

    private void dibujar(Nodo nodo, int nivel) {
        if (nodo == null) return;
        dibujar(nodo.getDer(), nivel + 1);
        System.out.println("        ".repeat(nivel) + nodo.getValor() + " (fb=" + nodo.getFactorBalance() + ")");
        dibujar(nodo.getIzq(), nivel + 1);
    }
}
