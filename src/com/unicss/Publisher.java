package com.unicss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * 发布者（监听器）
 * @author lenzhao
 * @email lenzhao@foxmail.com
 * @date2015-4-16 下午1:34:41
 */
public class Publisher {

    private static final Logger logger = Logger.getLogger(Publisher.class);

    private final Jedis publisherJedis;

    private final String[] channel;

    public Publisher(Jedis publisherJedis, String[] channel) {
        this.publisherJedis = publisherJedis;
        this.channel = channel;
    }

    public void start() {
        logger.info("Type your message (quit for terminate)");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String line = reader.readLine();
                if (!"quit".equals(line)) {
                	for(String ch : channel){
                		publisherJedis.publish(ch, line);
                	}
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            logger.error("IO failure while reading input, e");
        }
    }
}
