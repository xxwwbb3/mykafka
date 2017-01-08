package com.xwb.kafka;

public interface KafkaProperties
{
    final static String zkConnect = "192.168.237.129:2181";
    final static String groupId = "kafkatest";
    final static String topic = "test";
    final static String kafkaServerURL = "192.168.237.129";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64 * 1024;
    final static int connectionTimeOut = 20000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "test1";
    final static String topic3 = "test2";
    final static String clientId = "SimpleConsumerDemoClient";
}