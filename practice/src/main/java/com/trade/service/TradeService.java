package com.trade.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.entity.Trade;
import com.trade.repo.TradeRepository;

@Service
public class TradeService {

	private static final Logger log = LoggerFactory
			.getLogger(TradeService.class);

	@Autowired
	TradeRepository tradeRepository;

	public boolean isValid(Trade trade) {
		if (validateMaturity(trade)) {
			Optional<Trade> exsitingTrade = tradeRepository.findById(trade
					.getTradeId());
			if (exsitingTrade.isPresent()) {
				return veriyVersion(trade, exsitingTrade.get());
			} else {
				return true;
			}
		}
		return false;
	}

	/*
	 * method validation for lower version
	 */
	private boolean veriyVersion(Trade trade, Trade oldTrade) {
		if (trade.getVersion() >= oldTrade.getVersion()) {
			return true;
		}
		return false;
	}

	/*
	 * Method to check trades maturity date
	 */
	private boolean validateMaturity(Trade trade) {
		return trade.getMaturityDate().isBefore(LocalDate.now()) ? false : true;
	}

	public void persistTrade(Trade trade) {
		trade.setCreatedDate(LocalDate.now());
		tradeRepository.save(trade);
	}

	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}

	public void updateTradesExpiry() {
		tradeRepository.findAll().stream().forEach(t -> {
			if (!validateMaturity(t)) {
				t.setExpiredFlag("Y");
				log.info("Trade which needs to updated {}", t);
				tradeRepository.save(t);
			}
		});
	}

}
