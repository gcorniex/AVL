package app;

import modelo.ArbolAVL;

/**
 * Prueba el MODELO solo, sin vista ni controlador.
 * Esto demuestra la ventaja de MVC: la lógica se puede probar por separado.
 */
public class PruebaModelo {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        for (int v : new int[] {10, 20, 30, 40, 50, 25}) {
            arbol.insertar(v);
        }
        System.out.println("En orden: " + arbol.enOrden());
        System.out.println("Altura:   " + arbol.altura());
        System.out.println("Raiz:     " + arbol.getRaiz().getValor());
        System.out.println("Eventos:  " + arbol.obtenerEventos().size() + " mensajes del modelo");

        check(arbol.enOrden().toString().equals("[10, 20, 25, 30, 40, 50]"), "recorrido en orden");
        check(arbol.altura() == 3, "altura 3");
        check(arbol.getRaiz().getValor() == 30, "raiz 30");
        check(arbol.buscar(25) && !arbol.buscar(99), "busqueda");
        check(!arbol.insertar(30), "no permite duplicados");
        System.out.println("TODAS LAS PRUEBAS PASARON");
    }

    private static void check(boolean condicion, String nombre) {
        if (!condicion) throw new AssertionError("Fallo: " + nombre);
        System.out.println("OK  - " + nombre);
    }
}
