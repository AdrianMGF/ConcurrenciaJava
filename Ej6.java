
//Adrián María Gordillo Fernández
//45381691T


/**
 * La clase {@code Ej6} gestiona un conjunto de impresoras disponibles en un sistema.
 * Proporciona métodos para solicitar y liberar impresoras de manera sincronizada,
 * asegurando que las solicitudes y liberaciones se manejen de forma segura en un
 * entorno multihilo.
 * <p>
 * La clase utiliza un array de booleanos para rastrear el estado de disponibilidad
 * de cada impresora y un contador para llevar la cuenta de cuántas impresoras están
 * actualmente disponibles.
 * </p>
 */
class Ej6 {
    private int impresorasDisponibles = 3; // Número total de impresoras disponibles
    private boolean[] libre = {true, true, true}; // Estado de disponibilidad de cada impresora

    /**
     * Solicita una impresora. Si no hay impresoras disponibles, el método espera
     * hasta que una impresora se libere. Una vez que una impresora se encuentra disponible,
     * el método la marca como ocupada y devuelve el número de la impresora (1-3).
     * 
     * @return el número de la impresora solicitada (1-3).
     * @throws InterruptedException si el hilo actual se interrumpe mientras espera.
     */
    public synchronized int pedirImpresora() throws InterruptedException {
        // Esperar hasta que haya al menos una impresora disponible
        while (impresorasDisponibles == 0) {
            wait(); 
        }

        int n = -1;
        // Buscar la primera impresora disponible
        for (int i = 0; i < libre.length; i++) {
            if (libre[i]) {
                libre[i] = false; // Marcar la impresora como ocupada
                impresorasDisponibles--; // Reducir el contador de impresoras disponibles
                n = i + 1; // Devolver el número de la impresora (1-3)
                break;
            }
        }
        return n; 
    }

    /**
     * Libera una impresora que se había solicitado anteriormente. Marca la impresora como
     * disponible y notifica a todos los hilos que podrían estar esperando por una impresora.
     * 
     * @param n el número de la impresora que se desea liberar (1-3).
     */
    public synchronized void liberarImpresora(int n) {
        // Marcar la impresora como disponible
        libre[n - 1] = true; 
        impresorasDisponibles++; // Incrementar el contador de impresoras disponibles
        notifyAll(); // Notificar a todos los hilos que están esperando por una impresora
    }
}
