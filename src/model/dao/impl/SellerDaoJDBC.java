package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model_entities.Department;
import model_entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	//CONEXAO COM O BANCO
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn=conn;
	}
	
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement st=null;
		try {
			st=conn.prepareStatement(
					"INSERT INTO seller"
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+"VALUES "
					+"(?, ?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected>0) {
				ResultSet rs= st.getGeneratedKeys();
				if(rs.next()) {
					int id= rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("ERRO INESPERADON NENHUMA LINHA FOI INSERIDA");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
		

		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st=null;
		try {
			st=conn.prepareStatement(
					"UPDATE seller "
					+"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+"WHERE Id = ? "
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			st=conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
			st.setInt(1,id);
			rs=st.executeQuery();
			
			if(rs.next()) {
				//CASO TENHA PROXIMA LINHA RETORNA  A TABELA COM TODOS ESSES DADOS
				//VAI PRECISAR NAVEGAR NOS DADOS PRA INSTANCIAR OS OBJ
				
				//instantiateDepartament é colocaa toda a função de set
				Department dep = instantiateDepartament(rs);
				
				Seller obj =instantienteSeller(rs,dep);
				return obj;
				
				
				
			}
			//CASO NÃO TENHA NENHUM VENDEDOR COM ESSE ID RETORNA NULO
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
		
		
		
		
	}

	private Seller instantienteSeller(ResultSet rs, Department dep) throws SQLException {
		 	Seller obj =new Seller();
			obj.setId(rs.getInt("Id"));
			obj.setName(rs.getString("Name"));
			obj.setEmail(rs.getString("Email"));
			obj.setBirthDate(rs.getDate("BirthDate"));
			obj.setBaseSalary(rs.getDouble("BaseSalary"));
			obj.setDepartment(dep);
			return obj;
	}


	private Department instantiateDepartament(ResultSet rs) throws SQLException {
		Department dep=new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}


	@Override
	public List<Seller> findAll() {
		//BUSCAR TODOS OS VENDEDORES
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			st=conn.prepareStatement(
					 "SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"ORDER BY Name"
									);
			
			rs=st.executeQuery();
			
			List<Seller> list= new ArrayList<>();
			//CONTROLE PRA N REPETIR O DEP
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				//TESTAR SE O DEPARTAMENTO JA EXISTE
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep==null) {
					dep=instantiateDepartament(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj= instantienteSeller(rs, dep);
				list.add(obj);
				
				
			}
				return list;
				
				
		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
	}
	
	}


	@Override
	public List<Seller> findByDepartment(Department department) {
		//
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			st=conn.prepareStatement(
					 "SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE DepartmentId = ? "
					+"ORDER BY Name"
									);
			
			st.setInt(1,department.getId());
			rs=st.executeQuery();
			
			List<Seller> list= new ArrayList<>();
			//CONTROLE PRA N REPETIR O DEP
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				//TESTAR SE O DEPARTAMENTO JA EXISTE
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep==null) {
					dep=instantiateDepartament(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj= instantienteSeller(rs, dep);
				list.add(obj);
				
				
			}
				return list;
				
				
		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
	}
	
	}
	
	
}
