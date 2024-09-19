
//Adrián María Gordillo Fernández
//45381691T

/**
 * La clase {@code usaPetersonN} contiene el método {@code main} que crea y ejecuta dos hilos.
 * Estos hilos utilizan el algoritmo de Peterson para sincronización.
 */
public class usaPetersonN {

    /**
     * El punto de entrada principal para la aplicación. Este método crea y ejecuta dos hilos
     * que ejecutan el algoritmo de Peterson para sincronización. Los hilos se identifican
     * como {@code process0} y {@code process1}. Después de iniciar ambos hilos, el método
     * espera a que ambos terminen su ejecución.
     *
     * @param args Argumentos de línea de comandos que no se utilizan en esta aplicación.
     */
    public static void main(String[] args) {
        Thread process0 = new Thread(new PetersonN(0));
        Thread process1 = new Thread(new PetersonN(1));

        process0.start();
        process1.start();

        try {
            process0.join();
            process1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
