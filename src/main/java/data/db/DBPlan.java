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
		try {
			String query = "Select * from plan ORDER BY plannumber";
			Connection connection = this.connect();
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				list.add(new Plan.Builder().setId(resultSet.getString("id"))
						.withDescription(resultSet.getString("description"))
						.withPlanNumber(resultSet.getString("plannumber")).withTitle(resultSet.getString("title"))
						.build());
			}
			resultSet.close();
			ps.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error in DB");
		}
		return list;
	}
	
	public Plan insertPlan(Plan plan){
		try{
			Connection connection = this.connect();
			String query = "INSERT INTO plan (`id`,`title`,`description`,`plannumber`,) VALUES(?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,plan.getId());
			ps.setString(2,plan.getTitle());
			ps.setString(3,plan.getDescription());
			ps.setString(4,plan.getPlanNumber());
			ps.executeQuery();
			ps.close();
			connection.close();
		}catch(Exception e){
			System.out.println("error in insert");
		}	
		return plan;
	}

	
	public Plan updatePlan(Plan plan){
		try{
			Connection connection = this.connect();
			String query = "UPDATE plan SET `title`=? , `description`=? , `plannumber`=? WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,plan.getTitle());
			ps.setString(2,plan.getDescription());
			ps.setString(3,plan.getPlanNumber());
			ps.setString(4,plan.getId());
			ps.executeQuery();
			ps.close();
			connection.close();
		}catch(Exception e){
			System.out.println("error in update");
		}	
		return plan;
	}
	
	public boolean deletePlan(Plan plan){
		try{
			Connection connection = this.connect();
			String query = "delete plan WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,plan.getId());
			ps.executeQuery();
			ps.close();
			connection.close();
			return true;
		}catch(Exception e){
			System.out.println("error in delete");
		}	
		return false;
	}
}
