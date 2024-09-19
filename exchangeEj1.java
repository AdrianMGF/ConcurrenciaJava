//Adrián María Gordillo Fernández
//45381691T

import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase exchangeEj1 implementa la interfaz Runnable y simula un
 * mecanismo de exclusión mutua utilizando la operación de intercambio atómico.
 * Utiliza la clase AtomicInteger para asegurar que las operaciones sobre las
 * variables compartidas sean atómicas, proporcionando un ejemplo básico de
 * cómo gestionar el acceso concurrente a una sección crítica en un entorno
 * multi-hilo.
 */
public class exchangeEj1 implements Runnable {

    
    private AtomicInteger r = new AtomicInteger(0);

    
    private AtomicInteger m;

    /**
     * Constructor de la clase exchangeEj1.
     * 
     * @param m La referencia a la variable compartida m, utilizada para el
     *          intercambio atómico de valores y la sincronización entre hilos.
     */
    public exchangeEj1(AtomicInteger m) {
        this.m = m;
    }

    /**
     * Método run que se ejecuta al iniciar el hilo. 
     * Este método contiene un bucle infinito que intenta entrar en la 
     * sección crítica, ejecuta el código de la sección crítica, y luego 
     * sale de la misma.
     */
    @Override
    public void run() {
        while (true) {
            
            do {
                exchange(r, m);
            } while (r.get() == 1);

            
            System.out.println(Thread.currentThread().getName() + " está en la sección crítica.");

            
            exchange(r, m);
        }
    }

    /**
     * Método privado que simula la instrucción exchange de forma atómica.
     * Intercambia los valores de las variables r y m.
     * 
     * @param r La variable local del hilo, utilizada para intentar acceder
     *          a la sección crítica.
     * @param m La variable compartida entre hilos, utilizada para sincronizar
     *          la entrada a la sección crítica.
     */
    private void exchange(AtomicInteger r, AtomicInteger m) {
        int temp = m.get();
        m.set(r.get());
        r.set(temp);
    }
}
