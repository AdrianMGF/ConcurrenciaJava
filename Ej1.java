

//Adrián María Gordillo Fernández
//45381691T




import java.util.*;
import java.util.concurrent.*;

/**
 * La clase {@code Ej1} utiliza un mecanismo de sincronización basado en el objeto {@code lock}
 * para controlar el acceso a la sección crítica en función del valor de la variable {@code n}.
 */
public class Ej1 {
    
    static int n = 0;
    
   
    private final Object lock = new Object();
    
    /**
     * Constructor de la clase {@code Ej1}.
     * Inicializa el contador {@code n} a 0.
     */
    public Ej1() {
        n = 0;
    }

    /**
     * Método sincronizado que incrementa el contador {@code n} y
     * controla el flujo en función de su valor.
     * <p>
     * Si el valor de {@code n} es menor que 3, el hilo actual espera.
     * Si el valor de {@code n} es 3 o mayor, se restablece {@code n} a 0
     * y se notifica a otros hilos para que continúen.
     * </p>
     * @throws InterruptedException si el hilo actual es interrumpido mientras espera.
     */
    public synchronized void entrada() throws InterruptedException {
        n++;
        if (n < 3) {
            // El hilo espera hasta que se notifique.
            lock.wait();
        } else {
            // Restablece el contador y notifica a los hilos en espera.
            n = 0;
            lock.notify();
            lock.notify();
        }
    }
}
