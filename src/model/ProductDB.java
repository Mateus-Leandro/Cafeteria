package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Product_registration;
import controller.Store;

public class ProductDB {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private PreparedStatement ps2;
	private ResultSet rs2;
	private DB db = new DB();
	private Product_registration product;
	private StoreProductDB storeProductDb = new StoreProductDB();

	public ArrayList<Product_registration> searchAllProducts(ArrayList<Product_registration> productsSearched, Integer idStore) {
		productsSearched = new ArrayList<Product_registration>();
		conn = db.getConnection();
		boolean nullStore = idStore == null;

		try {
			ps = conn.prepareStatement("SELECT * FROM tb_product");
			rs = ps.executeQuery();

			while (rs.next()) {
				product = new Product_registration();
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

	public ArrayList<Product_registration> searchProduct(Store selectedStore, ArrayList<Product_registration> products, String wantedProduct,
			String typeSearch) {
		products = new ArrayList<Product_registration>();
		conn = db.getConnection();
		String query = null;

		try {
			switch (typeSearch) {
				case "Cod":
					query = "SELECT * FROM tb_product WHERE id like ?";
					break;
				case "Nome":
					query = "SELECT * FROM tb_product WHERE name like ?";
					break;
				case "Cod. Barras":
					query = "SELECT * FROM tb_product WHERE barCode like ?";
					break;
			}
			ps = conn.prepareStatement(query);
			ps.setString(1, wantedProduct);
			rs = ps.executeQuery();
			while (rs.next()) {
				product = new Product_registration();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setInventory(searchInventory(selectedStore, product));
				product.setBarCode(rs.getString("barCode"));
				product.setManufacturerDate(rs.getDate("manufacturerDate"));
				product.setValidationDate(rs.getDate("validationDate"));
				products.add(product);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.closeStatement(ps);
			db.closeResultSet(rs);
			db.closeConnection(conn);

		}
		return products;
	}

	public Product_registration includeProduct(Product_registration productIncluded, Integer idSelectedStore) {
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

	public boolean updateProduct(Product_registration productUpdated, Integer idStore) {
		conn = db.getConnection();
		boolean saved = false;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE db_cafeteria.tb_product "
					+ "SET name = ?, barCode = ?, price = ?, manufacturerDate = ?, validationDate = ?"
					+ "WHERE id = ?");
			ps.setString(1, productUpdated.getName());
			ps.setString(2, productUpdated.getBarCode());
			ps.setDouble(3, productUpdated.getPrice());
			ps.setDate(4, new java.sql.Date(productUpdated.getManufacturerDate().getTime()));
			ps.setDate(5, new java.sql.Date(productUpdated.getValidationDate().getTime()));
			ps.setInt(6, productUpdated.getId());
			ps.execute();

			if (storeProductDb.deleteProductStore(productUpdated.getId(), idStore, conn)) {
				saved = recordStock(productUpdated, idStore);
			}

			return saved;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}

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

	public boolean recordStock(Product_registration product, int idStore) {
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

	public Integer searchInventory(Store selectedStore, Product_registration product) {

		Integer inventory = 0;
		// Busca estoque do produto.
		if (selectedStore != null) {
			try {
				ps2 = conn
						.prepareStatement("SELECT inventory FROM tb_store_product WHERE idStore = ? AND idProduct = ?");
				ps2.setInt(1, selectedStore.getId());
				ps2.setInt(2, product.getId());
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					inventory = rs2.getInt("inventory");
				}

			} catch (Exception e) {
				e.printStackTrace();
				return inventory;
			}
		}
		return inventory;
	}
}
