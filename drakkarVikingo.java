
//Adrián María Gordillo Fernández
//45381691T

import java.util.*;
import java.util.concurrent.*;

/**
 * La clase {@code drakkarVikingo} simula un sistema en el que un grupo de vikingos
 * comen de una marmita y un cocinero la llena cuando está vacía.
 * <p>
 * La clase utiliza sincronización para gestionar el acceso a la marmita y asegurar
 * que los vikingos y el cocinero no interfieran entre sí. La marmita tiene una capacidad
 * fija y el cocinero debe esperar a que esté vacía antes de llenarla.
 * </p>
 */
public class drakkarVikingo {
    private static int marmita = 0; // Cantidad de marmita disponible

    /**
     * Constructor para la clase {@code drakkarVikingo}.
     * Inicializa la marmita con una cantidad fija de 5 unidades.
     */
    public drakkarVikingo() {
        marmita = 5;
    }

    /**
     * Permite a un vikingo comer de la marmita. Si la marmita está vacía, el vikingo
     * debe esperar a que el cocinero la llene. Una vez que la marmita tiene comida,
     * el vikingo consume una unidad de la misma y muestra un mensaje con la cantidad
     * restante.
     * 
     * @throws InterruptedException si el hilo que está esperando es interrumpido.
     */
    public synchronized void comer() throws InterruptedException {
        // Esperar hasta que la marmita tenga comida
        while (marmita == 0) {
            notifyAll(); // Notificar al cocinero que podría estar esperando
            wait(); // Esperar hasta que la marmita tenga comida
        }
        // Consumir una unidad de comida
        marmita--;
        System.out.println("Acabo de comer, marmita=" + marmita);
    }

    /**
     * Permite al cocinero llenar la marmita con comida. Si la marmita ya tiene comida,
     * el cocinero debe esperar a que esté vacía antes de llenarla. Después de llenar
     * la marmita, notifica a los vikingos que pueden comenzar a comer.
     * 
     * @throws InterruptedException si el hilo que está esperando es interrumpido.
     */
    public synchronized void llenar() throws InterruptedException {
        // Esperar hasta que la marmita esté vacía
        while (marmita > 0) {
            wait(); // Esperar hasta que la marmita esté vacía
        }
        // Llenar la marmita
        marmita = 5;
        System.out.println("El cocinero acaba de cocinar");
        notifyAll(); // Notificar a los vikingos que la marmita está llena
    }
}
