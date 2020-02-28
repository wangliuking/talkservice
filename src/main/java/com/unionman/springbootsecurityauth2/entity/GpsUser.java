package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GpsUser implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    private int gpsUserId;

    private String gpsUserPassword;

    private int oldGpsUserId;
}
