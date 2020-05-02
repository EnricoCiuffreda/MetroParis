package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata,DefaultEdge> graph;
	private List<Fermata> fermate;
	private Map<Integer,Fermata> fermateIdMap;
	
	public Model() {
		this.graph=new SimpleDirectedGraph<>(DefaultEdge.class);
		MetroDAO dao=new MetroDAO();
		//creazione vertici
		this.fermate=dao.getAllFermate();
		this.fermateIdMap=new HashMap<>();
		for(Fermata f:this.fermate) {
			fermateIdMap.put(f.getIdFermata(),f);
		}
		Graphs.addAllVertices(graph, fermate);
		System.out.println(this.graph);
		//crezione archi 1(coppie di vertici)
		/*
		for(Fermata fp:this.fermate) {
			for(Fermata fa: this.fermate) {
				//se esite un collegamento aggiungo l'arco
				if(dao.fermateConnesse(fp, fa)) {
					this.graph.addEdge(fp, fa);
				}
			}
		}*/
		//CREAZIONE DEGLI ARCHI 2(solo archi che ci sono)
		/*for(Fermata fp:this.fermate) {
			List <Fermata> connesse=dao.getAdiacenti(fp,fermateIdMap);
			for(Fermata fa:connesse) {
				this.graph.addEdge(fp, fa);
			}
		}*/
		List<CoppiaFermate>coppie=dao.coppieFermate(fermate,fermateIdMap);
		for(CoppiaFermate c:coppie) {
			this.graph.addEdge(c.getFp(), c.getFa());
		}
		System.out.println(this.graph);
	}
	
	public static void main(String args[]) {
		Model m=new Model();
	}

}
