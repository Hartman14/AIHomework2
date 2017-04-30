package ga;

import utils.*;
import java.util.*;

public class GeneticAlgorithm {

	protected int mPopulationSize;
	protected int mTournamentsSize;
	protected double mCrossoverProb;
	protected double mMutationProb;
	protected ArrayList<Link> mNetwork;
	
	private ArrayList<String> population;
	
	private Random rand;

	public GeneticAlgorithm(ArrayList<Link> network, int populationSize,
			int tournamentsSize, double crossoverProb, double mutationProb) {
		mNetwork = network;
		mPopulationSize = populationSize;
		mTournamentsSize = tournamentsSize;
		mCrossoverProb = crossoverProb;
		mMutationProb = mutationProb;
		// ...
		rand = new Random();
		population = new ArrayList<>();
		createInitialPopulation();
	}

	public void createInitialPopulation() {
		for(int i = 0; i < mPopulationSize; i++){
			StringBuilder chromosome = new StringBuilder();
			for(int j = 0; j < mNetwork.size(); j++){
				chromosome.append(rand.nextInt(2));
			}
			population.add(chromosome.toString());
		}
	}
	
	public void printPopulation(){
		for (String s:population){
			System.out.println(s);
		}
	}

	public void runOneGeneration() {
		ArrayList<String> newPop = new ArrayList<>();
		
		for(int i = 0; i < population.size(); i++){
			if(newPop.size() < population.size()){
				String s1 = tournamentSize(mTournamentsSize);
				String s2 = tournamentSize(mTournamentsSize);
				/*while(s1 == s2){
					s2 = tournamentSize(mTournamentsSize);
				}*/
				String temp = crossOver(s1, s2);
				String mTemp = mutate(temp);
				newPop.add(mTemp);
			}
		}
		population = newPop;
		
	}

	//red line 
	public double getAverageFitness() {
		double avgFit = 0.0;
		double testFit = 0.0;
		
		for(String i : population){
			testFit += Fitness.calculateFitnessOfOverlayNetwork(mNetwork, i);
		}
		avgFit = (testFit / population.size());
		
		return avgFit;
	}

	//blue line
	public double getBestFitness() {
		double numeroUno = 0.0;
		double testFit = 0.0;
		
		for(String i : population){
			testFit = Fitness.calculateFitnessOfOverlayNetwork(mNetwork, i);
			if(testFit > numeroUno){
				numeroUno = testFit;
			}
		}
		
		
		return numeroUno;
	}
	
	public String tournamentSize(int size){
		String bestChromosome = null;
		double fitValue = 0.0;
		ArrayList<Integer> used = new ArrayList<>();
		
		for(int i = 0; i < size; i++){
			int grab = rand.nextInt(population.size());
			while(used.contains(grab)){
				grab = rand.nextInt(population.size());
			}
			used.add(grab);
			double currentFit = Fitness.calculateFitnessOfOverlayNetwork(mNetwork, population.get(grab));
			
				if((fitValue < currentFit) || (bestChromosome == null)){
					fitValue = currentFit;
					bestChromosome = population.get(grab);
				}
			
		}
		
		return bestChromosome;
	}

	public String crossOver(String parent1, String parent2){
		StringBuilder toaster = new StringBuilder();
		for(int i = 0; i < mNetwork.size(); i++){
			double waffle = rand.nextDouble();
			if(waffle > mCrossoverProb){
				toaster.append(parent2.charAt(i));
			}
			else{
				toaster.append(parent1.charAt(i));
			}
		}
		String breakfast = toaster.toString();
		
		return breakfast;
	}
	
	public String mutate(String testSubject){
		StringBuilder madScientist = new StringBuilder();
		for(int i = 0; i < testSubject.length(); i++){
			double formula = rand.nextDouble();
			if(formula <= mMutationProb){
				if(testSubject.charAt(i) == '1'){
					madScientist.append("0");
				}
				else{
					madScientist.append("1");
				}
			}
			else{
				madScientist.append(testSubject.charAt(i));
			}
		}
		String monster = madScientist.toString();
		
		return monster;
	}
	
}
