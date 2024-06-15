package com.dougstowell.training.toxiproxy.demo.model;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Event")
@RequiredArgsConstructor
public class Event implements Serializable {
    private String id;
    private String body;

    public Event(String id, String body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

