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
						.withPlanNumber(resultSet.getString("plannumber"))
						.withTitle(resultSet.getString("title"))
						.withAdress(resultSet.getString("adress"))
						.withInstantAction(resultSet.getString("instantaction"))
						.withWatherTransport(resultSet.getString("wathertransport"))
						.withImportantContact(resultSet.getString("importantcontacts"))
						.withMap(resultSet.getString("maplink"))
						.build());
			}
			resultSet.close();
			
		} catch (Exception e) {
			System.out.println("Error in DB");
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Plan> getPlansWithKeyword(String keyword) {
		List<Plan> list = new ArrayList<>();
		String sqlKeyword = "%" + keyword + "%";
		String sql = "Select * from plan WHERE id LIKE ? OR description LIKE ? OR plannumber LIKE ? "
				+ "OR title LIKE ? OR adress LIKE ? ORDER BY plannumber";
		try(Connection conn = this.connect();
		        PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1,sqlKeyword);
				pstmt.setString(2,sqlKeyword);
				pstmt.setString(3,sqlKeyword);
				pstmt.setString(4,sqlKeyword);
				pstmt.setString(5,sqlKeyword);
				
				ResultSet resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				list.add(new Plan.Builder().setId(resultSet.getString("id"))
						.withDescription(resultSet.getString("description"))
						.withPlanNumber(resultSet.getString("plannumber"))
						.withTitle(resultSet.getString("title"))
						.withAdress(resultSet.getString("adress"))
						.withInstantAction(resultSet.getString("instantaction"))
						.withWatherTransport(resultSet.getString("wathertransport"))
						.withImportantContact(resultSet.getString("importantcontacts"))
						.withMap(resultSet.getString("maplink"))
						.build());
			}
			resultSet.close();
			
		} catch (Exception e) {
			System.out.println("Error in DB");
			e.printStackTrace();
		}
		return list;
	}
	
	public Plan insertPlan(Plan plan){
		String sql = "INSERT INTO plan (`id`,`title`,`description`,`plannumber`,`adress`,`instantaction`,`wathertransport`,`importantcontacts`,`maplink`) VALUES(?,?,?,?,?,?,?,?,?);";
		try(Connection conn = this.connect();
	        PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,plan.getId());
			pstmt.setString(2,plan.getTitle());
			pstmt.setString(3,plan.getDescription());
			pstmt.setString(4,plan.getPlanNumber());
			pstmt.setString(5,plan.getAdress());
			pstmt.setString(6,plan.getInstantAction());
			pstmt.setString(7,plan.getWatherTransport());
			pstmt.setString(8,plan.getImportantContacts());
			pstmt.setString(9,plan.getMap());
			
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("error in insert" + e);
			e.printStackTrace();
		}
		return plan;
	}

	
	public Plan updatePlan(Plan plan){
		String sql = "UPDATE plan SET `title`=? ,`description`=?, `plannumber`=? , `adress`=?, `instantaction`=?, `wathertransport`=?, `importantcontacts`=? ,`maplink`=?  WHERE id=?";
		try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,plan.getTitle());
			pstmt.setString(2,plan.getDescription());
			pstmt.setString(3,plan.getPlanNumber());
			pstmt.setString(4,plan.getAdress());
			pstmt.setString(5,plan.getInstantAction());
			pstmt.setString(6,plan.getWatherTransport());
			pstmt.setString(7,plan.getImportantContacts());
			pstmt.setString(8,plan.getMap());
			pstmt.setString(9,plan.getId());
			
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("error in update");
			e.printStackTrace();
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
