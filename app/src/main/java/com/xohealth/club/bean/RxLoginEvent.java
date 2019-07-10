package com.xohealth.club.bean;

/**
 * Created by xulc on 2018/11/16.
 */

public class RxLoginEvent {
    private String eventMessage;

    public RxLoginEvent(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public String getEventMessage() {
        return eventMessage;
    }
}
