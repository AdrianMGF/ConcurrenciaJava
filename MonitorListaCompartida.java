//Adrián María Gordillo Fernández
//45381691T



import java.util.LinkedList;
import java.util.List;

/**
 * La clase {@code MonitorListaCompartida} proporciona un mecanismo de sincronización
 * para manejar una lista compartida en un entorno concurrente. 
 * <p>
 * La lista puede ser accedida de manera segura por múltiples hilos mediante métodos sincronizados
 * que permiten operaciones de búsqueda, inserción y borrado. Los métodos están diseñados para
 * asegurar la consistencia y evitar problemas de sincronización entre diferentes tipos de
 * operaciones en la lista.
 * </p>
 */
public class MonitorListaCompartida {

    private final List<Integer> lista = new LinkedList<>(); // Lista compartida
    private boolean borradorActivo = false; // Indica si hay un borrador activo
    private int insertadoresActivos = 0; // Contador de insertadores activos

    /**
     * Método que permite a un hilo realizar una operación de búsqueda en la lista.
     * <p>
     * El método espera si hay un borrador activo en curso, ya que no se permite
     * realizar búsquedas mientras se está borrando la lista. La búsqueda se realiza
     * de manera concurrente una vez que se ha garantizado que no hay operaciones
     * de borrado en curso.
     * </p>
     */
    public synchronized void buscar() {
        try {
            // Esperar si hay un borrador activo
            while (borradorActivo) {
                wait();
            }
            // Realizar la operación de búsqueda
            System.out.println("Buscando en la lista: " + lista);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }

    /**
     * Método que permite a un hilo insertar un elemento en la lista.
     * <p>
     * El método espera si hay un borrador activo o si hay otros insertadores activos.
     * Solo se permite la inserción cuando no hay ningún borrador en curso y no hay
     * otros insertadores realizando operaciones. Después de insertar el elemento, se
     * notifica a otros hilos que podrían estar esperando.
     * </p>
     * 
     * @param elemento el elemento a insertar en la lista.
     */
    public synchronized void insertar(int elemento) {
        try {
            // Esperar si hay un borrador activo o si hay otros insertadores activos
            while (borradorActivo || insertadoresActivos > 0) {
                wait();
            }
            insertadoresActivos++;
            // Realizar la operación de inserción
            lista.add(elemento);
            System.out.println("Elemento insertado: " + elemento);
            insertadoresActivos--;
            // Notificar a los procesos que estaban esperando
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }

    /**
     * Método que permite a un hilo realizar una operación de borrado en la lista.
     * <p>
     * El método espera si hay un borrador activo o si hay otros insertadores activos.
     * Solo se permite el borrado cuando no hay ningún borrador en curso y no hay
     * otros insertadores realizando operaciones. Después de realizar el borrado, se
     * notifica a otros hilos que podrían estar esperando.
     * </p>
     */
    public synchronized void borrar() {
        try {
            // Esperar si hay otros procesos activos (insertadores o buscadores)
            while (borradorActivo || insertadoresActivos > 0) {
                wait();
            }
            borradorActivo = true;
            // Realizar la operación de borrado
            if (!lista.isEmpty()) {
                Integer eliminado = lista.remove(0);
                System.out.println("Elemento borrado: " + eliminado);
            }
            borradorActivo = false;
            // Notificar a los procesos que estaban esperando
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }
}
