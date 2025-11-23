package com.sofiia.output;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sofiia.model.StatisticItem;
import com.sofiia.statistics.StatisticsWrapper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XmlWriter {

    public void write(Map<String, Long> stats, String attribute) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

            List<StatisticItem> items = stats.entrySet().stream()
                    .map(e -> new StatisticItem(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

            StatisticsWrapper wrapper = new StatisticsWrapper(items);

            File outputDir = new File("target/output");
            if (!outputDir.exists() && !outputDir.mkdirs()) {
                throw new IllegalStateException("Could not create output directory: " + outputDir.getAbsolutePath());
            }

            String fileName = "target/output/statistics_by_" + attribute + ".xml";
            xmlMapper.writeValue(new File(fileName), wrapper);

            System.out.println("Statistics saved to file: " + fileName);
        } catch (Exception e) {
            System.err.println("Error writing XML: " + e.getMessage());
        }
    }
}