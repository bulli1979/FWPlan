package data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.UserElement;

public class DBUserElement extends DBConnector {
	private static DBUserElement instance;

	private DBUserElement() {
	}

	public static DBUserElement getInstance() {
		if (instance == null) {
			instance = new DBUserElement();
		}
		return instance;
	}

	public List<UserElement> getAllElementsForPlan(String planId) {
		List<UserElement> list = new ArrayList<>();
		String sql = "Select * from userelement where plan=?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, planId);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				list.add(new UserElement.Builder().setId(resultSet.getString("id"))
						.withLeft(resultSet.getDouble("left"))
						.withWidth(resultSet.getDouble("width"))
						.withTop(resultSet.getDouble("top"))
						.withHeight(resultSet.getDouble("height"))
						.withText(resultSet.getString("text"))
						.withType(resultSet.getInt("typ"))
						.forPlan(resultSet.getString("plan"))
						.withImage(resultSet.getString("image"))
						.build());
			}
			resultSet.close();

		} catch (Exception e) {
			System.out.println("Error in DB");
			e.printStackTrace();
		}
		return list;
	}

	public UserElement insertElement(UserElement userElement) {
		String sql = "INSERT INTO userelement (`id`,`plan`,`text`,`left`,`width`,`top`,`height`,`typ`,`image`) VALUES(?,?,?,?,?,?,?,?,?);";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userElement.getId());
			pstmt.setString(2, userElement.getPlanId());
			pstmt.setString(3, userElement.getText());
			pstmt.setDouble(4, userElement.getLeft());
			pstmt.setDouble(5, userElement.getWidth());
			pstmt.setDouble(6, userElement.getTop());
			pstmt.setDouble(7, userElement.getHeight());
			pstmt.setInt(8, userElement.getType());
			pstmt.setString(9, userElement.getImage());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("error in insert" + e);
			e.printStackTrace();
		}
		return userElement;
	}

	public UserElement updateElement(UserElement userElement) {
		String sql = "UPDATE userelement SET `id`=? ,`text`=?,`left`=?,`width`=?,`top`=?,`height`=?,`typ`=?,`image`=?  WHERE id=?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userElement.getId());
			pstmt.setString(2, userElement.getText());
			pstmt.setDouble(3, userElement.getLeft());
			pstmt.setDouble(4, userElement.getWidth());
			pstmt.setDouble(5, userElement.getTop());
			pstmt.setDouble(6, userElement.getHeight());
			pstmt.setInt(7,userElement.getType());
			pstmt.setString(8, userElement.getImage());
			pstmt.setString(9, userElement.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("error in update");
			e.printStackTrace();
		}
		return userElement;
	}

	public boolean deleteElement(UserElement userelement) {
		String sql = "DELETE FROM userelement WHERE id=?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userelement.getId());
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println("error in delete" + e.getMessage());
		}
		return false;
	}
}
