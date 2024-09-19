

//Adrián María Gordillo Fernández
//45381691T




/**
 * La clase warStations simula un escenario donde cuatro estaciones de combate intentan
 * adquirir y utilizar sistemas de defensa compartidos.
 * Cada estación es representada por un hilo, que intentará adquirir, usar y luego liberar
 * un sistema de defensa proporcionado por la clase ciwsControl.
 */
public class warStations {
    
    /**
     * El método main es el punto de entrada de la aplicación.
     * Se crean cuatro estaciones de combate, cada una representada por un hilo.
     * Cada estación intentará adquirir un sistema de defensa, usarlo por un tiempo aleatorio,
     * y luego liberarlo.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        ciwsControl control = new ciwsControl();

        for (int i = 1; i <= 4; i++) {
            final int stationId = i;
            new Thread(() -> {
                try {
                    int sistema = control.adquirirSistema();

                    System.out.println("Estación " + stationId + " está usando el sistema de defensa " + (sistema + 1));
                    Thread.sleep((int) (Math.random() * 1000 + 500));  

                   
                    control.liberarSistema(sistema);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Estación-" + i).start();
        }
    }
}
