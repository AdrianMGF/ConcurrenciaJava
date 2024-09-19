//Adrián María Gordillo Fernández
//45381691T

/**
 * La clase <code>UsaPuenteB</code> es la clase principal que prueba la implementación del monitor <code>PuenteB</code>.
 * Simula el tráfico de coches desde el norte y el sur en el puente.
 */
public class usaPuenteB {
    /**
     * El punto de entrada principal para probar <code>PuenteB</code>.
     *
     * @param args los argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        PuenteB puenteB = new PuenteB();

        // Runnable para probar PuenteB con coches viniendo del sur
        Runnable pruebaPuenteB = () -> {
            try {
                for (int i = 0; i < 15; i++) {
                    puenteB.solicitarPaso(false); // Coche viniendo del sur
                    System.out.println("Coche del sur cruzando PuenteB");
                    Thread.sleep(500);
                    puenteB.liberarPuente();
                    System.out.println("Coche del sur ha cruzado PuenteB");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Runnable para probar PuenteB con coches viniendo del norte
        Runnable pruebaPuenteBNorte = () -> {
            try {
                for (int i = 0; i < 15; i++) {
                    puenteB.solicitarPaso(true); // Coche viniendo del norte
                    System.out.println("Coche del norte cruzando PuenteB");
                    Thread.sleep(500);
                    puenteB.liberarPuente();
                    System.out.println("Coche del norte ha cruzado PuenteB");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Crear y lanzar hilos para probar PuenteB
        Thread hiloSur = new Thread(pruebaPuenteB);
        Thread hiloNorte = new Thread(pruebaPuenteBNorte);

        hiloSur.start();
        hiloNorte.start();

        try {
            hiloSur.join();
            hiloNorte.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
