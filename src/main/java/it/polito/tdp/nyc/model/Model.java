package it.polito.tdp.nyc.model;

import java.util.List;

import it.polito.tdp.nyc.db.NYCDao;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import java.util.*;

public class Model {
	
	private List<String> boroughs;
	private NYCDao dao;
	private Graph<String, DefaultWeightedEdge> graph;
	
	public Model() {
		this.dao = new NYCDao();
		this.boroughs = this.getAllBoroughs();	
	}


	public List<String> getAllBoroughs(){
		return this.dao.getAllBoroughs();
	}


	public List<String> getBoroughs() {
		return boroughs;
	}
	
	public Map<String, Set<String>> getNTASSID(String borough){
		return this.dao.getNTASSID(borough);
	}

	public NYCDao getDao() {
		return dao;
	}
	
	public void creaGrafo(String borough) {
		this.graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.getNTASSID(borough).keySet());
		Map<String, Set<String>> NTASSID = this.getNTASSID(borough);
		for (String NTA1 : NTASSID.keySet()) {
			for (String NTA2 : NTASSID.keySet()) {
				if (NTA1.compareTo(NTA2) < 0) { //Un semplice accorgimento anzichè avere la !equals mi permette 
					//di non scorrere doppiamente. Esempio: A --> B e poi B --> A
					Set<String> union = new HashSet<>(NTASSID.get(NTA1));
					union.addAll(NTASSID.get(NTA2));
					Graphs.addEdge(this.graph, NTA1, NTA2, union.size());
				}
			}
		}
		
		
	}


	public Graph<String, DefaultWeightedEdge> getGraph() {
		return graph;
	}
	
	public List<Arco> analisiArchi(){
		
		List<Arco> result = new ArrayList<Arco>();
		
		double sumWeight = 0;
		for (DefaultWeightedEdge dfe : this.graph.edgeSet()) {
			sumWeight += this.graph.getEdgeWeight(dfe);
		}
		double avgWeight = sumWeight/this.graph.edgeSet().size();
		
		for (DefaultWeightedEdge dfe : this.graph.edgeSet()) {
			
			double weight = this.graph.getEdgeWeight(dfe);
			String NTA1 = this.graph.getEdgeSource(dfe);
			String NTA2 = this.graph.getEdgeTarget(dfe);
			
			if (weight > avgWeight) {
				
				result.add(new Arco(NTA1, NTA2, weight));
			}
		}
		
		Collections.sort(result);
		
		return result;
	}
	
}
