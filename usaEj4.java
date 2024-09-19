
//Adrián María Gordillo Fernández
//45381691T



/**
 * La clase {@code usaEj4} es un ejemplo de aplicación que utiliza múltiples hilos
 * para realizar un procesamiento concurrente en un flujo de trabajo compuesto por
 * un generador, un visualizador y un impresor.
 * <p>
 * En este ejemplo, se crean y se inician tres hilos que realizan las siguientes tareas:
 * <ul>
 *   <li>Un {@code Generador} que produce datos y los coloca en un {@code Buffer}.</li>
 *   <li>Un {@code Visualizador} que toma los datos del {@code Buffer}, los procesa y los
 *       almacena en una {@code Imagen}.</li>
 *   <li>Un {@code Impresor} que toma los datos de la {@code Imagen} y los imprime o muestra.</li>
 * </ul>
 * </p>
 */
public class usaEj4 {

    /**
     * Método principal que inicializa y ejecuta los hilos necesarios para la aplicación.
     * <p>
     * Este método realiza las siguientes acciones:
     * <ul>
     *   <li>Crea una instancia de {@code Buffer} para el almacenamiento intermedio de datos.</li>
     *   <li>Crea una instancia de {@code Imagen} para el almacenamiento final de los datos procesados.</li>
     *   <li>Crea y lanza un hilo {@code Generador} para producir datos y almacenarlos en el {@code Buffer}.</li>
     *   <li>Crea y lanza un hilo {@code Visualizador} para leer datos del {@code Buffer}, procesarlos
     *       y almacenarlos en la {@code Imagen}.</li>
     *   <li>Crea y lanza un hilo {@code Impresor} para tomar los datos de la {@code Imagen} y
     *       realizar la impresión o visualización de los datos.</li>
     * </ul>
     * </p>
     *
     * @param args los argumentos de línea de comandos proporcionados al iniciar la aplicación.
     */
    public static void main(String[] args) {
        // Crear instancias de los componentes necesarios.
        Ej4.Buffer buffer = new Ej4.Buffer();
        Ej4.Imagen imagen = new Ej4.Imagen();

        // Crear y lanzar los hilos para el generador, visualizador e impresor.
        Thread generador = new Thread(new Ej4.Generador(buffer));
        Thread visualizador = new Thread(new Ej4.Visualizador(buffer, imagen));
        Thread impresor = new Thread(new Ej4.Impresor(imagen));

        // Iniciar los hilos.
        generador.start();
        visualizador.start();
        impresor.start();
    }
}
