
//Adrián María Gordillo Fernández
//45381691T

/**
 * La clase {@code UsaFisher} contiene el método {@code main} que inicia la ejecución
 * de los procesos utilizando la clase {@code Fisher}.
 */
public class usaFisher {

    /**
     * El método {@code main} que inicializa y ejecuta dos hilos, cada uno representando
     * un proceso que usa la clase {@code Fisher}.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en esta implementación).
     */
    public static void main(String[] args) {
        // Crear e iniciar dos hilos (representando dos procesos)
        Thread t1 = new Thread(new Fisher(1));
        Thread t2 = new Thread(new Fisher(2));

        t1.start();
        t2.start();
    }
}
