package com.ml.algorithms.collections.linearregression;

import java.util.Iterator;
import java.util.List;

import com.ml.algorithms.collections.linearregression.models.LinearTwoVariableTrainingSet;


public class LinearRegression {

	// Parameters of the learning model (Weights)
	private static double theta0, theta1;
	
	private double cost;
	
	private static final double LEARNING_RATE = 0.0001;
	
	LinearRegression() {
		
		// We initialize it with random numbers to start with.
		theta0 = Math.random();
		theta1 = Math.random();
	}
	
	public double[] train(List<LinearTwoVariableTrainingSet> trainingSet) {
		cost = computeCostFunction(trainingSet);
		
		// We need to minimize the cost.
		// Minimize J(ϑ0, ϑ1) using Gradient Descent algorithm
		applyGradientDescent(trainingSet);
		
		return new double[] {theta0, theta1};
	}
	
	/**
	 * Cost Function (Squared Error function)
	 * J(ϑ0, ϑ1) = 
	 * 	(1 / 2*(Size of the data set)) * (summation of all m for given i in: (h(xi) - yi)^2)
	 * Through this function we realize the cost of the prediction from the expected output.
	 * 
	 * @param dataSet
	 * @return cost
	 */
	public static double computeCostFunction(List<LinearTwoVariableTrainingSet> dataSet) {
    	int trainingSetSize = dataSet.size();
    	double sumOfIndividualCost = 0;
    	
    	Iterator<LinearTwoVariableTrainingSet> iter = dataSet.iterator();
    	LinearTwoVariableTrainingSet data;
    	
    	while(iter.hasNext()) {
    		data = iter.next();
    		sumOfIndividualCost = sumOfIndividualCost + Math.pow((getHypothesis(data.getX()) - data.getY()), 2);
    	}
    	
    	return (sumOfIndividualCost/(2*trainingSetSize));
    }
	
	/**
	 * h(x) = ϑ0 + ϑ1x
	 * 
	 * @param x
	 * @return predicted value;
	 */
	private static double getHypothesis(Double x) {
    	return theta0 + (theta1*x);
    }
    
	
    /**
     * Gradient Descent
     * Repeat until convergence: ϑj = ϑj - α*(∂/∂ϑj)*J(ϑ0, ϑ1)
     * 
     * Reference:
     * (∂/∂ϑ0)*J(ϑ0, ϑ1) = (1/m)*(summation of all m for given i in: (h(xi) - yi))
     * (∂/∂ϑ1)*J(ϑ0, ϑ1) = (1/m)*(summation of all m for given i in: (h(xi) - yi)*xi)
     * 
     * This method updates the parameters directly as it gets closer to convergence
     * TODO: check negative or positive slop and modify accordingly
     * 
     * @param trainingSet
     */
    private void applyGradientDescent(List<LinearTwoVariableTrainingSet> trainingSet) {
    	Double theta0Temp, theta1Temp, cache1, cache2;
    	int iteration = 0;
    	double computedCost = cost;
    	System.out.println(cost);
    	while(true) {
    		cache1 = LinearRegression.theta0;
    		cache2 = LinearRegression.theta1;
    		
   	    	theta0Temp = LinearRegression.theta0 - (LEARNING_RATE * getDerivativeForParameter0(trainingSet));
	    	theta1Temp = LinearRegression.theta1 - (LEARNING_RATE * getDerivativeForParameter1(trainingSet));
	    	
	    	LinearRegression.theta0 = theta0Temp;
	    	LinearRegression.theta1 = theta1Temp;
	    	iteration++;
	    	System.out.println("\n" + theta0 + " <-> "+ theta1);
	    	computedCost = computeCostFunction(trainingSet);
	    	System.out.println("Old Cost: " + cost);
	    	System.out.println("Computed Cost: " + computedCost);
	    	
	    	
	    	if (cost < computedCost) {
	    		LinearRegression.theta0 = cache1;
	    		LinearRegression.theta1 = cache2;
	    		
	    		break;
	    	} else {
	    		cost = computedCost;
	    	}
    	}
    	System.out.println(iteration);
    }
    
    /**
     * Derivative of cost function computed with respect to ϑ0
     * (∂/∂ϑ1)*J(ϑ0, ϑ1) = (1/m)*(summation of all m for given i in: (h(xi) - yi)*xi)
     * 
     * @param trainingSet
     * @return updated parameter ϑ1
     */
    private double getDerivativeForParameter0(List<LinearTwoVariableTrainingSet> trainingSet) {
    	int trainingSetSize = trainingSet.size();
    	double sumOfCost = 0;
    	
    	Iterator<LinearTwoVariableTrainingSet> iter = trainingSet.iterator();
    	LinearTwoVariableTrainingSet dataSet;
    	
    	while(iter.hasNext()) {
    		dataSet = iter.next();
    		sumOfCost = sumOfCost + (getHypothesis(dataSet.getX()) - dataSet.getY());
    	}
    	
    	return (sumOfCost/trainingSetSize);
    }
    
    /**
     * Derivative of cost function computed with respect to ϑ1
     * (∂/∂ϑ1)*J(ϑ0, ϑ1) = (1/m)*(summation of all m for given i in: (h(xi) - yi))
     * 
     * @param trainingSet
     * @return updated parameter ϑ0
     */
    private double getDerivativeForParameter1(List<LinearTwoVariableTrainingSet> trainingSet) {
    	int trainingSetSize = trainingSet.size();
    	double sumOfCost = 0;
    	
    	Iterator<LinearTwoVariableTrainingSet> iter = trainingSet.iterator();
    	LinearTwoVariableTrainingSet dataSet;
    	
    	while(iter.hasNext()) {
    		dataSet = iter.next();
    		sumOfCost = sumOfCost + ((getHypothesis(dataSet.getX()) - dataSet.getY())*dataSet.getX());
    	}
    	
    	return (sumOfCost/trainingSetSize);
    }
}
