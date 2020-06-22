package ejercicio4;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
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
    public Grafico(String nombre , String titulo, ArrayList<Double> muestra ,double epsilon) {
        super(nombre);
        LogAxis yAxis = new LogAxis("");
        yAxis.setBase(10);
        LogFormat format = new LogFormat(yAxis.getBase(), "", "", true);
        yAxis.setNumberFormatOverride(format);
        XYPlot plot = new XYPlot(
                new XYSeriesCollection(createDataset(muestra, epsilon)),
                new NumberAxis(),
                yAxis,
                new XYLineAndShapeRenderer(true, false));
        JFreeChart lineChart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);

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

    private XYSeries createDataset(ArrayList<Double>muestra,double epsilon) {
        XYSeries datasetConvergencia = new XYSeries("Convergencia");
        for (int i=0;i<muestra.size();i++){
            datasetConvergencia.add(i,(double)muestra.get(i));
        }
        XYSeriesCollection dataset=new XYSeriesCollection();

        return datasetConvergencia;
    }
}
