package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BD implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    private String bdId;

    private String address;

    private int csPort;

    private int voicePort;

    private String bdName;

    private String oldBdId;
}
