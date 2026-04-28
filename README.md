# Análisis de Sistemas de Inventario: Spaghetti vs MVC

Este documento proporciona un análisis técnico detallado de dos implementaciones del mismo sistema de inventario: una versión monolítica ("Spaghetti") y una versión modular basada en el patrón arquitectónico MVC.

---

## 1. Análisis Técnico: ProductsSpaguetti

La versión "Spaghetti" se caracteriza por ser un código monolítico donde toda la lógica de negocio, la interacción con el usuario y la persistencia de datos residen en una sola función y clase.

### Funciones y Lógica
*   **Función `main`**: Es el único punto de ejecución y contiene toda la lógica del sistema. No existe modularización, lo que dificulta la reutilización de código.
*   **Manejo de Listas/Arrays**: 
    *   Utiliza **arreglos primitivos paralelos** (`String[] nombres`, `double[] precios`, `int[] cantidades`).
    *   Depende de una `capacidadMax` definida por el usuario al inicio, lo que limita la escalabilidad dinámica.
    *   Usa un contador `totalProductos` para rastrear cuántos elementos están ocupados en los arreglos.
*   **Persistencia (.txt)**:
    *   Se realiza mediante `PrintWriter` y `FileWriter`.
    *   Los datos se guardan en formato CSV (valores separados por comas).
    *   **Crítica**: Si el usuario olvida seleccionar la opción de guardar, los cambios en memoria se pierden al salir.
*   **Operaciones CRUD**:
    *   **Create**: Opción 1 (`if (opcion == 1)`), solicita datos y los inserta en la siguiente posición libre del arreglo.
    *   **Read**: Opción 2, recorre los arreglos con un bucle `for` y calcula el valor total en tiempo de ejecución.
    *   **Update**: Opción 3, accede directamente al índice del arreglo para modificar el stock.
    *   **Delete**: Opción 4, simplemente asigna `null` a la posición del nombre, lo cual es ineficiente ya que deja "huecos" en el arreglo.

---

## 2. Análisis Técnico: ProductsMVC

La versión MVC (Modelo-Vista-Controlador) organiza el código en capas con responsabilidades únicas, facilitando el mantenimiento y la expansión del sistema.

### Justificación de la Arquitectura Modular
La arquitectura MVC es la mejor opción porque:
1.  **Separación de Concernimientos**: La lógica de datos (Model) no sabe nada de la interfaz (View), y el Controller actúa como mediador.
2.  **Mantenibilidad**: Si se desea cambiar la consola por una interfaz gráfica (GUI), solo se modifica la Vista; el resto del sistema permanece intacto.
3.  **Escalabilidad**: Usa estructuras dinámicas (`ArrayList`) en lugar de arreglos fijos.

### Componentes y Conexiones
*   **Modelo (`Product.java`)**: Representa la entidad del mundo real. Contiene atributos, getters/setters y la lógica de negocio básica (como `getTotalValue()`).
*   **Vista (`InventoryView.java`)**: Encargada exclusivamente de la interacción con el usuario. Lee entradas del teclado y muestra mensajes en consola. No contiene lógica de procesamiento.
*   **Repositorio (`ProductRepository.java`)**: Capa de persistencia. Se encarga de leer y escribir en el archivo `inventory.txt`. Centraliza el manejo de errores de E/S.
*   **Controlador (`InventoryController.java`)**: El "cerebro" del sistema. Coordina las acciones: recibe órdenes de la Vista, manipula el Modelo y solicita persistencia al Repositorio.
*   **Clase Principal (`Main.java`)**: Realiza la **Inyección de Dependencias**. Instancia los componentes y los conecta, iniciando el flujo de trabajo.

### Análisis Técnico Detallado
*   **Persistencia**: Se obtiene a través de `ProductRepository` mediante el uso de `BufferedReader` y `PrintWriter`. Convierte líneas de texto en objetos `Product` (deserialización) y viceversa (serialización).
*   **Dependencias**: El proyecto es autosuficiente. Utiliza únicamente la API estándar de Java (`java.io`, `java.util`). No requiere frameworks externos, lo que lo hace ligero y portable.
*   **Manejo de Datos**: Utiliza `List<Product>`, permitiendo que el inventario crezca dinámicamente sin necesidad de definir un tamaño máximo inicial.

---

## Comparativa Final

| Característica | ProductsSpaguetti | ProductsMVC |
| :--- | :--- | :--- |
| **Estructura** | Un solo archivo (Monolítico) | Múltiples paquetes/clases (Modular) |
| **Acoplamiento** | Muy alto (Difícil de cambiar) | Bajo (Componentes independientes) |
| **Manejo de Datos** | Arreglos fijos (Paralelos) | Objetos en Listas Dinámicas |
| **Reutilización** | Nula | Alta |
| **Persistencia** | Manual en el flujo principal | Centralizada en Repositorio |
