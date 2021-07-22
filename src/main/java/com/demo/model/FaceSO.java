package com.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class FaceSO {
    private int frameId;
    private String path;
    private List<Face> faces;
}
