


//Adrián María Gordillo Fernández
//45381691T



/**
 * La clase <code>binSem</code> implementa un semáforo binario utilizando
 * el mecanismo de sincronización de Java basado en monitores.
 * 
 * Un semáforo binario es una estructura de sincronización que permite la
 * coordinación entre hilos, utilizando dos operaciones principales: wait
 * y signal. Esta implementación utiliza métodos sincronizados
 * para gestionar el estado del semáforo y la espera de hilos.
 */
public class binSem {
    private int value;

    /**
     * Crea un nuevo semáforo binario con el valor inicial especificado.
     *
     * @param initialValue el valor inicial del semáforo. Debe ser 0 o 1 para un semáforo binario.
     */
    public binSem(int initialValue) {
        this.value = initialValue;
    }

    /**
     * Realiza la operación de espera en el semáforo. Si el valor del semáforo es menor o igual a 0,
     * el hilo que invoca este método se bloqueará hasta que otro hilo llame a <code>signalSem</code>
     * para incrementar el valor del semáforo.
     */
    public synchronized void waitSem() {
        while (value <= 0) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
            }
        }
        value--; 
    }

    /**
     * Realiza la operación de señalización en el semáforo. Incrementa el valor del semáforo y notifica
     * a uno de los hilos que están esperando en <code>waitSem</code> que el semáforo tiene ahora
     * un valor mayor o igual a 1.
     */
    public synchronized void signalSem() {
        value++; 
        notify(); 
    }
}
