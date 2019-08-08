package spring.batch;

import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.kie.api.runtime.KieSession;

import spring.batch.Usuario;

// Implementando la interfaz ItemProcessor
public class CustomItemProcessor implements ItemProcessor<Usuario, Usuario> {
	
	// Creamos un HashMap cuya Key será el ID y el valor el precio	
	private HashMap<Integer, Double> mapa = new HashMap<>();
		
	/*
	 * Abrimos una nueva sesión Kie con la configuración de Drools
	 * para poder aplicar las reglas posteriormente
	 */
	private KieSession kSession = new DroolsConfiguration().getKieSession();
	
	@SuppressWarnings("unchecked")
	@Override
	/*
	 * Método perteneciente a la interfaz ItemProcessor
	 * Como entrada se le dará un item de tipo Usuario
	 * En este caso, este Item será cada regristro de la tabla que nos proporciona el ItemReader
	 * Devolverá el item si cumple las reglas, y nulo si no las cumple
	 */
	public Usuario process(Usuario item) throws Exception {
		
		// Lista de tipo String donde almacenaremos la oferta para posteriormente insertarla en la tabla ofertas
		ArrayList<String> lista = new ArrayList<String>();
		
		//Variable declarada también en la hoja de cálculo
		kSession.setGlobal("lista", new ArrayList<String>());
		
		//Definición Hashmap que sumará el precio si corresponde a determinado usuario y al mes anterior
		Double tempo = 0d;
		if (mapa.containsKey(item.getId()) && (Metodo.nMes(item.getFecha()) == Metodo.nMes(new Date())-1)) {
			
			tempo = mapa.get(item.getId());
			
			mapa.replace(item.getId(), item.getPrecio() + tempo);

			item.setPrecio(mapa.get(item.getId()));
		} 
		
		else {
			if (Metodo.nMes(item.getFecha()) == Metodo.nMes(new Date())-1) {
			mapa.put(item.getId(), item.getPrecio());
			}
		}
		
		//Una vez obtenido el valor del mapa, lo pasamos a la hoja de cálculo con setGlobal
		kSession.setGlobal("mapa", mapa);
		
		//Insertamos cada item en la sesión
		kSession.insert(item);

		//Lanzamos todas las reglas
		int nReglas = kSession.fireAllRules();
		
		/*
		 * Si el número de reglas es mayor que 0, significa que se han cumplido
		 * Por lo que devolveremos el item y pasará al ItemWriter
		 */
		if (nReglas > 0) {
			System.out.println("Procesando... " + item + "\n");
			//Obtenemos el valor de la lista de la hoja de calculo, que corresponde al tipo de oferta
			lista = (ArrayList<String>) kSession.getGlobal("lista");
			item.setOferta(lista);
			mapa.clear();
			return item;
		}
		
		//Devolvemos null si no se cumple la regla
		return null;
	} 
	
}