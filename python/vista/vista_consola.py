"""VISTA - Todo lo que el usuario ve y escribe en la consola.

No contiene lógica del árbol: solo muestra datos y pide datos.
"""


class VistaConsola:
    def mostrar_bienvenida(self):
        print("==========================================")
        print("   ARBOL AVL  -  Patron MVC (Python)")
        print("==========================================")

    def mostrar_menu(self):
        print()
        print("--------------- MENU ---------------")
        print("1. Insertar un valor")
        print("2. Buscar un valor")
        print("3. Mostrar el arbol")
        print("4. Recorrido en orden")
        print("5. Cargar ejemplo (10, 20, 30, 40, 50, 25)")
        print("6. Vaciar el arbol")
        print("0. Salir")

    def pedir_opcion(self):
        """Pide la opción del menú. Devuelve -1 si no es un número y 0 si se acaba la entrada."""
        try:
            return int(input("Elige una opcion: ").strip())
        except ValueError:
            return -1
        except EOFError:
            return 0

    def pedir_numero(self, mensaje):
        """Pide un número entero. Devuelve None si lo escrito no es un número."""
        try:
            return int(input(mensaje).strip())
        except (ValueError, EOFError):
            return None

    def mostrar_mensaje(self, mensaje):
        print(mensaje)

    def mostrar_eventos(self, eventos):
        for e in eventos:
            print(e)

    def mostrar_en_orden(self, valores):
        print("En orden:", valores)

    def mostrar_arbol(self, raiz, altura):
        """Dibuja el árbol "acostado": la raíz a la izquierda, hijos derechos arriba e izquierdos abajo."""
        if raiz is None:
            print("(el arbol esta vacio)")
            return
        print("Arbol (de lado, entre parentesis el factor de balance):")
        self._dibujar(raiz, 0)
        print(f"Altura del arbol: {altura}")

    def _dibujar(self, nodo, nivel):
        if nodo is None:
            return
        self._dibujar(nodo.der, nivel + 1)
        print("        " * nivel + f"{nodo.valor} (fb={nodo.factor_balance})")
        self._dibujar(nodo.izq, nivel + 1)
