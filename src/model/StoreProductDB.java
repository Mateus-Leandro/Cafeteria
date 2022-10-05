package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreProductDB {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public boolean deleteProductStore(Integer idProduct, Integer idStore, Connection conn) {
		try {
			conn.setAutoCommit(false);

			// Executado quando realizado exclusão de loja.
			if (idProduct == null) {
				ps = conn.prepareStatement("DELETE FROM db_cafeteria.tb_store_product WHERE idStore = ?");
				ps.setInt(1, idStore);
			} else {
				// Executado quando realizado exclusão do item.
				if (idStore == null) {
					ps = conn.prepareStatement("DELETE FROM db_cafeteria.tb_store_product WHERE idProduct = ?");
					ps.setInt(1, idProduct);
				} else {
					// Executado quandor realizado alteração de estoque na loja.
					ps = conn.prepareStatement(
							"DELETE FROM db_cafeteria.tb_store_product WHERE idProduct = ? AND idStore = ?");
					ps.setInt(1, idProduct);
					ps.setInt(2, idStore);
				}
			}

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
		}
	}
}
