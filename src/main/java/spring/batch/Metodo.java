package spring.batch;

import java.util.Calendar;
import java.util.Date;

public class Metodo {

	public static int nMes(Date item) {
		
		Calendar mes = Calendar.getInstance();
		mes.setTime(item);
		
		int nmes = (mes.get(Calendar.MONTH)) + 1;
		
		return nmes;
	}
	
}
