package com.dougstowell.training.toxiproxy.demo;

import java.io.IOException;
import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import lombok.Getter;

public class ToxiProxyHelper {
    private final ToxiproxyClient client;

    @Getter
    private final Proxy localstackProxy;

    @Getter
    private final Proxy redisProxy;

    public ToxiProxyHelper() {
        this.client = new ToxiproxyClient();

        this.localstackProxy = createProxy("toxiproxy-demo_test_localstack_master",
                "localhost:4566", "demo-localstack:4566");
        this.redisProxy = createProxy("toxiproxy-demo_test_redis_master", "localhost:6379",
                "demo-redis:6379");
    }

    public void reset() {
        try {
            client.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Proxy createProxy(String name, String listenURl, String upStreamURL) {
        try {
            return client.createProxy(name, listenURl, upStreamURL);
        } catch (IOException e) {
            try {
                return client.getProxy(name);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
