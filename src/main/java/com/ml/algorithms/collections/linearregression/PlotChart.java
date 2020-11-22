package com.ml.algorithms.collections.linearregression;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import com.ml.algorithms.collections.linearregression.models.LinearTwoVariableTrainingSet;

public class PlotChart extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlotChart(List<LinearTwoVariableTrainingSet> data, List<LinearTwoVariableTrainingSet> output) throws IOException {
		super("Linear Regression");

		// Create the chart using the sample data
		JFreeChart chart = createChart(data, output);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1000, 570));
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(List<LinearTwoVariableTrainingSet> trainingSet, 
			List<LinearTwoVariableTrainingSet> output) throws IOException {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("Training set");
		XYSeries testSeries = new XYSeries("Test set");
		Iterator<LinearTwoVariableTrainingSet> iter = trainingSet.iterator();
    	LinearTwoVariableTrainingSet dataSet;
    	
    	while(iter.hasNext()) {
    		dataSet = iter.next();
    		series.add(dataSet.getX(), dataSet.getY());
    	}
 
    	Iterator<LinearTwoVariableTrainingSet> oIter = output.iterator();
    	while(oIter.hasNext()) {
    		dataSet = oIter.next();
    		testSeries.add(dataSet.getX(), dataSet.getY());
    	}
    	
    	dataset.addSeries(testSeries);
		dataset.addSeries(series);
		
		JFreeChart chart = ChartFactory.createScatterPlot("Test", "X", "Y", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = chart.getXYPlot();
		
		/*
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	      renderer.setSeriesPaint( 0 , Color.YELLOW );
	      renderer.setSeriesPaint( 1 , Color.RED );
	      renderer.setSeriesPaint( 2 , Color.GREEN );
	      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
	      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
	      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
	      
	    plot.setRenderer(renderer);  */
		return chart;
	}
}
