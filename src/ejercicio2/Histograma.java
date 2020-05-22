package ejercicio2;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



import common.ImagenWill;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

public class Histograma extends ApplicationFrame {
	public static int CANTCOLOR=256;

	public Histograma(String title,BufferedImage img) {
		super(title);
		JPanel chartPanel = crearPanel(img);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
		setContentPane(chartPanel);
	}

	private static DefaultCategoryDataset crearDataset(BufferedImage img) {
		double[] arregloDistribucion = new double[CANTCOLOR];
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < img.getWidth(); i++) { // recorro ancho de la imagen que le paso por parametro
			for (int j = 0; j < img.getHeight(); j++) { // recorro alto
				Color simbolo= new Color(img.getRGB(i,j));
				arregloDistribucion[simbolo.getRed()]++; //guardo los valores de imagen en el arreglo
			}
		}
		for (int i=0;i<arregloDistribucion.length;i++) {
			if (arregloDistribucion[i]!=0){
				dataset.addValue(arregloDistribucion[i],"numero de repeticiones",""+(Math.floor(i/16)));
			}

		}
		return dataset;
	}
	private static JFreeChart crearChart(DefaultCategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"Histograma",
				null,
				null,
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
		);

		try{
			ChartUtilities.saveChartAsJPEG(new File("C:\\histograma.jpg"), chart, 500, 475);
		}
		catch(IOException e){
			System.out.println("Error al abrir el archivo");
		}
		return chart;
	}

	public static JPanel crearPanel(BufferedImage img) {
		JFreeChart chart = crearChart(crearDataset(img));
		return new ChartPanel(chart);
	}


}
