package com.unicss;

import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPubSub;

/**
 * 订阅者（监听器）
 * @author lenzhao
 * @email lenzhao@foxmail.com
 * @date2015-4-16 下午1:34:28
 */
public class Subscriber extends JedisPubSub {

    private static Logger logger = Logger.getLogger(Subscriber.class);

    @Override
    public void onMessage(String channel, String message) {
        logger.info(String.format("onMessage::Message received. Channel: %s, Msg: %s", channel, message));
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
    	logger.info(String.format("onPMessage::Message received. Channel: %s, Msg: %s", channel, message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }
}
