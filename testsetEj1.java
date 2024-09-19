//Adrián María Gordillo Fernández
//45381691T

import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase testsetEj1 implementa la interfaz Runnable y simula un
 * mecanismo de exclusión mutua utilizando la operación atómica
 * compareAndSet. Este método se basa en una operación de "test and set" 
 * para coordinar el acceso a una sección crítica en un entorno multi-hilo.
 */
public class testsetEj1 implements Runnable {

    private static AtomicInteger m;

    /**
     * Constructor de la clase testsetEj1.
     * 
     * @param m La referencia a la variable compartida m, utilizada para
     *          sincronizar los hilos y controlar la entrada a la sección crítica.
     */
    public testsetEj1(AtomicInteger m) {
        this.m = m;
    }

    /**
     * Método run que se ejecuta al iniciar el hilo.
     * Contiene un bucle infinito que intenta entrar en la sección crítica
     * utilizando la operación test and set, ejecuta el código de la sección
     * crítica y luego libera el acceso a la misma.
     */
    @Override
    public void run() {
        while (true) {
            while (!testset(m)) {}

            System.out.println(Thread.currentThread().getName() + " está en la sección crítica.");

            m.set(0);
        }
    }

    /**
     * Método privado que implementa la operación atómica de "test and set".
     * 
     * @param m La variable compartida entre hilos, utilizada para
     *          sincronizar la entrada a la sección crítica.
     * @return true si la operación compareAndSet tuvo éxito, lo que significa
     *         que el hilo pudo entrar en la sección crítica; false en caso contrario.
     */
    private boolean testset(AtomicInteger m) {
        return m.compareAndSet(0, 1);
    }
}
