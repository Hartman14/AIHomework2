package utils;

import java.util.ArrayList;

public class Fitness {

	private static double fitness;
	private static double cost;
	private static double costCompleteNetwork;

	public static double calculateFitnessOfOverlayNetwork(ArrayList<Link> network, String chromosome) {
		fitness = 0.0;
		cost = 0.0;
		costCompleteNetwork = 0.0;
		for (int i = 0; i < network.size(); i++) {
			costCompleteNetwork += network.get(i).getCost();
			if (chromosome.charAt(i) == '1') {
				cost += network.get(i).getCost();
			}
		}
		fitness = 1 - (cost / costCompleteNetwork);
		return fitness;
	}
}
