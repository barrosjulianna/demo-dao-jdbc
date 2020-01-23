package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model_entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao= DaoFactory.createSellerDao();
		
		System.out.println("===TESTE NUMERO 1 FindById ===");
		Seller seller= sellerDao.findById(3);
		System.out.println(seller);
		
		
		
		

	}

}
