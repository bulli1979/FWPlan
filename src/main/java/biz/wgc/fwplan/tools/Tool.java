package biz.wgc.fwplan.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import biz.wgc.fwplan.constants.ValueHolder;
import biz.wgc.fwplan.data.UserElement;

/**
 * @author Mirko Eberlein
 *
 * The Tool class provides basic operations to convert strings into Date objects and reverse.
 * An Enumerator with static helper functions. (Using Enum as Singelton see Effective Java.)
 *
 */
public enum Tool {
	INSTANCE;
	
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Converts a string which contains a date to a Date object.
	 * @param dateString string to be converted to a Date object. Target format yyyy-MM-dd (required by the SQLite-database)
	 * @return a Date object created from the given String
	 * @throws ParseException	Signals that an error has been reached unexpectedly while parsing.
	 *
	 */
	public Date createDateFromString(String dateString) throws ParseException{
		return formater.parse(dateString);
	}
	
	
	/**
	 * Converts a Date object to a string.
	 * @param date Date object to be converted to String
	 * @return date as String in format yyyy-MM-dd
	 */
	public String createStringFromDate(Date date){
		return formater.format(date);
	}

	public void resizeHeight(UserElement element, double newHeight) {
		double oldHeight = element.getHeight();
		double percentage = newHeight/oldHeight;
		double oldWidth = element.getWidth();
		double newWidth = oldWidth*percentage;
		element.setHeight(newHeight);
		element.setWidth(newWidth);
	}
	
	public void resizeWidth(UserElement element, double newWidth) {
		double oldHeight = element.getHeight();
		double oldWidth = element.getWidth();
		double percentage = newWidth/oldWidth;
		double newHeight = oldHeight*percentage;
		element.setHeight(newHeight);
		element.setWidth(newWidth);
	}
	
	public int countWhaterTransports(){
		if(ValueHolder.INSTANCE.getPlan() == null) {
			return 1;
		}
		String wt2 = ValueHolder.INSTANCE.getPlan().getWatherTransport2();
		String wt3 = ValueHolder.INSTANCE.getPlan().getWatherTransport3();
		String wt4 = ValueHolder.INSTANCE.getPlan().getWatherTransport4();
		if(wt4 != null && wt4.length()>0) {
			return 4;
		}
		if(wt3 != null && wt3.length()>0) {
			return 3;
		}
		if(wt2 != null && wt2.length()>0) {
			return 2;
		}
		return 1;
	}
	
}
