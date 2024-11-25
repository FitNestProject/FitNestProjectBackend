package org.ilisi.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Route {

    @JsonProperty("coordinatesFromPath")
    private List<List<Double>> path;

    public List<List<Double>> getPath() {
        return path;
    }

    public void setPath(List<List<Double>> path) {
        this.path = path;
    }

}
