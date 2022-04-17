package com.trade.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.trade.service.TradeService;

public class TaskScheduler {
	
	@Autowired
	 private TradeService service;
	
	private static final Logger log = LoggerFactory.getLogger(TaskScheduler.class);

	@Scheduled
	public void updateExpiryTask(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		log.info("The time is now {}", dateFormat.format(new Date()));
		service.updateTradesExpiry();
	}
}