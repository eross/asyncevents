package com.evross.aa.sandbox.sqsreceiver.generator;

import com.evross.aa.sandbox.sqsreceiver.model.Temperature;
import com.evross.aa.sandbox.sqsreceiver.model.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.PostConstruct;

import static java.util.concurrent.TimeUnit.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TemperatureSensor {
	@Autowired
	private final ApplicationEventPublisher publisher;
	private final Random rnd = new Random();
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	public TemperatureSensor(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	
	@PostConstruct
	public void startProcessing() {
		this.executor.schedule(this::probe,  1,  SECONDS);
	}
	
	private void probe() {
		System.out.println("probe()");
		double temperature = 16 + rnd.nextGaussian() * 10;
		publisher.publishEvent(new Temperature(temperature));
		
		executor.schedule(this::probe, rnd.nextInt(5000), MILLISECONDS);
	}
	
}