package app;

import controlador.ControladorAVL;
import modelo.ArbolAVL;
import vista.VistaConsola;

/** Punto de entrada: crea las tres piezas de MVC y las conecta. */
public class Main {
    public static void main(String[] args) {
        ArbolAVL modelo = new ArbolAVL();
        VistaConsola vista = new VistaConsola();
        ControladorAVL controlador = new ControladorAVL(modelo, vista);
        controlador.iniciar();
    }
}
