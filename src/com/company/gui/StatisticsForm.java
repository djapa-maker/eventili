/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.gui;

import com.codename1.ui.*;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.company.entities.Ticket;

/**
 *
 * @author islem
 */
public class StatisticsForm  extends Form {
    public StatisticsForm(ArrayList<Ticket> tickets) {
        super("Statistics");

       // Count the occurrences of each reclamation status
HashMap<String, Integer> statusCounts = new HashMap<>();
for (Ticket ticket : tickets) {
    String status = ticket.getStatus();
    Integer count = statusCounts.get(status);
    if (count == null) {
        count = 0;
    }
    count++;
    statusCounts.put(status, count);
}

// Create a CategorySeries to hold the data
CategorySeries series = new CategorySeries("Ticket Status");

// Add the data to the series
for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
    String status = entry.getKey();
    int count = entry.getValue();
    series.add(status, count);
}
        // Create a renderer for the pie chart
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setChartTitle("Ticket Status");
        renderer.setLabelsColor(0xFF000000); // Set label color to black
        renderer.setChartTitleTextSize(60);
        renderer.setLabelsTextSize(40);
        renderer.setLegendTextSize(40);

        // Set colors for the pie slices
        int[] colors = new int[]{0xFF0000FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFFF00, 0xFFFF00FF};
        for (int i = 0; i < colors.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            renderer.addSeriesRenderer(seriesRenderer);
        }

        // Create a pie chart and set the renderer
        PieChart chart = new PieChart(series, renderer);

        // Create a ChartComponent and add it to the form
        ChartComponent chartComponent = new ChartComponent(chart);
        add(chartComponent);
    
    }
}