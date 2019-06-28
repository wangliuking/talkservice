package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "table_system")
public class SystemData implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    @Id
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
