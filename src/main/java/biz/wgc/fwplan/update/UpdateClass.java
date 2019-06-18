package biz.wgc.fwplan.update;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import biz.wgc.fwplan.data.Settings;
import biz.wgc.fwplan.data.db.DBConnector;
import biz.wgc.fwplan.data.db.DBSettings;

public class UpdateClass {
	private static final double INIT = 0.9;
	private static Logger logger = Logger.getLogger(UpdateClass.class);
	private static final String UPDATE = "update-v-%s.txt";

	public void chkForUpdates() {
		logger.debug("checkForUpdates");
		try {
			Optional<Settings> optionalSettings = DBSettings.getInstance().getSettings();
			if (!optionalSettings.isPresent()) {
				runUpdatesForVersion(INIT);
				Settings settings = new Settings();
				settings.setVersion(INIT);
				DBSettings.getInstance().saveSetting(settings);
			}
		} catch (Exception e) {
			logger.error("Error in update" + logger);
		}

	}

	public void runUpdatesForVersion(double version) {
		String path = String.format(UPDATE, Double.toString(version).replace(".", "-"));
		Optional<List<String>> sqlList = getResourceFileAsList(path);
		if (sqlList.isPresent()) {
			DBConnector db = new DBConnector();
			for (String sql : sqlList.get()) {
				System.out.println(sql);
				db.runSQL(sql);
			}
		}
	}

	public Optional<List<String>> getResourceFileAsList(String path) {
		try {
			InputStream is = UpdateClass.class.getClassLoader().getResourceAsStream(path);
			if (is != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				return Optional.ofNullable(reader.lines().collect(Collectors.toList()));
			} else {
				logger.warn("updateFile not found for " + path);
			}
		} catch (Exception e) {
			logger.error("Error in update: " + path, e);

		}
		return Optional.ofNullable(null);
	}

}
