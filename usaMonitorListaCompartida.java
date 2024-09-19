
//Adrián María Gordillo Fernández
//45381691T

/**
 * La clase {@code usaMonitorListaCompartida} es un ejemplo de uso de la clase {@code MonitorListaCompartida}.
 * <p>
 * En el método {@code main}, se crean e inician hilos para realizar operaciones de búsqueda, inserción
 * y borrado en una lista compartida, gestionada por un objeto {@code MonitorListaCompartida}. Los hilos
 * de búsqueda buscan elementos en la lista, los hilos de inserción añaden elementos a la lista y los hilos
 * de borrado eliminan elementos de la lista. La clase demuestra cómo manejar operaciones concurrentes
 * utilizando la sincronización proporcionada por el monitor.
 * </p>
 */
public class usaMonitorListaCompartida {

    /**
     * El método principal que configura y ejecuta hilos para realizar operaciones concurrentes
     * en la lista compartida.
     * <p>
     * Crea hilos para buscadores, insertadores y borradores, y los inicia. Luego, espera a que todos
     * los hilos terminen su ejecución antes de finalizar el programa.
     * </p>
     * 
     * @param args los argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Crear una instancia del monitor de lista compartida
        MonitorListaCompartida monitor = new MonitorListaCompartida();

        // Crear y lanzar hilos para los buscadores
        Thread buscador1 = new Thread(() -> monitor.buscar());
        Thread buscador2 = new Thread(() -> monitor.buscar());

        // Crear y lanzar hilos para los insertores
        Thread inserter1 = new Thread(() -> monitor.insertar(1));
        Thread inserter2 = new Thread(() -> monitor.insertar(2));

        // Crear y lanzar hilos para los borradores
        Thread borrador1 = new Thread(() -> monitor.borrar());
        Thread borrador2 = new Thread(() -> monitor.borrar());

        // Iniciar los hilos
        buscador1.start();
        buscador2.start();
        inserter1.start();
        inserter2.start();
        borrador1.start();
        borrador2.start();

        // Esperar a que todos los hilos terminen
        try {
            buscador1.join();
            buscador2.join();
            inserter1.join();
            inserter2.join();
            borrador1.join();
            borrador2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }
}
