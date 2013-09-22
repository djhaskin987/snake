package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Wrapper of Date for guarantee that
 * the Date is within the specified
 * range
 * @author Kevin
 *
 */
public class ValidDate implements Serializable{
	private Date date;
	
	public ValidDate(Date date){
		
	}
	
	public Date getDate(){
		return this.date;
	}

}
