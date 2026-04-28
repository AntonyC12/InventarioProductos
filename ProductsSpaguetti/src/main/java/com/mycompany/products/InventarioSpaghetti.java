package com.mycompany.products;

import java.io.*;
import java.util.Scanner;

public class InventarioSpaghetti {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String nombreArchivo = "inventarioSpaghetti.txt";

        System.out.print("Ingrese la capacidad máxima de su inventario: ");
        int capacidadMax = teclado.nextInt();
        teclado.nextLine();

        String[] nombres = new String[capacidadMax];
        double[] precios = new double[capacidadMax];
        int[] cantidades = new int[capacidadMax];
        int totalProductos = 0;

        // --- CARGA INICIAL (Al abrir el programa) ---
        try {
            File archivo = new File(nombreArchivo);
            if (archivo.exists()) {
                Scanner lector = new Scanner(archivo);
                while (lector.hasNextLine() && totalProductos < capacidadMax) {
                    String[] partes = lector.nextLine().split(",");
                    nombres[totalProductos] = partes[0];
                    precios[totalProductos] = Double.parseDouble(partes[1]);
                    cantidades[totalProductos] = Integer.parseInt(partes[2]);
                    totalProductos++;
                }
                lector.close();
                System.out.println(">>> Datos cargados.");
            }
        } catch (Exception e) {
            System.out.println("No hay datos previos para cargar.");
        }

        int opcion = 0;
        // Ahora el bucle se rompe con la opción 6
        while (opcion != 6) {
            System.out.println("\n--- BAZAR ---");
            System.out.println("1. Registrar productos");
            System.out.println("2. Listar inventario");
            System.out.println("3. Actualizar stock");
            System.out.println("4. Eliminar producto");
            System.out.println("5. GUARDAR CAMBIOS EN ARCHIVO");
            System.out.println("6. SALIR");
            System.out.print("Acción: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            if (opcion == 1) {
                System.out.print("¿Cuántos productos desea ingresar? ");
                int cantidadAIngresar = teclado.nextInt();
                teclado.nextLine();
                for (int i = 0; i < cantidadAIngresar; i++) {
                    if (totalProductos < capacidadMax) {
                        System.out.print("Nombre: ");
                        nombres[totalProductos] = teclado.nextLine();
                        System.out.print("Precio: ");
                        precios[totalProductos] = teclado.nextDouble();
                        System.out.print("Stock: ");
                        cantidades[totalProductos] = teclado.nextInt();
                        teclado.nextLine(); 
                        totalProductos++;
                    }
                }
            } else if (opcion == 2) {
                System.out.println("\nID | Nombre | Precio | Stock | Valor Total");
                for (int i = 0; i < totalProductos; i++) {
                    if (nombres[i] != null) {
                        double valorTotal = precios[i] * cantidades[i];
                        System.out.printf("%d | %s | $%.2f | %d | $%.2f\n", 
                                          i, nombres[i], precios[i], cantidades[i], valorTotal);
                    }
                }
            } else if (opcion == 3) {
                System.out.print("ID del producto: ");
                int id = teclado.nextInt();
                if (id >= 0 && id < totalProductos && nombres[id] != null) {
                    System.out.print("Nuevo stock: ");
                    cantidades[id] = teclado.nextInt();
                }
            } else if (opcion == 4) {
                System.out.print("ID a eliminar: ");
                int id = teclado.nextInt();
                if (id >= 0 && id < totalProductos) {
                    nombres[id] = null;
                }
            } else if (opcion == 5) { 
                // --- NUEVA OPCIÓN: GUARDAR SIN SALIR ---
                try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreArchivo))) {
                    for (int i = 0; i < totalProductos; i++) {
                        if (nombres[i] != null) {
                            escritor.println(nombres[i] + "," + precios[i] + "," + cantidades[i]);
                        }
                    }
                    System.out.println(">>> ¡Archivo actualizado exitosamente!");
                } catch (IOException e) {
                    System.out.println(">>> ERROR al guardar el archivo.");
                }
            }
        }
        System.out.println("Saliendo... (Si no guardaste con la opción 5, los cambios se perdieron)");
    }
}