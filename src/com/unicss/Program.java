package com.unicss;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Program {

    public static final String[] CHANNEL_NAME = new String[]{"hello_foo","hello_bar"};

    private static Logger logger = Logger.getLogger(Program.class);

    public static void main(String[] args) throws Exception {

        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        final JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379, 0);
        final Jedis subscriberJedis = jedisPool.getResource();
        final Subscriber subscriber = new Subscriber();

        new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                logger.info("Subscribing to \"commonChannel\". This thread will be blocked.");
                subscriberJedis.psubscribe(subscriber, "hello_*");
                logger.info("Subscription ended.");
            } catch (Exception e) {
                logger.error("Subscribing failed.", e);
            }
        }
    }).start();
    final Jedis publisherJedis = jedisPool.getResource();

    new Publisher(publisherJedis, CHANNEL_NAME).start();

    subscriber.punsubscribe();
    jedisPool.returnResource(subscriberJedis);
    jedisPool.returnResource(publisherJedis);
}
}