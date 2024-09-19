
//Adrián María Gordillo Fernández
//45381691T

import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase usaexchangeEj1 se utiliza para demostrar el uso de la clase exchangeEj1.
 * Esta clase crea y ejecuta dos hilos que simulan procesos concurrentes, 
 * utilizando un mecanismo de exclusión mutua implementado en exchangeEj1.
 */
public class usaexchangeEj1 {
    
    
    private static AtomicInteger m = new AtomicInteger(1);

    /**
     * El método main es el punto de entrada de la aplicación.
     * Crea instancias de la clase exchangeEj1 y las ejecuta en hilos separados
     * para simular procesos concurrentes que compiten por acceder a una sección crítica.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        
        exchangeEj1 proceso1 = new exchangeEj1(m);
        exchangeEj1 proceso2 = new exchangeEj1(m);

        
        Thread hilo1 = new Thread(proceso1, "Proceso 1");
        Thread hilo2 = new Thread(proceso2, "Proceso 2");

        
        hilo1.start();
        hilo2.start();
    }
}