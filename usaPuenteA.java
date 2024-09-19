//Adrián María Gordillo Fernández
//45381691T


/**
 * La clase <code>UsaPuenteA</code> es la clase principal que prueba la implementación del monitor <code>PuenteA</code>.
 * Simula el tráfico de coches desde el norte y el sur en el puente.
 */
public class usaPuenteA {
    /**
     * El punto de entrada principal para probar <code>PuenteA</code>.
     *
     * @param args los argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        PuenteA puenteA = new PuenteA();

        // Runnable para probar PuenteA con coches viniendo del norte
        Runnable pruebaPuenteA = () -> {
            try {
                for (int i = 0; i < 15; i++) {
                    puenteA.solicitarPaso(true); // Coche viniendo del norte
                    System.out.println("Coche del norte cruzando PuenteA");
                    Thread.sleep(500);
                    puenteA.liberarPuente();
                    System.out.println("Coche del norte ha cruzado PuenteA");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Runnable para probar PuenteA con coches viniendo del sur
        Runnable pruebaPuenteASur = () -> {
            try {
                for (int i = 0; i < 15; i++) {
                    puenteA.solicitarPaso(false); // Coche viniendo del sur
                    System.out.println("Coche del sur cruzando PuenteA");
                    Thread.sleep(500);
                    puenteA.liberarPuente();
                    System.out.println("Coche del sur ha cruzado PuenteA");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Crear y lanzar hilos para probar PuenteA
        Thread hiloNorte = new Thread(pruebaPuenteA);
        Thread hiloSur = new Thread(pruebaPuenteASur);

        hiloNorte.start();
        hiloSur.start();

        try {
            hiloNorte.join();
            hiloSur.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
