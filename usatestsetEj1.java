//Adrián María Gordillo Fernández
//45381691T

import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase usatestsetEj1 se utiliza para demostrar el uso de la clase testsetEj1.
 * Esta clase crea y ejecuta dos hilos que simulan procesos concurrentes,
 * utilizando un mecanismo de exclusión mutua basado en la operación atómica
 * compareAndSet implementada en testsetEj1.
 */
public class usatestsetEj1 {

    private static AtomicInteger m = new AtomicInteger(0);

    /**
     * El método main es el punto de entrada de la aplicación.
     * Crea instancias de la clase testsetEj1 y las ejecuta en hilos separados
     * para simular procesos concurrentes que compiten por acceder a una
     * sección crítica.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        testsetEj1 proceso1 = new testsetEj1(m);
        testsetEj1 proceso2 = new testsetEj1(m);

        Thread hilo1 = new Thread(proceso1, "Proceso 1");
        Thread hilo2 = new Thread(proceso2, "Proceso 2");

        hilo1.start();
        hilo2.start();
    }
}
