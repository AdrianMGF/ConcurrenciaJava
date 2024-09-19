import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.util.*;

public class sBonoLoto extends UnicastRemoteObject implements  iBonoLoto{
	Random r=new Random();
	private int[] premio=new int[5];
	public  void resetServidor() throws RemoteException{
		for(int i=0;i<5;i++){
			premio[i]=r.nextInt(48)+1;
		}
		System.out.println("Numero premiado:");
		for(int i=0;i<5;i++){
		System.out.print(premio[i]+" ");
		}
		System.out.println();
	}
	
	public boolean compApuesta(int[] apuesta)throws RemoteException{
		int cont=0;
		boolean igual=true;
		while(cont<5 && igual){
		igual=(apuesta[cont]==premio[cont]);
		cont++;
		}
		return igual;
	}
	
	public sBonoLoto()throws RemoteException{resetServidor();}
	
	public static void main(String[] args)throws Exception{
		int i=0;
		Scanner sc=new Scanner(System.in);
		iBonoLoto rem=new sBonoLoto();
		Naming.bind("BonoLoto",rem);
		System.out.println("Servidor preparado");
		
	}
	
}