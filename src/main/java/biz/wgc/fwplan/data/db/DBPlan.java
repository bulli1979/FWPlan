package biz.wgc.fwplan.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import biz.wgc.fwplan.data.Plan;

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
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				list.add(new Plan.Builder().setId(resultSet.getString("id"))
						.withDescription(resultSet.getString("description"))
						.withPlanNumber(resultSet.getString("plannumber")).withTitle(resultSet.getString("title"))
						.withAdress(resultSet.getString("adress"))
						.withInstantAction(resultSet.getString("instantaction"))
						.withWatherTransport1(resultSet.getString("wathertransport"))
						.withWatherTransport2(resultSet.getString("wathertransport2"))
						.withWatherTransport3(resultSet.getString("wathertransport3"))
						.withWatherTransport4(resultSet.getString("wathertransport4"))
						.withImportantContact(resultSet.getString("importantcontacts"))
						.withMap(resultSet.getString("maplink")).build());
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
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, sqlKeyword);
			pstmt.setString(2, sqlKeyword);
			pstmt.setString(3, sqlKeyword);
			pstmt.setString(4, sqlKeyword);
			pstmt.setString(5, sqlKeyword);

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				list.add(new Plan.Builder().setId(resultSet.getString("id"))
						.withDescription(resultSet.getString("description"))
						.withPlanNumber(resultSet.getString("plannumber")).withTitle(resultSet.getString("title"))
						.withAdress(resultSet.getString("adress"))
						.withInstantAction(resultSet.getString("instantaction"))
						.withWatherTransport1(resultSet.getString("wathertransport"))
						.withWatherTransport2(resultSet.getString("wathertransport"))
						.withWatherTransport3(resultSet.getString("wathertransport"))
						.withWatherTransport4(resultSet.getString("wathertransport"))
						.withImportantContact(resultSet.getString("importantcontacts"))
						.withMap(resultSet.getString("maplink")).build());
			}
			resultSet.close();

		} catch (Exception e) {
			System.out.println("Error in DB");
			e.printStackTrace();
		}
		return list;
	}

	public Plan insertPlan(Plan plan) {
		String sql = "INSERT INTO plan (`id`,`title`,`description`,`plannumber`,`adress`,`instantaction`,`wathertransport`,`wathertransport2`,`wathertransport3`,`wathertransport4`,`importantcontacts`,`maplink`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, plan.getId());
			pstmt.setString(2, plan.getTitle());
			pstmt.setString(3, plan.getDescription());
			pstmt.setString(4, plan.getPlanNumber());
			pstmt.setString(5, plan.getAdress());
			pstmt.setString(6, plan.getInstantAction());
			pstmt.setString(7, plan.getWatherTransport1());
			pstmt.setString(8, plan.getWatherTransport2());
			pstmt.setString(9, plan.getWatherTransport3());
			pstmt.setString(10, plan.getWatherTransport4());
			pstmt.setString(11, plan.getImportantContacts());
			pstmt.setString(12, plan.getMap());

			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("error in insert" + e);
			e.printStackTrace();
		}
		return plan;
	}

	public Plan updatePlan(Plan plan) {
		String sql = "UPDATE plan SET `title`=? ,`description`=?, `plannumber`=?, `adress`=?, `instantaction`=?, `wathertransport`=?,`wathertransport2`=?,`wathertransport3`=?,`wathertransport4`=?, `importantcontacts`=? ,`maplink`=?  WHERE id=?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, plan.getTitle());
			pstmt.setString(2, plan.getDescription());
			pstmt.setString(3, plan.getPlanNumber());
			pstmt.setString(4, plan.getAdress());
			pstmt.setString(5, plan.getInstantAction());
			pstmt.setString(6, plan.getWatherTransport1());
			pstmt.setString(7, plan.getWatherTransport2());
			pstmt.setString(8, plan.getWatherTransport3());
			pstmt.setString(9, plan.getWatherTransport4());
			pstmt.setString(10, plan.getImportantContacts());
			pstmt.setString(11, plan.getMap());
			pstmt.setString(12, plan.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("error in update");
			e.printStackTrace();
		}
		return plan;
	}

	public boolean deletePlan(Plan plan) {
		String sql = "DELETE FROM plan WHERE id=?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, plan.getId());
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println("error in delete" + e.getMessage());
		}
		return false;
	}
}
