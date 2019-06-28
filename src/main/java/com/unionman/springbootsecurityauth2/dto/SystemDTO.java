package com.unionman.springbootsecurityauth2.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemDTO implements Serializable {
    private int callTime;
    private int pttOntime;
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
}
