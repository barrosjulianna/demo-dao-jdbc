package model.dao;

import java.util.List;

import model_entities.Department;
import model_entities.Seller;


public interface SellerDao {
	
	void insert (Seller obj);
	void update (Seller obj);
	void deleteById (Integer id);
	Seller findById (Integer id); //PEGAR O ID E CONSULTAR NO BD O OBJ ID
	List<Seller> findAll(); //retornar tudo
	List<Seller> findByDepartment(Department department);

}
