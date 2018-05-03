package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	


}
