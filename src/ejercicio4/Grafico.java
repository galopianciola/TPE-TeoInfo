package ejercicio4;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Grafico extends ApplicationFrame {
    public Grafico(String nombre , String titulo, ArrayList<Double> muestra ) {
        super(nombre);
        JFreeChart lineChart = ChartFactory.createLineChart(
                titulo,
                "Numero Muestra","Error",
                createDataset(muestra),
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

    private CategoryDataset createDataset(ArrayList<Double>muestra) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (int i=0;i<muestra.size();i++){
            if((i%5000==0)&&(muestra.get(i)<1e-2)&&(muestra.get(i)>-1e-2)) {
                dataset.addValue(muestra.get(i), "Error", "" + i);
            }
        }
        return dataset;
    }
}
