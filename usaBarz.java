
//Adrián María Gordillo Fernández
//45381691T

/**
 * La clase {@code usaBarz} contiene el método {@code main} que demuestra el uso de la clase {@code Barz}.
 * Crea y gestiona múltiples hilos que utilizan el mecanismo de sincronización definido en {@code Barz}.
 */
public class usaBarz {

    /**
     * El punto de entrada de la aplicación que demuestra la sincronización utilizando {@code Barz}.
     *
     * @param args los argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Barz barz = new Barz(3); // Inicializa `Barz` con k = 3

        Runnable waitTask = () -> {
            try {
                barz.generalWait();
                System.out.println(Thread.currentThread().getName() + " has entered.");
                Thread.sleep(1000); // Simula trabajo en la sección crítica
                barz.generalSignal();
                System.out.println(Thread.currentThread().getName() + " has exited.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Crear y empezar múltiples hilos para demostrar la sincronización
        Thread t1 = new Thread(waitTask, "Thread-1");
        Thread t2 = new Thread(waitTask, "Thread-2");
        Thread t3 = new Thread(waitTask, "Thread-3");
        Thread t4 = new Thread(waitTask, "Thread-4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Esperar a que todos los hilos terminen
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
