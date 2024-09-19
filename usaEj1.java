
//Adrián María Gordillo Fernández
//45381691T


import java.util.concurrent.*;

/**
 * La clase {@code usaEj1} implementa la interfaz {@code Runnable} y utiliza una instancia de
 * la clase {@code Ej1} para ejecutar el método {@code entrada} en un entorno multihilo.
 */
public class usaEj1 implements Runnable {
    
    /**
     * Instancia de la clase {@code Ej1} utilizada para sincronización.
     */
    Ej1 monitor = new Ej1();

    /**
     * Constructor de la clase {@code usaEj1}.
     * Inicializa la instancia {@code monitor}.
     */
    public usaEj1() {}

    /**
     * Ejecuta el método {@code entrada} de la instancia {@code monitor}.
     * Este método es llamado cuando el hilo es ejecutado por el {@code ExecutorService}.
     */
    @Override
    public void run() {
        try {
            monitor.entrada();
        } catch (Exception e) {
            // Manejo de excepción. Por ahora se omite la excepción.
            e.printStackTrace();
        }
    }

    /**
     * Método principal que configura y ejecuta un grupo de hilos utilizando un
     * {@code ExecutorService} para ejecutar instancias de {@code usaEj1}.
     * <p>
     * Crea un {@code ExecutorService} con un grupo de hilos de tamaño variable y
     * ejecuta 9 instancias de {@code usaEj1}. Luego, cierra el servicio y espera a
     * que todos los hilos terminen su ejecución.
     * </p>
     * 
     * @param args argumentos de línea de comandos, no utilizados en este método.
     * @throws InterruptedException si el hilo principal es interrumpido mientras espera
     *         la finalización de todos los hilos.
     */
    public static void main(String[] args) throws InterruptedException {
        // Crear un ExecutorService con un grupo de hilos de tamaño variable.
        ExecutorService exe = Executors.newCachedThreadPool();
        
        // Ejecutar 9 instancias de usaEj1.
        for (int i = 0; i < 9; i++) {
            exe.execute(new usaEj1());
        }
        
        // Cerrar el ExecutorService y esperar a que todos los hilos terminen su ejecución.
        exe.shutdown();
        exe.awaitTermination(1, TimeUnit.DAYS);
    }
}
