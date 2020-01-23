package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

//OPERA��ES ESTATICAS PRA INSTANCIAR OS DAOS
public class DaoFactory {
//INSTANCIAR A IMPLEMENTA��O
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}
