package it.polito.tdp.nyc.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.creaGrafo("SI");
		System.out.println(model.getGraph().vertexSet().size());
		System.out.println(model.getGraph().edgeSet().size());
		System.out.println(model.analisiArchi().size());

	}

}
