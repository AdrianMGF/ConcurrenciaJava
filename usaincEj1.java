//Adrián María Gordillo Fernández
//45381691T

import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase usaincEj1 se utiliza para demostrar el uso de la clase incEj1.
 * Esta clase crea y ejecuta dos hilos que simulan procesos concurrentes,
 * utilizando un mecanismo de exclusión mutua basado en la operación de 
 * incremento atómico implementada en incEj1.
 */
public class usaincEj1 {

    private static AtomicInteger m = new AtomicInteger(1);

    /**
     * El método main es el punto de entrada de la aplicación.
     * Crea instancias de la clase incEj1 y las ejecuta en hilos separados
     * para simular procesos concurrentes que compiten por acceder a una 
     * sección crítica.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        incEj1 proceso1 = new incEj1(m);
        incEj1 proceso2 = new incEj1(m);

        Thread hilo1 = new Thread(proceso1, "Proceso 1");
        Thread hilo2 = new Thread(proceso2, "Proceso 2");

        hilo1.start();
        hilo2.start();
    }
}
