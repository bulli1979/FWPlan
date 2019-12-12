package biz.wgc.fwplan.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationSettings {
	private static final String MAP_LINK_NAME = "maplink";
	private static final String WIDTH_LEFT_NAME = "widthLeftDifference";
	private static final String WIDTH_RIGHT_NAME = "widthRightDifference";
	private static final String HEIGHT_TOP_NAME = "topDifference";
	private static final String HEIGHT_BOTTOM_NAME = "bottomDifference";
	private static final String USERDATA = "userdata";
	public static String MAPLINK;
	public static int WIDTH_LEFT_DIFFERENCE = 340;
	public static int WIDTH_RIGHT_DIFFERENCE = 0;
	public static int HEIGHT_TOP_DIFFERENCE = 220;;
	public static int HEIGHT_BOTTOM_DIFFERENCE = 100;
	public static int BORDERWIDTH = 2;
	public static final String ABOUTUS = "https://webgate.biz/einsatzplaner/";
	public static final String EDITICONS_PROPERTIES = System.getProperty("user.dir") + File.separator + USERDATA + File.separator + "properties" + File.separator + "icon.properties";
	public static final String APP_PROPERTIES = System.getProperty("user.dir") + File.separator + USERDATA + File.separator + "properties" + File.separator + "app.properties";
	
	public static void setSettings() throws IOException {
		Properties props = new Properties();
		InputStream inStream = new FileInputStream(APP_PROPERTIES);
		props.load(inStream);
		if(props != null) {
			ApplicationSettings.MAPLINK = props.getProperty(MAP_LINK_NAME);
			ApplicationSettings.WIDTH_LEFT_DIFFERENCE = Integer.parseInt(props.getProperty(WIDTH_LEFT_NAME));
			ApplicationSettings.WIDTH_RIGHT_DIFFERENCE = Integer.parseInt(props.getProperty(WIDTH_RIGHT_NAME));
			ApplicationSettings.HEIGHT_TOP_DIFFERENCE = Integer.parseInt(props.getProperty(HEIGHT_TOP_NAME));
			ApplicationSettings.HEIGHT_BOTTOM_DIFFERENCE = Integer.parseInt(props.getProperty(HEIGHT_BOTTOM_NAME));
		}
		inStream.close();
	}
}

