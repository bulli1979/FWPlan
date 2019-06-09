package biz.wgc.fwplan.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import biz.wgc.fwplan.data.Settings;


public class DBSettings extends DBConnector{
	
	private static DBSettings instance;

	private DBSettings() {}
	
	public static DBSettings getInstance() {
		if (instance == null) {
			instance = new DBSettings();
		}
		return instance;
	}
	
	public Optional<Settings> getSettings() {
		String sql = "Select * from settings where id=1";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			Settings settings = null;
			ResultSet resultSet = pstmt.executeQuery();
			if(resultSet.next()) {
				settings = new Settings();
				settings.setId(resultSet.getInt("id"));
				settings.setVersion(resultSet.getDouble("version"));
			}
			return Optional.of(settings);
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return Optional.ofNullable(null);
	}
	
	public void saveSetting(Settings settings) {
		String sql = "INSERT INTO settings  (`id`,`version`) VALUES(?,?);";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, 1);
			pstmt.setDouble(2, settings.getVersion());
			pstmt.execute();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateSetting(Settings settings) {
		String sql = "UPDATE settings set version =? where id=1";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDouble(1, settings.getVersion());
			pstmt.executeQuery();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
