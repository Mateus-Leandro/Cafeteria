package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Payment;
import controller.Product_request;
import controller.Profile;
import controller.Request;
import controller.User;

public class RequestDB {
	private Connection conn;
	private Connection conn2;
	private PreparedStatement ps;
	private PreparedStatement ps2;
	private ResultSet rs;
	private ResultSet rs2;
	private DB db = new DB();
	private Request request;
	private Profile profile;
	private User client;
	private Payment payment;
	private Product_request productRequest;

	public ArrayList<Request> searchRequest(ArrayList<Request> requestsSearched) {
		requestsSearched = new ArrayList<Request>();
		conn = db.getConnection();
		conn2 = db.getConnection();

		try {
			ps = conn.prepareStatement(
					"select tb_request.id AS id_request, tb_user.id AS id_user, tb_profile.id AS id_profile,"
							+ " tb_profile.description AS description_profile, tb_user.name as client_name,"
							+ " tb_user.zipCode, tb_user.telephone, tb_user.cpf, tb_payment.id AS payment_id,"
							+ " tb_payment.description as payment_description, tb_payment.card, amount, finished,creationDate"
							+ " from tb_request left join tb_user on tb_request.idUser = tb_user.id"
							+ " left join tb_profile on tb_user.profileId = tb_profile.id"
							+ " left join tb_payment on tb_request.idPayment = tb_payment.id;");
			rs = ps.executeQuery();

			while (rs.next()) {
				profile = new Profile(rs.getInt("id_profile"), rs.getString("description_profile"));

				client = new User(rs.getInt("id_user"), profile, rs.getString("client_name"), rs.getString("zipCode"),
						rs.getString("telephone"), rs.getString("cpf"));

				payment = new Payment(rs.getInt("payment_id"), rs.getString("payment_description"),
						rs.getBoolean("card"));
				request = new Request();
				request.setId(rs.getInt("id_request"));
				request.setUser(client);
				request.setPayment(payment);
				request.setAmount(rs.getDouble("amount"));
				request.setFinished(rs.getBoolean("finished"));
				request.setCreationDate(rs.getDate("creationDate"));
				request.setProducts(getOrderProducts(request.getId(), request.getProducts()));
				requestsSearched.add(request);
			}

			return requestsSearched;

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

			// Conexões para busca dos produtos do pedido
			DB.closeStatement(ps2);
			DB.closeResultSet(rs2);
			DB.closeConnection(conn2);
		}

	}

	public ArrayList<Product_request> getOrderProducts(int requestId, ArrayList<Product_request> products) {
		products = new ArrayList<Product_request>();
		conn2 = db.getConnection();
		try {
			ps2 = conn2.prepareStatement(
					"SELECT idProduct, tb_product.name, tb_product.barCode, theAmount, unitPrice, totalPrice "
							+ " FROM tb_product_request"
							+ " INNER JOIN tb_product ON tb_product_request.idProduct = tb_product.id"
							+ " WHERE idRequest = ?");
			ps2.setInt(1, requestId);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				productRequest = new Product_request();
				productRequest.setId(rs2.getInt("idProduct"));
				productRequest.setName(rs2.getString("name"));
				productRequest.setBarCode(rs2.getString("barCode"));
				productRequest.setTheAmount(rs2.getDouble("theAmount"));
				productRequest.setUnitPrice(rs2.getDouble("unitPrice"));
				productRequest.setTotalPrice(rs2.getDouble("totalPrice"));
				products.add(productRequest);
			}
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}