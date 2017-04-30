package main;

import java.io.IOException;
import java.util.*;

import javax.swing.SwingUtilities;

import utils.*;
import ga.GeneticAlgorithm;

public class RunGA {

	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		int populationSize = 100;
		int tournamentsSize = 2;
		double crossoverProb = 0.7;
		double mutationProb = 0.001;
		int maxNumOfGenerations = 1000;
		double bestFitness = 0.0;
		ArrayList<Link> network = new ArrayList<Link>();

		final double[] yBestFitness = new double[maxNumOfGenerations];
		final double[] yAverageFitness = new double[maxNumOfGenerations];

		// read file
		FileHelper filehelper = new FileHelper();
		String fileName = "network.txt";
		try {
			network = filehelper.readFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// run GA
		GeneticAlgorithm ga = new GeneticAlgorithm(network, populationSize,
				tournamentsSize, crossoverProb, mutationProb);

		yBestFitness[0] = ga.getBestFitness();
		yAverageFitness[0] = ga.getAverageFitness();

		for (int i = 0; i < maxNumOfGenerations; i++) {
			double bestFitnessCurrentRound;
			ga.runOneGeneration();
			bestFitnessCurrentRound = ga.getBestFitness();
			if (bestFitnessCurrentRound > bestFitness) {
				bestFitness = bestFitnessCurrentRound;
			}
			yBestFitness[i] = bestFitness;
			yAverageFitness[i] = ga.getAverageFitness();
		}
		System.out.println("Best overall fitness: " + ga.getBestFitness());
		System.out.println("Average overall fitness: " + ga.getAverageFitness());
		ga.printPopulation();
		// plot graph
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Plot(yAverageFitness, yBestFitness).setVisible(true);
			}
		});
	}

}
