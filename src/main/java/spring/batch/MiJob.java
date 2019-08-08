package spring.batch;


import org.springframework.batch.core.Job; 
import org.springframework.batch.core.JobExecution; 
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * Al establecer una clase como @Component
 * Le estamos diciendo a Spring que detecte automáticamente esta clase para la inyección de las dependencias
 */
@Component
public class MiJob {
	
    // Creando el contexto de la aplicación - contenedor de beans del que obtendremos la información necesaria
    ApplicationContext context = new ClassPathXmlApplicationContext("job/jobConfig.xml");  
    
    // Creando el job launcher: obtenemos el bean "jobLauncher" de los archivos jobConfig.xml/context.xml
    JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher"); 
  
    // Creando el job: obtenemos el bean "jobUsuarios" de los archivos jobConfig.xml/context.xml
    Job job = (Job) context.getBean("jobUsuarios"); 
    
    // @Scheduled sirve para repetir un método cada x tiempo (en este caso: cada 10000 milisegundos)
    @Scheduled(fixedDelay = 10000)
    public void ejecutar() throws Exception {
      
        /*
         * Ejecutando el Job
         * Ejecutamos nuestro job asignándole unos parámetros nuevos
         * Estos parámetros, en este caso, son creados automáticamente por Spring Batch
         */
        JobExecution execution = jobLauncher.run(job, new JobParameters());
        
        /*
         * Obtenemos el estado de la ejecución. Generalmente son dos:
         * COMPLETED Y FAILED
         */
        System.out.println("ESTADO: " + execution.getStatus()); 
     }  
}
