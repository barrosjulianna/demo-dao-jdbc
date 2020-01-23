package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

//OPERAÇÕES ESTATICAS PRA INSTANCIAR OS DAOS
public class DaoFactory {
//INSTANCIAR A IMPLEMENTAÇÃO
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}
