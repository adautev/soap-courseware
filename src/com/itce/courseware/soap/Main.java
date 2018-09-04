package com.itce.courseware.soap;


import javax.xml.ws.Endpoint;

public class Main {

    public static void main(String[] args) {
        String url = "http://localhost:8080/weather";
        Endpoint.publish(url, new Weather());
        System.out.println("Service started @ " + url);
    }
}
