package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserBook implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    private int id;

    private String name;

    private List<String> userList;
}
