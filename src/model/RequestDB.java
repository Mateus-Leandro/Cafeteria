package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Payment;
import controller.Profile;
import controller.Request;
import controller.User;

public class RequestDB {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private DB db = new DB();
	private Request request;
	private Profile profile;
	private User client;
	private Payment payment;

	public ArrayList<Request> searchRequest(ArrayList<Request> requestsSearched) {
		requestsSearched = new ArrayList<Request>();
		conn = db.getConnection();

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
		}
	}
}