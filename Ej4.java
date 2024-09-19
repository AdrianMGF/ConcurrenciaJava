
//Adrián María Gordillo Fernández
//45381691T

/**
 * La clase {@code Ej4} proporciona un ejemplo de procesamiento concurrente utilizando
 * tres procesos: un generador, un visualizador y un impresor. 
 * El {@code Generador} produce caracteres y los coloca en un {@code Buffer}.
 * El {@code Visualizador} toma caracteres del {@code Buffer} y los inserta en una {@code Imagen}.
 * El {@code Impresor} extrae caracteres de la {@code Imagen} y los imprime.
 */
public class Ej4 {

    /**
     * La clase {@code Buffer} representa un buffer circular utilizado para almacenar caracteres
     * entre el generador y el visualizador.
     * <p>
     * El buffer tiene un tamaño fijo y utiliza métodos sincronizados para garantizar que
     * los caracteres sean insertados y extraídos de manera segura en un entorno multihilo.
     * </p>
     */
    static class Buffer {
        private final int N = 10; // Tamaño del buffer
        private char[] buffer = new char[N];
        private int pin = 0; // Índice de inserción
        private int pout = 0; // Índice de extracción
        private int size = 0; // Número actual de elementos en el buffer
        
        /**
         * Inserta un carácter en el buffer. Si el buffer está lleno, el método espera
         * hasta que haya espacio disponible.
         * 
         * @param x el carácter a insertar en el buffer.
         */
        public synchronized void insertar(char x) {
            while (size == N) {
                try {
                    wait(); // Esperar si el buffer está lleno
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            buffer[pin] = x;
            pin = (pin + 1) % N;
            size++;
            notifyAll(); // Notificar a los consumidores
        }
        
        /**
         * Extrae un carácter del buffer. Si el buffer está vacío, el método espera
         * hasta que haya un carácter disponible.
         * 
         * @return el carácter extraído del buffer.
         */
        public synchronized char extraer() {
            while (size == 0) {
                try {
                    wait(); // Esperar si el buffer está vacío
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            char x = buffer[pout];
            pout = (pout + 1) % N;
            size--;
            notifyAll(); // Notificar a los productores
            return x;
        }
    }

    /**
     * La clase {@code Imagen} representa una memoria de imagen que almacena caracteres
     * después de ser procesados por el visualizador. 
     * <p>
     * La imagen tiene un tamaño fijo y utiliza métodos sincronizados para gestionar
     * la inserción y extracción de caracteres en un entorno multihilo.
     * </p>
     */
    static class Imagen {
        private final int N = 10; // Tamaño de la memoria de imagen
        private char[] imagen = new char[N];
        private int pin = 0; // Índice de inserción
        private int pout = 0; // Índice de extracción
        private int size = 0; // Número actual de elementos en la imagen
        private boolean finalizado = false; // Estado de finalización
        
        /**
         * Inserta un carácter en la memoria de imagen. Si la memoria está llena, el método espera
         * hasta que haya espacio disponible. Notifica al impresor si el carácter especial de finalización
         * ('§') es insertado o si la memoria está llena.
         * 
         * @param x el carácter a insertar en la memoria de imagen.
         */
        public synchronized void insertar(char x) {
            while (size == N) {
                try {
                    wait(); // Esperar si la memoria de imagen está llena
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            imagen[pin] = x;
            pin = (pin + 1) % N;
            size++;
            if (size == N || x == '§') { // § es el carácter especial de finalización
                notifyAll(); // Notificar al impresor
            }
        }

        /**
         * Extrae un carácter de la memoria de imagen. Si la memoria está vacía y no se ha completado
         * la inserción de todos los datos, el método espera hasta que haya un carácter disponible o hasta
         * que se haya finalizado la inserción.
         * 
         * @return el carácter extraído de la memoria de imagen.
         */
        public synchronized char extraer() {
            while (size == 0 && !finalizado) {
                try {
                    wait(); // Esperar si la memoria de imagen está vacía y no ha terminado
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            char x = imagen[pout];
            pout = (pout + 1) % N;
            size--;
            if (size == 0) {
                notifyAll(); // Notificar al visualizador para que pueda seguir insertando
            }
            return x;
        }

        /**
         * Marca la memoria de imagen como finalizada. Notifica al impresor para procesar
         * la última pantalla de datos.
         */
        public synchronized void finalizar() {
            finalizado = true;
            notifyAll(); // Notificar al impresor para procesar la última pantalla
        }
    }

    /**
     * La clase {@code Generador} implementa un proceso que genera caracteres aleatorios y
     * los inserta en un {@code Buffer}. Puede generar un carácter especial de finalización ('§')
     * para indicar el final de la generación de datos.
     */
    static class Generador implements Runnable {
        private Buffer buffer;

        /**
         * Constructor para la clase {@code Generador}.
         * 
         * @param buffer el buffer donde se insertarán los caracteres generados.
         */
        public Generador(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            char c;
            do {
                c = generaCaracter();
                buffer.insertar(c);
            } while (c != '§');
        }

        /**
         * Genera un carácter aleatorio. Hay aproximadamente un 5% de probabilidad de generar
         * un carácter especial de finalización ('§').
         * 
         * @return el carácter generado aleatoriamente.
         */
        private char generaCaracter() {
            if (Math.random() < 0.05) { // Aproximadamente 5% de probabilidad de carácter especial
                return '§'; // Carácter especial
            }
            return (char) (Math.random() * 26 + 'A'); // Letras A-Z
        }
    }

    /**
     * La clase {@code Visualizador} implementa un proceso que extrae caracteres de un {@code Buffer}
     * y los inserta en una {@code Imagen}. Termina su trabajo cuando se encuentra con un carácter
     * especial de finalización ('§') y luego marca la imagen como finalizada.
     */
    static class Visualizador implements Runnable {
        private Buffer buffer;
        private Imagen imagen;

        /**
         * Constructor para la clase {@code Visualizador}.
         * 
         * @param buffer el buffer desde donde se extraen los caracteres.
         * @param imagen la imagen donde se insertan los caracteres extraídos.
         */
        public Visualizador(Buffer buffer, Imagen imagen) {
            this.buffer = buffer;
            this.imagen = imagen;
        }

        @Override
        public void run() {
            char c;
            do {
                c = buffer.extraer();
                imagen.insertar(c);
            } while (c != '§');
            imagen.finalizar(); // Notificar al impresor que ya no hay más datos
        }
    }

    /**
     * La clase {@code Impresor} implementa un proceso que extrae caracteres de una {@code Imagen}
     * y los imprime. Termina su trabajo cuando se encuentra con un carácter especial de finalización ('§').
     */
    static class Impresor implements Runnable {
        private Imagen imagen;

        /**
         * Constructor para la clase {@code Impresor}.
         * 
         * @param imagen la imagen desde donde se extraen los caracteres para imprimir.
         */
        public Impresor(Imagen imagen) {
            this.imagen = imagen;
        }

        @Override
        public void run() {
            char c;
            do {
                c = imagen.extraer();
                imprimir(c);
            } while (c != '§');
        }

        /**
         * Simula la impresión de un carácter en la pantalla.
         * 
         * @param c el carácter a imprimir.
         */
        private void imprimir(char c) {
            System.out.print(c);
        }
    }
}
