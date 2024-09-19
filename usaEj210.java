public class usaEj210{
    public static void main(String[] args) {
        // Crear una instancia del monitor con N1 y N2 recursos disponibles
        Ej210 monitor = new Ej210(2, 2);

        // Crear y ejecutar hilos que simulan procesos
        Thread proceso1 = new Thread(() -> {
            try {
                System.out.println("Proceso 1: Solicita 1 recurso r1 y 1 recurso r2");
                monitor.solicitarRecursos(true, true);
                System.out.println("Proceso 1: Recursos r1 y r2 asignados");

                // Simular trabajo con los recursos
                Thread.sleep(2000);

                System.out.println("Proceso 1: Libera 1 recurso r1 y 1 recurso r2");
                monitor.liberarRecursos(true, true);
                System.out.println("Proceso 1: Recursos r1 y r2 liberados");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

        Thread proceso2 = new Thread(() -> {
            try {
                System.out.println("Proceso 2: Solicita 1 recurso r1");
                monitor.solicitarRecursos(true, false);
                System.out.println("Proceso 2: Recurso r1 asignado");

                // Simular trabajo con el recurso
                Thread.sleep(3000);

                System.out.println("Proceso 2: Libera 1 recurso r1");
                monitor.liberarRecursos(true, false);
                System.out.println("Proceso 2: Recurso r1 liberado");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

        Thread proceso3 = new Thread(() -> {
            try {
                System.out.println("Proceso 3: Solicita 1 recurso r2");
                monitor.solicitarRecursos(false, true);
                System.out.println("Proceso 3: Recurso r2 asignado");

                // Simular trabajo con el recurso
                Thread.sleep(1000);

                System.out.println("Proceso 3: Libera 1 recurso r2");
                monitor.liberarRecursos(false, true);
                System.out.println("Proceso 3: Recurso r2 liberado");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

        Thread proceso4 = new Thread(() -> {
            try {
                System.out.println("Proceso 4: Solicita 1 recurso r1 y 1 recurso r2");
                monitor.solicitarRecursos(true, true);
                System.out.println("Proceso 4: Recursos r1 y r2 asignados");

                // Simular trabajo con los recursos
                Thread.sleep(1500);

                System.out.println("Proceso 4: Libera 1 recurso r1 y 1 recurso r2");
                monitor.liberarRecursos(true, true);
                System.out.println("Proceso 4: Recursos r1 y r2 liberados");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

        // Iniciar los hilos
        proceso1.start();
        proceso2.start();
        proceso3.start();
        proceso4.start();

        // Esperar a que todos los hilos terminen
        try {
            proceso1.join();
            proceso2.join();
            proceso3.join();
            proceso4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
