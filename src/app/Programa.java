package app;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import modelo.dao.FabricaDao;
import modelo.dao.VendedorDao;
import modelo.entidade.Departamento;
import modelo.entidade.Vendedor;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		VendedorDao vendDao = FabricaDao.criarVendedorDao();

		System.out.println("=== Teste 1: findById ===");
		Vendedor vend = vendDao.acharId(3);
		System.out.println(vend);
		System.out.println();
		System.out.println("=== Teste 2: findByDep ===");
		Departamento departamento = new Departamento(2, null);
		List<Vendedor> listaVend = vendDao.acharPorDepartamento(departamento);
		for (Vendedor v : listaVend) {
			System.out.println(v);
		}
		System.out.println();
		System.out.println("=== Teste 3: findAll ===");
		listaVend = vendDao.acharTudo();
		for (Vendedor vx : listaVend) {
			System.out.println(vx);
		}
		System.out.println();
		System.out.println("=== Teste 4: Insert ===");
		Vendedor novoVendedor = new Vendedor(null, "Greg", "greg@gmail.com", new Date(), 4000.0, departamento);
		vendDao.inserir(novoVendedor);
		System.out.println("Inserido! Novo id: " + novoVendedor.getId());
		System.out.println();
		System.out.println("=== Teste 5: Update ===");
		Vendedor velhoVendedor = vendDao.acharId(1);
		velhoVendedor.setNome("Martha Waine");
		vendDao.atualizar(velhoVendedor);
		System.out.println("Atualização completa!");
		System.out.println();
		System.out.println("=== Teste 6: Delete ===");
		System.out.print("Insira o ID a ser deletado: ");
		int id = sc.nextInt();
		vendDao.deletarId(id);
	}

}
