package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Store;

public class StoreDB {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private DB db = new DB();
	private Store store;

	public ArrayList<Store> searchStores(ArrayList<Store> storesSearched) {
		storesSearched = new ArrayList<Store>();
		conn = db.getConnection();

		try {
			ps = conn.prepareStatement("SELECT * FROM tb_store");
			rs = ps.executeQuery();

			while (rs.next()) {
				store = new Store();
				store.setId(rs.getInt("id"));
				store.setName(rs.getString("name"));
				store.setCnpj(rs.getString("cnpj"));
				store.setStreet(rs.getString("street"));
				store.setStreetNumber(rs.getString("number"));
				store.setDistrict(rs.getString("district"));
				store.setCity(rs.getString("city"));
				store.setCreationDate(rs.getDate("creationDate"));
				storesSearched.add(store);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
		return storesSearched;
	}

	public Store includeStore(Store storeIncluded) {
		conn = db.getConnection();

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(
					"INSERT INTO db_cafeteria.tb_store "
					+ "(name, cnpj, street, number, district, city, creationDate) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, storeIncluded.getName());
			ps.setString(2, storeIncluded.getCnpj());
			ps.setString(3, storeIncluded.getStreet());
			ps.setString(4, storeIncluded.getStreetNumber());
			ps.setString(5, storeIncluded.getDistrict());
			ps.setString(6, storeIncluded.getCity());
			ps.setDate(7, new java.sql.Date(System.currentTimeMillis()));

			ps.execute();
			conn.commit();
			rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				storeIncluded.setId(rs.getInt(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
		return storeIncluded;
	}
	
	public boolean updateStore(Store storeUpdated) {
		conn = db.getConnection();
		
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(
					"UPDATE db_cafeteria.tb_store "
					+ "SET name = ?, cnpj = ?, street = ?, number = ?, district = ?, city = ? "
					+ "WHERE id = ?");
			ps.setString(1, storeUpdated.getName());
			ps.setString(2, storeUpdated.getCnpj());
			ps.setString(3, storeUpdated.getStreet());
			ps.setString(4, storeUpdated.getStreetNumber());
			ps.setString(5, storeUpdated.getDistrict());
			ps.setString(6, storeUpdated.getCity());
			ps.setInt(7, storeUpdated.getId());
			
			ps.execute();
			conn.commit();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}
	
	public boolean deleteStore(int numberStore) {
		conn = db.getConnection();
		
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("DELETE FROM db_cafeteria.tb_store WHERE id = ?");
			ps.setInt(1, numberStore);
			
			ps.execute();
			conn.commit();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}

}
