
//Adrián María Gordillo Fernández
//45381691T


/**
 * La clase {@code Fisher} representa un proceso que usa un algoritmo de exclusión mutua
 * para acceder a una sección crítica. Implementa la interfaz {@code Runnable} para
 * ser ejecutada en un hilo.
 */
public class Fisher implements Runnable {
    private int id;
    private static volatile int gate = 0; // Variable compartida para la sincronización
    private static final Object lock = new Object(); // Objeto de bloqueo para la sincronización

    /**
     * Constructor de la clase {@code Fisher}.
     *
     * @param id El identificador del proceso.
     */
    public Fisher(int id) {
        this.id = id;
    }

    /**
     * Método que ejecuta el proceso en un hilo. El proceso realiza operaciones no críticas,
     * intenta entrar en la sección crítica, realiza operaciones en la sección crítica y
     * luego sale de la sección crítica.
     */
    @Override
    public void run() {
        while (true) {
            // Sección no crítica
            realizarOperacionesNoCriticas();

            // Sección de entrada
            while (true) {
                synchronized (lock) {
                    // Esperar hasta que el gate sea 0
                    if (gate == 0) {
                        gate = id; // Establecer el gate al ID del hilo actual
                        // Simular un retraso
                        try {
                            Thread.sleep(10); // Ajustar el retraso según sea necesario
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        // Comprobar si el gate sigue siendo el ID de este hilo
                        if (gate == id) {
                            break; // Salir del bucle y entrar en la sección crítica
                        }
                    }
                }
            }

            // Sección crítica
            realizarOperacionesCriticas();

            // Sección de salida
            synchronized (lock) {
                gate = 0; // Restablecer el gate a 0
            }

            // Opcional: Simular trabajo fuera de la sección crítica
            realizarOperacionesNoCriticas();
        }
    }

    /**
     * Simula la realización de operaciones no críticas.
     */
    private void realizarOperacionesNoCriticas() {
        System.out.println("Proceso " + id + " está realizando operaciones no críticas.");
        try {
            Thread.sleep(100); // Ajustar el retraso según sea necesario
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simula la realización de operaciones críticas en la sección crítica.
     */
    private void realizarOperacionesCriticas() {
        System.out.println("Proceso " + id + " está en la sección crítica.");
        try {
            Thread.sleep(100); // Simular algún trabajo en la sección crítica
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
