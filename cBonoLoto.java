import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;


public class cBonoLoto{
	public static void main(String[] args)throws Exception{
		Scanner sc=new Scanner(System.in);
		int[] bono=new int [5];
		boolean premiado;
		System.out.println("introduzca los numeros de la bonoLoto");
		for(int i=0;i<5;i++){
			bono[i]=sc.nextInt();
		}
		iBonoLoto ref=(iBonoLoto)Naming.lookup("//localhost/BonoLoto");
		premiado=ref.compApuesta(bono);
		if(premiado){
			System.out.println("Enhorabuena ");
		}
		else{
			System.out.println("Lo siento, mas suerte la proxima");
		}
	}
}