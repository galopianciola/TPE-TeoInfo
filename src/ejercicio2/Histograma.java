package ejercicio2;


import java.io.File;
import java.io.IOException;

import common.ImagenWill;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

public class Histograma extends ApplicationFrame {
	public static int CANTCOLOR=256;

	public Histograma(String title,ImagenWill img) {
		super(title);
		JPanel chartPanel = crearPanel(img);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
		setContentPane(chartPanel);
	}

	private static DefaultCategoryDataset crearDataset(ImagenWill img) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i=0;i<img.getArregloFrecuencia().length;i++) {
			if (img.getArregloFrecuencia()[i]!=0){
				dataset.addValue(img.getArregloFrecuencia()[i],"numero de repeticiones",""+(int)(Math.floor(i/16)));
			}

		}
		return dataset;
	}

	private static JFreeChart crearChart(DefaultCategoryDataset dataset,String nombre) {
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
			ChartUtilities.saveChartAsJPEG(new File("histograma de "+nombre+".jpg"), chart, 500, 475);
		}
		catch(IOException e){
			System.out.println("Error al abrir el archivo");
		}
		return chart;
	}

	public static JPanel crearPanel(ImagenWill img) {
		JFreeChart chart = crearChart(crearDataset(img),img.getNombreImagen());
		return new ChartPanel(chart);
	}


}
