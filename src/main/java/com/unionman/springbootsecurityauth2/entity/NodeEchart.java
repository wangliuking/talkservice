package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NodeEchart {
    private int id;
    private int pId;
    private String name;
    private int level;
    private List<NodeEchart> children = new ArrayList<>();
}
