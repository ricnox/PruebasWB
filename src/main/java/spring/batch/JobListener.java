package spring.batch;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

	private LocalDateTime startTime, stopTime;

	/*
	 * Método beforeJob (antes del Job)
	 * Lo ejecutamos para definir y mostrar a qué hora empieza el job.
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
		startTime = new LocalDateTime();
		System.out.println("EL JOB EMPIEZA A LAS: " + startTime);
	}

	/*
	 * Método afterJob (después del job)
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		// Mostramos a qué hora finaliza el job
		stopTime = new LocalDateTime();
		System.out.println("EL JOB TERMINA A LAS: " + stopTime);

		// Si el estado del Job es COMPLETED, mostramos el mensaje de que se ha completado correctamente.
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("JOB COMPLETADO CORRECTAMENTE.");
			
			/* 
			 * Obtenemos y mostramos la diferencia en milisegundos del tiempo en el que empieza el job
			 * y en el que acaba, para obtener la duración del job
			 */
			Period period = new Period(stopTime, startTime);
			long diff = Math.abs(period.getMillis());
			System.out.println("DURACIÓN: " + diff + " MILISEGUNDOS.");
			
		// Si el estado del Job es FAILED, mostraremos las excepciones por las que lo ha hecho
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			System.out.println("El JOB HA FALLADO DEBIDO A LAS SIGUIENTES EXCEPCIONES: ");
			List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
			for (Throwable th : exceptionList) {
				System.err.println("Exception: " + th.getLocalizedMessage());
			}
		}
	}
}
