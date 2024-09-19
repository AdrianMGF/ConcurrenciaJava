//Adrián María Gordillo Fernández
//45381691T


import java.util.concurrent.Semaphore;

/**
 * La clase {@code Barz} implementa un mecanismo de sincronización basado en semáforos binarios.
 * Utiliza semáforos para gestionar el acceso a una sección crítica y coordinar entre hilos.
 */
public class Barz {

    private final Semaphore mutex;  // Semáforo para exclusión mutua
    private final Semaphore gate;   // Semáforo para coordinar el acceso
    private int value;              // Contador de hilos en la sección crítica

    /**
     * Crea una instancia de {@code Barz} con un valor inicial especificado.
     *
     * @param k el número inicial de hilos permitidos en la sección crítica
     */
    public Barz(int k) {
        this.mutex = new Semaphore(1);  // Inicialmente señalizado (permite acceso)
        this.gate = new Semaphore(1);   // Inicialmente señalizado (permite acceso)
        this.value = k;
    }

    /**
     * Espera para entrar en la sección crítica. Decrementa el contador de hilos permitidos
     * y maneja la liberación del semáforo de coordinación.
     *
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void generalWait() throws InterruptedException {
        gate.acquire();        // Adquiere el semáforo `gate`
        mutex.acquire();       // Adquiere el semáforo `mutex`
        try {
            value -= 1;
            if (value > 0) {
                gate.release();   // Libera el semáforo `gate` si aún hay más hilos
            }
        } finally {
            mutex.release();   // Libera el semáforo `mutex`
        }
    }

    /**
     * Señala que un hilo ha salido de la sección crítica. Incrementa el contador de hilos
     * permitidos y maneja la liberación del semáforo de coordinación.
     *
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void generalSignal() throws InterruptedException {
        mutex.acquire();       // Adquiere el semáforo `mutex`
        try {
            value += 1;
            if (value == 1) {
                gate.release();   // Libera el semáforo `gate` si este es el primer hilo
            }
        } finally {
            mutex.release();   // Libera el semáforo `mutex`
        }
    }
}
