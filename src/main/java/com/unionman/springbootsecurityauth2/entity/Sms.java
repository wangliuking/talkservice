package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Sms implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    private int id;

    private String srcId;

    private String tarId;

    private int type;

    private int contentType;

    private String size;

    private String content;

    private String sendTime;

    private String callId;

    private int status;

}
