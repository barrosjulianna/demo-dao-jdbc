package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model_entities.Department;
import model_entities.Seller;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao= DaoFactory.createSellerDao();
		System.out.println("Digite o ID desejado");
		int id= sc.nextInt();
		System.out.println("===TESTE NUMERO 1 FindById ===");
		Seller seller= sellerDao.findById(id);
		System.out.println(seller);
		
		System.out.println("\n===TESTE NUMERO 2 FindByDepartment ===");
		Department department = new Department(2,null);
		List<Seller>list = sellerDao.findByDepartment(department);
		
		for(Seller obj: list) {
			System.out.println(obj);
		}
			
		System.out.println("\n===TESTE NUMERO 3 FindAll ===");
		list = sellerDao.findAll();
			
		for(Seller objj: list) {
		System.out.println(objj);
		
		}
				
		

	}

}
