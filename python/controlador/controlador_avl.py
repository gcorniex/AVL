"""CONTROLADOR - Es el intermediario entre la vista y el modelo.

  1. Pide a la VISTA lo que el usuario quiere hacer.
  2. Le ordena al MODELO que lo haga.
  3. Le pide a la VISTA que muestre el resultado.
"""

EJEMPLO = [10, 20, 30, 40, 50, 25]


class ControladorAVL:
    def __init__(self, modelo, vista):
        self.modelo = modelo
        self.vista = vista

    def iniciar(self):
        self.vista.mostrar_bienvenida()
        while True:
            self.vista.mostrar_menu()
            opcion = self.vista.pedir_opcion()
            if opcion == 1:
                self._insertar()
            elif opcion == 2:
                self._buscar()
            elif opcion == 3:
                self._mostrar_arbol()
            elif opcion == 4:
                self.vista.mostrar_en_orden(self.modelo.en_orden())
            elif opcion == 5:
                self._cargar_ejemplo()
            elif opcion == 6:
                self.modelo.vaciar()
                self.vista.mostrar_mensaje("Arbol vaciado.")
            elif opcion == 0:
                self.vista.mostrar_mensaje("Hasta luego!")
                break
            else:
                self.vista.mostrar_mensaje("Opcion no valida.")

    def _insertar(self):
        valor = self.vista.pedir_numero("Valor a insertar: ")
        if valor is None:
            self.vista.mostrar_mensaje("Eso no es un numero entero.")
            return
        insertado = self.modelo.insertar(valor)
        eventos = self.modelo.obtener_eventos()
        if not insertado:
            self.vista.mostrar_mensaje(f"El valor {valor} ya existe (no se permiten duplicados).")
            return
        self.vista.mostrar_mensaje(f"Se inserto el {valor}.")
        self.vista.mostrar_eventos(eventos)
        self._mostrar_arbol()

    def _buscar(self):
        valor = self.vista.pedir_numero("Valor a buscar: ")
        if valor is None:
            self.vista.mostrar_mensaje("Eso no es un numero entero.")
            return
        if self.modelo.buscar(valor):
            self.vista.mostrar_mensaje(f"El valor {valor} SI esta en el arbol.")
        else:
            self.vista.mostrar_mensaje(f"El valor {valor} NO esta en el arbol.")

    def _mostrar_arbol(self):
        self.vista.mostrar_arbol(self.modelo.raiz, self.modelo.altura())

    def _cargar_ejemplo(self):
        self.modelo.vaciar()
        for valor in EJEMPLO:
            self.vista.mostrar_mensaje("")
            self.vista.mostrar_mensaje(f">>> Insertando {valor}")
            self.modelo.insertar(valor)
            self.vista.mostrar_eventos(self.modelo.obtener_eventos())
            self._mostrar_arbol()
