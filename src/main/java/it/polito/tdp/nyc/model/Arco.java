package it.polito.tdp.nyc.model;

public class Arco implements Comparable<Arco>{

	private String NTA1;
	private String NTA2;
	private Double weight;
	
	public Arco(String nTA1, String nTA2, Double weight) {
		super();
		NTA1 = nTA1;
		NTA2 = nTA2;
		this.weight = weight;
	}
	
	public String getNTA1() {
		return NTA1;
	}
	public void setNTA1(String nTA1) {
		NTA1 = nTA1;
	}
	public String getNTA2() {
		return NTA2;
	}
	public void setNTA2(String nTA2) {
		NTA2 = nTA2;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Arco o) {
		return Double.compare(o.weight, this.weight);
	}

	@Override
	public String toString() {
		return NTA1 + " --- " + NTA2 + "  |  Peso: " + weight;
	}
	
	
	
}
