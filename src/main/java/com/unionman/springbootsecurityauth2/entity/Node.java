package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node {
    private int id;
    private int pId;
    private String label;
    private String fullName;
    private int level;
    private List<Node> children = new ArrayList<>();
}
