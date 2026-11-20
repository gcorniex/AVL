# Árboles AVL con el patrón MVC (Java y Python)

Implementación de un **árbol AVL** (árbol binario de búsqueda auto-balanceado) organizada con el patrón
**Modelo - Vista - Controlador (MVC)**, en dos lenguajes: **Java** y **Python**.
Ambas versiones hacen exactamente lo mismo, para poder comparar cómo se aplica MVC en cada lenguaje.

## ¿Qué es un árbol AVL?
Un árbol binario de búsqueda que se mantiene balanceado: en **cada nodo**, la diferencia de alturas entre su
hijo izquierdo y su hijo derecho (el *factor de balance*) debe ser -1, 0 o 1. Si una inserción rompe esa regla,
el árbol se corrige con **rotaciones** (simples o dobles). Así buscar, insertar y borrar cuestan siempre **O(log n)**.

## ¿Cómo se aplica MVC?

```
   Usuario
      |  (elige una opción del menú)
      v
 +-------------+   pide datos / muestra resultados   +-----------+
 | CONTROLADOR | <---------------------------------> |   VISTA   |
 +-------------+                                     +-----------+
      |  (ordena: insertar, buscar...)
      v
 +-------------+
 |   MODELO    |   <- árbol AVL: Nodo + ArbolAVL (lógica y datos)
 +-------------+
```

| Pieza | Responsabilidad | Java | Python |
|---|---|---|---|
| **Modelo** | Datos y lógica del árbol: nodos, insertar, buscar, factor de balance, rotaciones. **No imprime nada ni lee del usuario.** | `modelo/Nodo.java`, `modelo/ArbolAVL.java` | `modelo/nodo.py`, `modelo/arbol_avl.py` |
| **Vista** | Todo lo que el usuario ve y escribe: menú, dibujo del árbol, mensajes. **No contiene lógica del árbol.** | `vista/VistaConsola.java` | `vista/vista_consola.py` |
| **Controlador** | Intermediario: recibe la opción del usuario, se la ordena al modelo y le pide a la vista que muestre el resultado. | `controlador/ControladorAVL.java` | `controlador/controlador_avl.py` |
| **Main** | Crea las tres piezas y las conecta. | `app/Main.java` | `main.py` |

**Detalle clave:** las rotaciones y los desbalances ocurren dentro del modelo, pero el modelo no puede imprimir.
Por eso los anota en una lista de *eventos* (`obtenerEventos()` / `obtener_eventos()`); el controlador los recoge y
se los entrega a la vista para que los muestre. Así la lógica queda separada de la presentación.

**Ventaja de MVC en este proyecto:** como el modelo no depende de la consola, se puede probar solo
(`PruebaModelo.java` / `pruebas_modelo.py`) y mañana se podría cambiar la vista por una interfaz gráfica sin tocar el árbol.

## Estructura del repositorio

```
ArbolesAVL-MVC/
├── README.md
├── java/                      <- proyecto de Eclipse
│   └── src/
│       ├── modelo/            Nodo.java, ArbolAVL.java
│       ├── vista/             VistaConsola.java
│       ├── controlador/       ControladorAVL.java
│       └── app/               Main.java, PruebaModelo.java
└── python/
    ├── main.py
    ├── pruebas_modelo.py
    ├── modelo/                nodo.py, arbol_avl.py
    ├── vista/                 vista_consola.py
    └── controlador/           controlador_avl.py
```

## Cómo ejecutarlo

### Java (Eclipse)
1. `File > Import > General > Existing Projects into Workspace` y elegir la carpeta `java/`.
2. Clic derecho en `src/app/Main.java` > `Run As > Java Application`.
3. Para probar solo el modelo: clic derecho en `src/app/PruebaModelo.java` > `Run As > Java Application`.

Requiere Java 11 o superior.

### Python
Desde la carpeta `python/`:
```bash
python main.py              # programa con menú (usa MVC completo)
python pruebas_modelo.py    # prueba solo el modelo
```
Requiere Python 3.6 o superior. No necesita instalar librerías.

## Menú del programa

| Opción | Qué hace |
|---|---|
| 1 | Insertar un valor (muestra los desbalances y rotaciones que ocurran) |
| 2 | Buscar un valor |
| 3 | Mostrar el árbol dibujado de lado, con el factor de balance de cada nodo |
| 4 | Recorrido en orden (siempre sale ordenado) |
| 5 | Cargar el ejemplo `10, 20, 30, 40, 50, 25` paso a paso (activa 3 rotaciones) |
| 6 | Vaciar el árbol |
| 0 | Salir |

## Ejemplo: `10, 20, 30, 40, 50, 25`

| Dato | Qué ocurre |
|---|---|
| 10, 20 | Sin problema |
| 30 | Derecha-Derecha: rotación izquierda sobre 10 |
| 40 | Sin problema |
| 50 | Derecha-Derecha: rotación izquierda sobre 30 |
| 25 | Derecha-Izquierda: rotación derecha sobre 40 y luego izquierda sobre 20 |

Árbol final (altura 3; un árbol de búsqueda normal con esos datos tendría altura 5):
```
        30
      /    \
    20      40
   /  \       \
  10   25      50
```

## Autores
Santiago, Andrés, Sofía y Gabriela.
