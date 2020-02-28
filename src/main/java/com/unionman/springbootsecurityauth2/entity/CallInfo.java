package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CallInfo implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    private int id;

    private String callId;

    private String callingId;

    private String calledId;

    private String pttId;

    private int callStatus;

    private int callType;

    private String startTime;

    private String endTime;

    private String endReason;

    private String filePath;

}
