package com.sofiia.statistics;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.sofiia.model.StatisticItem;

import java.util.List;

public class StatisticsWrapper {
    @JacksonXmlElementWrapper(localName = "statistics")
    @JacksonXmlProperty(localName = "item")
    private List<StatisticItem> items;

    public StatisticsWrapper() {}

    public StatisticsWrapper(List<StatisticItem> items) {
        this.items = items;
    }

    public List<StatisticItem> getItems() { return items; }
    public void setItems(List<StatisticItem> items) { this.items = items; }
}
