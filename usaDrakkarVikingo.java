
//Adrián María Gordillo Fernández
//45381691T


import java.util.concurrent.*;
import java.util.*;

/**
 * La clase {@code usaDrakkarVikingo} implementa la interfaz {@code Runnable} y simula
 * el comportamiento de vikingos que comen de una marmita y un cocinero que la llena
 * cuando está vacía. 
 * <p>
 * Cada instancia de {@code usaDrakkarVikingo} representa un hilo que actúa como un vikingo
 * o como el cocinero. Los vikingos intentan comer de la marmita, mientras que el cocinero
 * la llena cuando está vacía. La sincronización entre los hilos se maneja a través de la
 * clase {@code drakkarVikingo}.
 * </p>
 */
public class usaDrakkarVikingo implements Runnable {
    private int tipohilo; // Tipo de hilo: 0 para vikingos, 1 para el cocinero
    private static drakkarVikingo m = new drakkarVikingo(); // Instancia compartida de la marmita

    /**
     * Constructor para la clase {@code usaDrakkarVikingo}.
     * 
     * @param n el tipo de hilo: 0 para vikingos y 1 para el cocinero.
     */
    public usaDrakkarVikingo(int n) {
        this.tipohilo = n;
    }

    /**
     * Método que define el comportamiento del hilo. Los vikingos intentan comer de la marmita
     * en un bucle, y el cocinero llena la marmita cuando está vacía.
     * 
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    @Override
    public void run() {
        try {
            switch (tipohilo) {
                case 0:
                    // Comportamiento de los vikingos: intentan comer 10 veces
                    for (int i = 0; i < 10; i++) {
                        m.comer();
                    }
                    break;
                case 1:
                    // Comportamiento del cocinero: llena la marmita indefinidamente
                    while (true) {
                        m.llenar();
                    }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones
        }
    }

    /**
     * Método principal que configura y ejecuta los hilos.
     * Crea un hilo para el cocinero y varios hilos para los vikingos,
     * gestionados por un {@code ExecutorService}.
     * 
     * @param args los argumentos de la línea de comandos (no utilizados en este caso).
     * @throws InterruptedException si el hilo principal es interrumpido mientras espera
     *                              a que terminen los hilos.
     */
    public static void main(String[] args) throws InterruptedException {
        // Crear un ExecutorService para gestionar los hilos
        ExecutorService exe = Executors.newCachedThreadPool();
        // Ejecutar un hilo para el cocinero
        exe.execute(new usaDrakkarVikingo(1));
        // Ejecutar 10 hilos para los vikingos
        for (int i = 0; i < 10; i++) {
            exe.execute(new usaDrakkarVikingo(0));
        }
        // Apagar el ExecutorService y esperar a que todos los hilos terminen
        exe.shutdown();
        exe.awaitTermination(1, TimeUnit.DAYS);
    }
}
