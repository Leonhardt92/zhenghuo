package com.demo.model;

import lombok.Data;

@Data
public class Face {
    private String id;
    private double left;
    private double top;
    private double width;
    private double height;
    private double rotation;
    private int frameId;

}
