package com.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Frame {
    private List<Face> faces;
    private String path;
    private int id;
    private int vedioId;
    private int seq;
}
