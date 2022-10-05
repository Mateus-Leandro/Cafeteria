package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Product;

public class ProductDB {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private PreparedStatement ps2;
	private ResultSet rs2;
	private DB db = new DB();
	private Product product;
	private StoreProductDB storeProductDb = new StoreProductDB();

	public ArrayList<Product> searchAllProducts(ArrayList<Product> productsSearched, Integer idStore) {
		productsSearched = new ArrayList<Product>();
		conn = db.getConnection();
		boolean nullStore = idStore == null;

		try {
			ps = conn.prepareStatement("SELECT * FROM tb_product");
			rs = ps.executeQuery();

			while (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setBarCode(rs.getString("barCode"));
				product.setPrice(rs.getDouble("price"));
				product.setInventory(0);

				// Busca estoque do produto.
				if (!nullStore) {
					ps2 = conn.prepareStatement(
							"SELECT inventory FROM tb_store_product WHERE idStore = ? AND idProduct = ?");
					ps2.setInt(1, idStore);
					ps2.setInt(2, product.getId());
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						product.setInventory(rs2.getInt("inventory"));
					}
				}
				product.setManufacturerDate(rs.getDate("manufacturerDate"));
				product.setValidationDate(rs.getDate("validationDate"));

				productsSearched.add(product);
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
		return productsSearched;
	}

	public Product includeProduct(Product productIncluded, Integer idSelectedStore) {
		conn = db.getConnection();

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO db_cafeteria.tb_product "
					+ "(name, barCode, price, manufacturerDate, validationDate) " + "VALUES (?, ?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, productIncluded.getName());
			ps.setString(2, productIncluded.getBarCode());
			ps.setDouble(3, productIncluded.getPrice());
			ps.setDate(4, new java.sql.Date(productIncluded.getManufacturerDate().getTime()));
			ps.setDate(5, new java.sql.Date(productIncluded.getValidationDate().getTime()));

			ps.execute();
			conn.commit();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				productIncluded.setId(rs.getInt(1));
			}

			// Grava estoque
			if (idSelectedStore != null) {
				recordStock(productIncluded, idSelectedStore);
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
		return productIncluded;
	}

	public boolean updateProduct(Product productUpdated, Integer idStore) {
		conn = db.getConnection();
		boolean saved = false;
		if (storeProductDb.deleteProductStore(productUpdated.getId(), idStore, conn)) {
			saved = recordStock(productUpdated, idStore);
			db.closeStatement(ps);
		}
		db.closeConnection(conn);
		return saved;
	}

	public boolean deleteProduct(int idProduct) {
		conn = db.getConnection();

		if (storeProductDb.deleteProductStore(idProduct, null, conn)) {
			try {
				conn.setAutoCommit(false);
				ps = conn.prepareStatement("DELETE FROM db_cafeteria.tb_product WHERE id = ?");
				ps.setInt(1, idProduct);

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
				DB.closeConnection(conn);
			}
		}
		return false;

	}

	public boolean recordStock(Product product, int idStore) {
		try {
			ps = conn.prepareStatement(
					"INSERT INTO db_cafeteria.tb_store_product (idStore, idProduct, inventory) VALUES (?, ?, ?)");
			ps.setInt(1, idStore);
			ps.setInt(2, product.getId());
			ps.setInt(3, product.getInventory());
			ps.execute();
			conn.commit();
			return true;
		} catch (SQLException e) {
			return false;
		}

	}
}
