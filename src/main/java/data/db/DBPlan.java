package data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.Plan;

public class DBPlan extends DBConnector {
	private static DBPlan instance;

	private DBPlan() {
	}

	public static DBPlan getInstance() {
		if (instance == null) {
			instance = new DBPlan();
		}
		return instance;
	}

	public List<Plan> getAllPlans() {
		List<Plan> list = new ArrayList<>();
		String sql = "Select * from plan ORDER BY plannumber";
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				list.add(new Plan.Builder().setId(resultSet.getString("id"))
						.withDescription(resultSet.getString("description"))
						.withPlanNumber(resultSet.getString("plannumber")).withTitle(resultSet.getString("title"))
						.build());
			}
			resultSet.close();
			
		} catch (Exception e) {
			System.out.println("Error in DB");
		}
		return list;
	}
	
	public Plan insertPlan(Plan plan){
		String sql = "INSERT INTO plan (`id`,`title`,`description`,`plannumber`) VALUES(?,?,?,?);";
		try(Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,plan.getId());
			pstmt.setString(2,plan.getTitle());
			pstmt.setString(3,plan.getDescription());
			pstmt.setString(4,plan.getPlanNumber());
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("error in insert" + e);
		}
		return plan;
	}

	
	public Plan updatePlan(Plan plan){
		String sql = "UPDATE plan SET `title`=? , `description`=? , `plannumber`=? WHERE id=?";
		try(Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,plan.getTitle());
			pstmt.setString(2,plan.getDescription());
			pstmt.setString(3,plan.getPlanNumber());
			pstmt.setString(4,plan.getId());
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("error in update");
		}	
		return plan;
	}
	
	public boolean deletePlan(Plan plan){
		String sql = "DELETE FROM plan WHERE id=?";
		try(Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,plan.getId());
			pstmt.executeUpdate();
			return true;
		}catch(Exception e){
			System.out.println("error in delete" + e.getMessage());
		}	
		return false;
	}
}
