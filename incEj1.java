//Adrián María Gordillo Fernández
//45381691T

import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase incEj1 implementa la interfaz Runnable y simula un
 * mecanismo de exclusión mutua mediante un incremento atómico.
 * Utiliza la clase AtomicInteger para asegurar que las operaciones
 * sobre las variables compartidas sean atómicas, proporcionando un 
 * ejemplo de cómo gestionar el acceso concurrente a una sección crítica 
 * en un entorno multi-hilo.
 */
public class incEj1 implements Runnable {

    
    private AtomicInteger r = new AtomicInteger(0);

   
    private AtomicInteger m;

    /**
     * Constructor de la clase incEj1.
     * 
     * @param m La referencia a la variable compartida m, utilizada para 
     *          incrementar el valor y sincronizar los hilos.
     */
    public incEj1(AtomicInteger m) {
        this.m = m;
    }

    /**
     * Método run que se ejecuta al iniciar el hilo.
     * Contiene un bucle infinito que intenta entrar en la sección crítica,
     * ejecuta el código de la sección crítica y luego sale de la misma.
     */
    @Override
    public void run() {
        while (true) {
            
            do {
                addc(r, m);
            } while (r.get() == 0);

            
            System.out.println(Thread.currentThread().getName() + " está en la sección crítica.");

            
            m.set(1);
        }
    }

    /**
     * Método privado que simula una operación atómica de incremento.
     * Incrementa el valor de la variable compartida m y lo asigna a r.
     * 
     * @param r La variable local del hilo, utilizada para intentar acceder 
     *          a la sección crítica.
     * @param m La variable compartida entre hilos, utilizada para 
     *          sincronizar la entrada a la sección crítica.
     */
    private void addc(AtomicInteger r, AtomicInteger m) {
        m.incrementAndGet();
        r.set(m.get());
    }
}
