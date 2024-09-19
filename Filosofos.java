
//Adrián María Gordillo Fernández
//45381691T



/**
 * La clase {@code Filosofos} implementa el monitor para manejar los tenedores en el problema de los filósofos comensales.
 * Utiliza métodos sincronizados para asegurar que solo un filósofo pueda tomar y liberar tenedores a la vez.
 */
public class Filosofos {
    private static final int NUM_FILOSOFOS = 5;
    private final boolean[] forkAvailable = new boolean[NUM_FILOSOFOS];

    /**
     * Inicializa los tenedores como disponibles.
     */
    public Filosofos() {
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            forkAvailable[i] = true;
        }
    }

    /**
     * El filósofo intenta tomar los tenedores adyacentes. Si los tenedores no están disponibles,
     * el filósofo espera hasta que lo estén.
     *
     * @param i el identificador del filósofo que está intentando tomar los tenedores.
     * @throws InterruptedException si el hilo que está esperando es interrumpido.
     */
    public synchronized void takeForks(int i) throws InterruptedException {
        int left = i;
        int right = (i + 1) % NUM_FILOSOFOS;

        // Espera hasta que ambos tenedores estén disponibles
        while (!forkAvailable[left] || !forkAvailable[right]) {
            wait();
        }

        // Toma los tenedores
        forkAvailable[left] = false;
        forkAvailable[right] = false;
    }

    /**
     * Libera los tenedores adyacentes y notifica a los filósofos que están esperando.
     *
     * @param i el identificador del filósofo que está liberando los tenedores.
     */
    public synchronized void releaseForks(int i) {
        int left = i;
        int right = (i + 1) % NUM_FILOSOFOS;

        // Libera los tenedores
        forkAvailable[left] = true;
        forkAvailable[right] = true;

        // Notifica a los filósofos adyacentes que los tenedores están disponibles
        notifyAll();
    }
}
