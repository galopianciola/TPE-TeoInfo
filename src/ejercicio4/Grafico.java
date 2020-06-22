package ejercicio4;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.LogFormat;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Grafico extends ApplicationFrame {
    public Grafico(String nombre , ArrayList<Double> muestra ) {
        super(nombre);








        final JFreeChart lineChart = ChartFactory.createXYLineChart(
                nombre,          // chart title
                "Category",               // domain axis label
                "Value",                  // range axis label
                createDataset(muestra),                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,
                false
        );



        final XYPlot plot = lineChart.getXYPlot();
        final NumberAxis domainAxis = new NumberAxis("Muestras");
        final LogAxis rangeAxis = new LogAxis("Error");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);


        String carpetaCreada="ImagenesGraficos";
        File carpeta=new File(carpetaCreada);
        carpeta.mkdirs();
        int width = 330;
        int height = 300;



        File outFile = new File( "ImagenesGraficos/"+nombre+".jpeg" );
        try {
            ChartUtilities.saveChartAsJPEG(outFile ,lineChart, width ,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XYSeriesCollection createDataset(ArrayList<Double>muestra) {
        XYSeries datasetConvergencia = new XYSeries("Convergencia");
        for (int i=0;i<muestra.size();i++){
            if((i%75==0)&&(muestra.get(i)>1e-4))
                datasetConvergencia.add(i,(double)muestra.get(i));
        }
        XYSeriesCollection dataset=new XYSeriesCollection();
        dataset.addSeries(datasetConvergencia);
        return dataset;
    }
}
