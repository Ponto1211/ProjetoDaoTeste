package app;

import modelo.dao.FabricaDao;
import modelo.dao.VendedorDao;
import modelo.entidade.Vendedor;

public class Programa {

	public static void main(String[] args) {

		VendedorDao vendDao=FabricaDao.criarVendedorDao();
		Vendedor vend=vendDao.acharId(3);
		
		System.out.println(vend);
	}

}
