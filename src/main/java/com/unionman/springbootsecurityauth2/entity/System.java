package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class System implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    private int id;

    private int callTime;

    private int pttOnTime;

    private int pttSilentTime;

    private int gpsReportInterval;

    private int appHeartInterval;

    private int audioHeartInterval;

    private int tcpListenPort;

    private int appVoicePort;

    private String bdId;

    private int bdListenPort;

    private int bdVoicePort;

    private int gpsServerPort;

    private int dbSynPort;

    private String videoRecPath;

    private String wavRecPath;

    private String videoUrlPrefix;

    private String VAGWAddress;

    private int VAGWPort;
}
