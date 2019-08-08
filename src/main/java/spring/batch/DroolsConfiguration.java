package spring.batch;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DroolsConfiguration {
	// private static final String RULES_PATH = "rules/";
	/* KieServices:
	 * Proporciona acceso a toda la infraestructura y tiempo de ejecución de Kie. 
	 * Proporciona varias factorías, servicios y métodos de utilidad. 
	 * Por lo tanto, primero se debe obtener una instancia de KieServices 
	 */
    private KieServices kieServices=KieServices.Factory.get();
 
    /* 
     * KieFileSystem:
     * Es un sistema de archivos en memoria proporcionado por el framework. 
     * Este código proporciona el contenedor para definir los recursos de Drools, 
     * tales como: archivos de reglas, tablas de decisiones...
     * Se trata de construir el contenido de KieFileSystem pasándolo al KieBuilder
     * Como resultado, se creará un KieModule
     */
    private  KieFileSystem getKieFileSystem() throws IOException{
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        // Archivo de reglas:
        List<String> rules=Arrays.asList("rules.xls");
        for(String rule:rules){
            kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
        }
        return kieFileSystem;

    }
 
    /*
     * KieContainer
     * es un marcador de posición para todos los KieBases para KieModule en particular . 
     * KieContainer está construido con la ayuda de otros beans, incluidos KieFileSystem, KieModule y KieBuilder.
     * El método buildAll () invocado en KieBuilder construye todos los recursos y los vincula a KieBase. 
     * Se ejecuta con éxito solo cuando puede encontrar y validar todos los archivos de reglas.
     */
    public KieContainer getKieContainer() throws IOException {
        getKieRepository();

        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
        kb.buildAll();

        KieModule kieModule = kb.getKieModule();
        KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        return kContainer;

    }
 
    /*
     * KieRepository
     * Añade el KieModule construido al KieRepository
     */
    private void getKieRepository() {
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
                        public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
    }
 
    /*
     * KieSession:
     * Las reglas se activan abriendo un bean KieSession , que se obtiene de KieContainer.
     */
    public KieSession getKieSession(){
        getKieRepository();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/rules.xls"));
        
        
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();

        KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        return kContainer.newKieSession();

    }
}