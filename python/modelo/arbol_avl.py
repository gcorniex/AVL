"""MODELO - Árbol AVL (árbol binario de búsqueda auto-balanceado).

IMPORTANTE (MVC): esta clase NO usa print() ni input(). Solo contiene los
datos y la lógica. Cuando ocurre algo interesante (un desbalance, una
rotación) lo anota en una lista de "eventos" y el CONTROLADOR decide si se
lo pasa a la VISTA para mostrarlo.
"""

from modelo.nodo import Nodo


class ArbolAVL:
    def __init__(self):
        self._raiz = None
        self._eventos = []

    # ------------------------- Consultas -------------------------

    @property
    def raiz(self):
        return self._raiz

    def esta_vacio(self):
        return self._raiz is None

    def altura(self):
        return Nodo.altura_de(self._raiz)

    def vaciar(self):
        self._raiz = None
        self._eventos = []

    def buscar(self, valor):
        actual = self._raiz
        while actual is not None:
            if valor == actual.valor:
                return True
            actual = actual.izq if valor < actual.valor else actual.der
        return False

    def en_orden(self):
        """Recorrido en orden: devuelve la lista de valores de menor a mayor."""
        resultado = []
        self._en_orden(self._raiz, resultado)
        return resultado

    def _en_orden(self, nodo, resultado):
        if nodo is not None:
            self._en_orden(nodo.izq, resultado)
            resultado.append(nodo.valor)
            self._en_orden(nodo.der, resultado)

    def obtener_eventos(self):
        """Devuelve los eventos ocurridos desde la última vez y limpia la lista."""
        copia = list(self._eventos)
        self._eventos = []
        return copia

    # ------------------------- Inserción -------------------------

    def insertar(self, valor):
        """Inserta un valor. Devuelve False si ya existía (no se permiten duplicados)."""
        if self.buscar(valor):
            return False
        self._raiz = self._insertar(self._raiz, valor)
        return True

    def _insertar(self, nodo, valor):
        # 1) Inserción normal de un BST
        if nodo is None:
            return Nodo(valor)
        if valor < nodo.valor:
            nodo.izq = self._insertar(nodo.izq, valor)
        else:
            nodo.der = self._insertar(nodo.der, valor)
        # 2) Actualizar la altura de este nodo
        nodo.actualizar_altura()
        # 3 y 4) Calcular el factor de balance y rebalancear si hace falta
        return self._balancear(nodo, valor)

    def _balancear(self, nodo, valor):
        fb = nodo.factor_balance

        if fb > 1 and valor < nodo.izq.valor:            # Izquierda-Izquierda
            self._registrar_desbalance(nodo, fb, "Izquierda-Izquierda")
            return self._rotacion_derecha(nodo)
        if fb < -1 and valor > nodo.der.valor:           # Derecha-Derecha
            self._registrar_desbalance(nodo, fb, "Derecha-Derecha")
            return self._rotacion_izquierda(nodo)
        if fb > 1 and valor > nodo.izq.valor:            # Izquierda-Derecha
            self._registrar_desbalance(nodo, fb, "Izquierda-Derecha (rotación doble)")
            nodo.izq = self._rotacion_izquierda(nodo.izq)
            return self._rotacion_derecha(nodo)
        if fb < -1 and valor < nodo.der.valor:           # Derecha-Izquierda
            self._registrar_desbalance(nodo, fb, "Derecha-Izquierda (rotación doble)")
            nodo.der = self._rotacion_derecha(nodo.der)
            return self._rotacion_izquierda(nodo)
        return nodo  # ya estaba balanceado

    def _registrar_desbalance(self, nodo, fb, caso):
        self._eventos.append(f"Desbalance en el nodo {nodo.valor} (factor {fb}) -> caso {caso}")

    # ------------------------- Rotaciones -------------------------

    def _rotacion_derecha(self, y):
        self._eventos.append(f"   Rotación a la DERECHA sobre el nodo {y.valor}")
        x = y.izq
        t2 = x.der

        x.der = y
        y.izq = t2

        y.actualizar_altura()  # primero y (quedó abajo)
        x.actualizar_altura()  # luego x (quedó arriba)
        return x

    def _rotacion_izquierda(self, x):
        self._eventos.append(f"   Rotación a la IZQUIERDA sobre el nodo {x.valor}")
        y = x.der
        t2 = y.izq

        y.izq = x
        x.der = t2

        x.actualizar_altura()
        y.actualizar_altura()
        return y
