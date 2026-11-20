"""Punto de entrada: crea las tres piezas de MVC y las conecta.

Ejecutar desde la carpeta python/:   python main.py
"""

from modelo.arbol_avl import ArbolAVL
from vista.vista_consola import VistaConsola
from controlador.controlador_avl import ControladorAVL

if __name__ == "__main__":
    modelo = ArbolAVL()
    vista = VistaConsola()
    controlador = ControladorAVL(modelo, vista)
    controlador.iniciar()
