package com.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Vedio {
    private List<Frame> frames;
    private String soundPath;
    private int id;
    private String name;
}
