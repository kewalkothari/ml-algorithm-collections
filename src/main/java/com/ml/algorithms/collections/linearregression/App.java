package com.ml.algorithms.collections.linearregression;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.algorithms.collections.linearregression.models.LinearTwoVariableTrainingSet;

public class App 
{
    @SuppressWarnings("unchecked")
	public static void main( String[] args ) throws JsonParseException, JsonMappingException, IOException
    {
    	
        InputStream in = App.class.getClassLoader().getResourceAsStream("training.json");
        System.out.println( "Hello World!" );
        List<LinearTwoVariableTrainingSet> trainingData = new ObjectMapper().readValue(in,
        		new TypeReference<List<LinearTwoVariableTrainingSet>>() { });
        LinearRegression algorithm = new LinearRegression();
        double[] data = algorithm.train(trainingData);
        System.out.println(data[0] + " - " + data[1]);
        double value = data[0] + (data[1]*16);
        System.out.println(value);
        
        InputStream testIn = App.class.getClassLoader().getResourceAsStream("test.json");
        List<LinearTwoVariableTrainingSet> testData = new ObjectMapper().readValue(testIn,
        		new TypeReference<List<LinearTwoVariableTrainingSet>>() { });
        
        Iterator<LinearTwoVariableTrainingSet> iter = testData.iterator();
    	LinearTwoVariableTrainingSet dataSet, outputSet;
    	List<LinearTwoVariableTrainingSet> output = new ArrayList<>();
    	while(iter.hasNext()) {
    		dataSet = iter.next();
    		outputSet = new LinearTwoVariableTrainingSet();
    		double computation = data[0] + (data[1]*dataSet.getX());
    		outputSet.setX(dataSet.getX());
    		outputSet.setY(computation);
    		output.add(outputSet);
    	}
    	
    	System.out.println("Output Cost: " + LinearRegression.computeCostFunction(testData));
        
        PlotChart demo = new PlotChart(trainingData, output);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
        
    }
    
    
}
