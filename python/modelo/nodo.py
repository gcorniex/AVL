"""MODELO - Nodo del árbol AVL."""


class Nodo:
    """Guarda el valor, sus dos hijos y su altura.

    Una hoja tiene altura 1; un subárbol vacío (None) tiene altura 0.
    """

    def __init__(self, valor):
        self.valor = valor
        self.altura = 1  # un nodo nuevo siempre entra como hoja
        self.izq = None
        self.der = None

    @staticmethod
    def altura_de(nodo):
        """Altura de un nodo que puede ser None."""
        return 0 if nodo is None else nodo.altura

    def actualizar_altura(self):
        """Recalcula la altura de este nodo a partir de sus hijos."""
        self.altura = 1 + max(Nodo.altura_de(self.izq), Nodo.altura_de(self.der))

    @property
    def factor_balance(self):
        """Factor de balance = altura del hijo izquierdo - altura del hijo derecho."""
        return Nodo.altura_de(self.izq) - Nodo.altura_de(self.der)
