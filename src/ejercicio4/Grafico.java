package ejercicio4;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
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
    public Grafico(String nombre , String titulo, ArrayList<Double> muestra ,double epsilon) {
        super(nombre);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                titulo,
                "Numero Muestra","Error",
                createDataset(muestra,epsilon),
                PlotOrientation.VERTICAL,
                true,true,false);

        String carpetaCreada="ImagenesGraficos";
        File carpeta=new File(carpetaCreada);
        carpeta.mkdirs();
        int width = 1300;
        int height = 760;

        File outFile = new File( "ImagenesGraficos/"+nombre+".jpeg" );
        try {
            ChartUtilities.saveChartAsJPEG(outFile ,lineChart, width ,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XYDataset createDataset(ArrayList<Double>muestra,double epsilon) {
        XYSeries datasetConvergencia = new XYSeries("Convergencia");
        XYSeries datasetError = new XYSeries("Error");
        for (int i=0;i<muestra.size();i++){
            if((i%10==0)&&(muestra.get(i)<0.5)&&(muestra.get(i)>-0.5)) {
                datasetConvergencia.add(i,(double)muestra.get(i));
                datasetError.add(i,epsilon);
            }
        }
        XYSeriesCollection dataset=new XYSeriesCollection();
        dataset.addSeries(datasetConvergencia);
        dataset.addSeries(datasetError);
        return dataset;
    }
}
