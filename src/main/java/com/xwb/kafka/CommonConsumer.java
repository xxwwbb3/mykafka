package com.xwb.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;
import kafka.message.MessageAndMetadata;

public class CommonConsumer {
	public static void main(String[] args) {
		// specify some consumer properties
		Properties props = new Properties();
		props.put("zookeeper.connect", "192.168.237.129:2181");
		props.put("zk.connectiontimeout.ms", "1000000");
		props.put("group.id", "test_group");

		// Create the connection to the cluster
		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> map=new HashMap<String,Integer>();
		map.put("test", 2);
		// create 4 partitions of the stream for topic “test”, to allow 4 threads to consume
		Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams = 
		    consumerConnector.createMessageStreams(map);
		List<KafkaStream<byte[], byte[]>> streams = topicMessageStreams.get("test");

		// create list of 4 threads to consume from each of the partitions 
		ExecutorService executor = Executors.newFixedThreadPool(4);

		// consume the messages in the threads
		for(final KafkaStream<byte[], byte[]> stream: streams) {
		  executor.submit(new Runnable() {
		    public void run() {
		      for(MessageAndMetadata<byte[], byte[]> msgAndMetadata: stream) {
		        // process message (msgAndMetadata.message())
		    	  System.out.println(msgAndMetadata.toString());
		      }	
		    }
		  });
		}
	}
}
