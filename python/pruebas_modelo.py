"""Prueba el MODELO solo, sin vista ni controlador.

Esto demuestra la ventaja de MVC: la lógica se puede probar por separado.
Ejecutar desde la carpeta python/:   python pruebas_modelo.py
"""

from modelo.arbol_avl import ArbolAVL


def check(condicion, nombre):
    assert condicion, f"Fallo: {nombre}"
    print(f"OK  - {nombre}")


arbol = ArbolAVL()
for v in [10, 20, 30, 40, 50, 25]:
    arbol.insertar(v)

print("En orden:", arbol.en_orden())
print("Altura:  ", arbol.altura())
print("Raiz:    ", arbol.raiz.valor)
print("Eventos: ", len(arbol.obtener_eventos()), "mensajes del modelo")

check(arbol.en_orden() == [10, 20, 25, 30, 40, 50], "recorrido en orden")
check(arbol.altura() == 3, "altura 3")
check(arbol.raiz.valor == 30, "raiz 30")
check(arbol.buscar(25) and not arbol.buscar(99), "busqueda")
check(not arbol.insertar(30), "no permite duplicados")
print("TODAS LAS PRUEBAS PASARON")
