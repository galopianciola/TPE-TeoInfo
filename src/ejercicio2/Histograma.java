package ejercicio2;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import common.ImagenWill;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

public class Histograma extends ApplicationFrame {
	double[] arregloDistribucion = new double[256]; // creo el arreglo donde se van a guardar el numero de escalas de grises

	public Histograma(String title,BufferedImage img) {
		super(title);
		JPanel chartPanel = crearPanel(img);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
		setContentPane(chartPanel);
	}

	private static IntervalXYDataset crearDataset(BufferedImage img) {
		double[] arregloDistribucion = new double[img.getWidth()];
		HistogramDataset dataset = new HistogramDataset();
		for (int i = 0; i < img.getWidth(); i++) { // recorro ancho de la imagen que le paso por parametro
			for (int j = 0; j < img.getHeight(); j++) { // recorro alto
				arregloDistribucion[i] += -(img.getRGB(i,j)); //guardo los valores de imagen en el arreglo
			}
		}
		dataset.addSeries( "Gamma de grises",arregloDistribucion,16);
		return dataset;
	}
	private static JFreeChart crearChart(IntervalXYDataset dataset) {
		JFreeChart chart = ChartFactory.createHistogram(
				"Histograma",
				null,
				null,
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
		);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
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
