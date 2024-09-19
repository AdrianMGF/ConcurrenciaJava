
//Adrián María Gordillo Fernández
//45381691T








/**
 * La clase prodCon implementa el patrón de productor-consumidor utilizando semáforos.
 * Los productores producen ítems y los colocan en un buffer, mientras que los consumidores
 * toman ítems del buffer y los consumen. Esta implementación usa semáforos binarios para
 * sincronización y evitar condiciones de carrera.
 */
public class prodCon {
    private static final int BUFFER_SIZE = 10;

    private static int[] buffer = new int[BUFFER_SIZE];

    private static int count = 0;

    private static int in = 0;

    private static int out = 0;

    private static binSem mutex = new binSem(1);

    private static binSem empty = new binSem(BUFFER_SIZE);

    private static binSem full = new binSem(0);

    /**
     * Método principal que inicia los hilos del productor y consumidor.
     *
     * @param args argumentos de línea de comandos, no utilizados en esta implementación.
     */
    public static void main(String[] args) {
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());

        producer.start();
        consumer.start();
    }

    /**
     * Clase que representa al productor en el patrón productor-consumidor.
     * El productor produce ítems y los coloca en el buffer.
     */
    static class Producer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    int item = produceItem();  

                    empty.waitSem();           
                    mutex.waitSem();           

                    buffer[in] = item;         
                    in = (in + 1) % BUFFER_SIZE;
                    count++;
                    System.out.println("Productor produce: " + item + " | Elementos en buffer: " + count);

                    mutex.signalSem();         
                    full.signalSem();          
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Simula la producción de un ítem generando un número aleatorio.
         *
         * @return el ítem producido.
         */
        private int produceItem() {
            return (int) (Math.random() * 100); 
        }
    }

    /**
     * Clase que representa al consumidor en el patrón productor-consumidor.
     * El consumidor toma ítems del buffer y los consume.
     */
    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    full.waitSem();            
                    mutex.waitSem();           

                    int item = buffer[out];    
                    out = (out + 1) % BUFFER_SIZE;
                    count--;
                    System.out.println("Consumidor consume: " + item + " | Elementos en buffer: " + count);

                    mutex.signalSem();         
                    empty.signalSem();         

                    consumeItem(item);        
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Simula el tiempo necesario para consumir un ítem.
         *
         * @param item el ítem a consumir.
         */
        private void consumeItem(int item) {
            try {
                Thread.sleep((int) (Math.random() * 1000)); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
