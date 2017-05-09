package org.jaffa.modules.printing.services;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import org.jaffa.datatypes.Parser;
import org.jaffa.unittest.UnitTestUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ImageDom {
    
    String chartData = UnitTestUtil.getDataDirectory()  + "/resources/ChartData.csv";
    String stringLocalFile = UnitTestUtil.getDataDirectory()  + "/resources/SampleImage.gif";
    String stringUrl = "http://jaffa.sourceforge.net/wiki/skins/common/images/wiki.png";
    String stringClasspath = "resources/PoweredByJaffa.jpg";
    URL urlImage = null;
    File fileImage = null;
    Image awtImage = null;
    
    ImageDom() throws Exception {
        urlImage = new URL(stringUrl);
        fileImage = new File(stringLocalFile);
        // Create a graph
        JFreeChart chart = createChart(createDataset());
        awtImage = chart.createBufferedImage(1000,500);
    }
    
    // 135x135 .png - Jaffa Logo
    public URL getUrlImage() {
        return urlImage;
    }
    
    // 16x16 .gif - Save Icon
    public File getFileImage() {
        return fileImage;
    }
    public String getStringLocalFile() {
        return stringLocalFile;
    }

    // 135x135 .png - Jaffa Logo
    public String getStringUrl() {
        return stringUrl;
    }
    
    public String getStringClasspath() {
        return stringClasspath;
    }
    public Image getAwtImage() {
        return awtImage;
    }

    /**
     * Returns a sample dataset.
     * @return The dataset.
     */
    private CategoryDataset createDataset() throws Exception {
        String[] series = null;

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Look for a related data file...
        File data = new File(chartData);

        // Read the file, and populate PageDetailsExtended
        BufferedReader in = new BufferedReader( new FileReader(data) );
        StringBuffer buf1 = new StringBuffer();
        String line = null;
        int[] colOrder = null;
        int row = 0;
        while ( (line = in.readLine()) != null) {
            row++;
            if(line.startsWith("#") || line.length()==0)
                // ignore commented out or blank lines.
                continue;

            String[] cols = line.split(",");
            if(series==null) {
                // This is the header, read in cols 2-n as the series names
                series = cols;
                continue;
            }
            for(int i = 1; i<3; i++) {
                dataset.addValue(Parser.parseDecimal(cols[i]), series[i], cols[0]);
            }
        }
        return dataset;
    }
                

    /**
     * Creates a sample chart.
     * @param dataset  the dataset.
     * @return The chart.
     */
    private JFreeChart createChart(CategoryDataset dataset) {
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "jaffa.sf.net 2005 Statistics",// chart title
            "Month",                  // domain axis label
            "Hits",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
 
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
 
        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);
 
        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);
 
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
 
        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
 
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue,
            0.0f, 0.0f, new Color(0, 0, 64)
        );
        GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green,
            0.0f, 0.0f, new Color(0, 64, 0)
        );
        GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red,
            0.0f, 0.0f, new Color(64, 0, 0)
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);
 
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
 
        return chart;
    }
 }