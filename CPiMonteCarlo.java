import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

public class CPiMonteCarlo{
	public static void main(String []arg)throws Exception{
		Scanner sc=new Scanner(System.in);
		
		int op=10;
		int puntos;
		iPiMonteCarlo RefORemoto = (iPiMonteCarlo)Naming.lookup("//localhost/Servidor");
		while(op!=0){
		System.out.print("Menu\n1.Incluir mas puntos a la aproximacion\n2.ResetServer\n0.Salir\n");
		op=sc.nextInt();
		switch(op){
		case 1:
			System.out.println("Numero de puntos a incluir en la aproximacion");
			puntos=sc.nextInt();
			RefORemoto.masPuntos(puntos);
			
			break;
		case 2:
			RefORemoto.reset();
			System.out.println("Servidor puesto a 0");
			 
			break;
		}
		}
	}
}