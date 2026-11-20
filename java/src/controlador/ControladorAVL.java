package controlador;

import java.util.List;

import modelo.ArbolAVL;
import vista.VistaConsola;

/**
 * CONTROLADOR - Es el intermediario entre la vista y el modelo.
 *   1. Pide a la VISTA lo que el usuario quiere hacer.
 *   2. Le ordena al MODELO que lo haga.
 *   3. Le pide a la VISTA que muestre el resultado.
 */
public class ControladorAVL {

    private static final int[] EJEMPLO = {10, 20, 30, 40, 50, 25};

    private final ArbolAVL modelo;
    private final VistaConsola vista;

    public ControladorAVL(ArbolAVL modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        vista.mostrarBienvenida();
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenu();
            switch (vista.pedirOpcion()) {
                case 1: insertar(); break;
                case 2: buscar(); break;
                case 3: mostrarArbol(); break;
                case 4: vista.mostrarEnOrden(modelo.enOrden()); break;
                case 5: cargarEjemplo(); break;
                case 6:
                    modelo.vaciar();
                    vista.mostrarMensaje("Arbol vaciado.");
                    break;
                case 0:
                    salir = true;
                    vista.mostrarMensaje("Hasta luego!");
                    break;
                default:
                    vista.mostrarMensaje("Opcion no valida.");
            }
        }
    }

    private void insertar() {
        Integer valor = vista.pedirNumero("Valor a insertar: ");
        if (valor == null) {
            vista.mostrarMensaje("Eso no es un numero entero.");
            return;
        }
        boolean insertado = modelo.insertar(valor);
        List<String> eventos = modelo.obtenerEventos();
        if (!insertado) {
            vista.mostrarMensaje("El valor " + valor + " ya existe (no se permiten duplicados).");
            return;
        }
        vista.mostrarMensaje("Se inserto el " + valor + ".");
        vista.mostrarEventos(eventos);
        mostrarArbol();
    }

    private void buscar() {
        Integer valor = vista.pedirNumero("Valor a buscar: ");
        if (valor == null) {
            vista.mostrarMensaje("Eso no es un numero entero.");
            return;
        }
        vista.mostrarMensaje(modelo.buscar(valor)
                ? "El valor " + valor + " SI esta en el arbol."
                : "El valor " + valor + " NO esta en el arbol.");
    }

    private void mostrarArbol() {
        vista.mostrarArbol(modelo.getRaiz(), modelo.altura());
    }

    private void cargarEjemplo() {
        modelo.vaciar();
        for (int valor : EJEMPLO) {
            vista.mostrarMensaje("");
            vista.mostrarMensaje(">>> Insertando " + valor);
            modelo.insertar(valor);
            vista.mostrarEventos(modelo.obtenerEventos());
            mostrarArbol();
        }
    }
}
