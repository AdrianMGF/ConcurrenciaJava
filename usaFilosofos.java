
//Adrián María Gordillo Fernández
//45381691T


/**
 * La clase {@code usaFilosofos} simula el comportamiento de los filósofos comensales.
 * Cada filósofo piensa, toma tenedores, come y luego libera los tenedores.
 */
public class usaFilosofos implements Runnable {
    private final Filosofos filosofos;
    private final int id;

    /**
     * Crea una instancia de {@code usaFilosofos}.
     *
     * @param filosofos el objeto {@code Filosofos} que gestiona los tenedores.
     * @param id el identificador del filósofo.
     */
    public usaFilosofos(Filosofos filosofos, int id) {
        this.filosofos = filosofos;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            think();
            try {
                filosofos.takeForks(id);
                eat();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                filosofos.releaseForks(id);
            }
        }
    }

    /**
     * Simula el pensamiento del filósofo.
     */
    private void think() {
        System.out.println("Filósofo " + id + " está pensando.");
        try {
            Thread.sleep((long) (Math.random() * 1000));  // Pensar por un tiempo aleatorio
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simula la acción de comer del filósofo.
     */
    private void eat() {
        System.out.println("Filósofo " + id + " está comiendo.");
        try {
            Thread.sleep((long) (Math.random() * 1000));  // Comer por un tiempo aleatorio
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Crea y ejecuta los filósofos comensales.
     *
     * @param args argumentos de línea de comandos (no utilizados en esta implementación).
     */
    public static void main(String[] args) {
        Filosofos filosofos = new Filosofos();
        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new usaFilosofos(filosofos, i));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
