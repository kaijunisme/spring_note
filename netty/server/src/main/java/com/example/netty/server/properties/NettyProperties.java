package com.example.netty.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyProperties {

    private String host;
    private int port;
    private int boss;
    private int worker;
    private int timeout;

}
