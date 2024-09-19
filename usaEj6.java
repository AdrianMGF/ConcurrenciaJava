

//Adrián María Gordillo Fernández
//45381691T

/**
 * La clase {@code usaEj6} implementa la interfaz {@code Runnable} y simula
 * el uso concurrente de impresoras gestionadas por la clase {@code Ej6}.
 * Cada instancia de {@code usaEj6} intenta solicitar una impresora, realizar
 * una tarea (simulada aquí por la solicitud y liberación de la impresora), y luego
 * liberar la impresora.
 * <p>
 * Se crean varios hilos que ejecutan instancias de {@code usaEj6} en paralelo
 * para demostrar la sincronización y la gestión de recursos compartidos.
 * </p>
 */
public class usaEj6 implements Runnable {

    private Ej6 impresoras = new Ej6(); // Instancia del gestor de impresoras

    /**
     * Constructor para la clase {@code usaEj6}.
     * Inicializa una nueva instancia de {@code Ej6} para gestionar las impresoras.
     */
    public usaEj6() {}

    /**
     * El método {@code run} es el punto de entrada para la ejecución del hilo.
     * Solicita una impresora a la instancia de {@code Ej6}, realiza la tarea
     * de liberarla (simulando el uso de la impresora) y maneja cualquier
     * excepción que pueda ocurrir durante estos procesos.
     */
    @Override
    public void run() {
        try {
            // Solicitar una impresora
            int numImpresora = impresoras.pedirImpresora();
            // Liberar la impresora después de su uso
            impresoras.liberarImpresora(numImpresora);
        } catch (Exception e) {
            // Manejo de excepciones (aquí simplemente se ignoran)
        }
    }

    /**
     * Método principal que crea y ejecuta varios hilos de {@code usaEj6}.
     * Cada hilo representa una instancia de un proceso que solicita y libera
     * impresoras en paralelo.
     * 
     * @param args los argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Crear y empezar 5 hilos de {@code usaEj6}
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new usaEj6());
            thread.start();
        }
    }
}
