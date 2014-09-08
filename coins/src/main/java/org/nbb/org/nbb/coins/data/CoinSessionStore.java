package org.nbb.org.nbb.coins.data;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.nbb.org.nbb.coins.model.Coin;

import java.io.Serializable;

@SuppressWarnings("serial")
@SessionScoped
@Named("CoinSessionStore")
public class CoinSessionStore implements Serializable {
	@Inject
	private Logger log;
	
	private Coin coin;

	public Coin getCoin() {
		return coin;
	}

	public void setCoin(Coin coin) {
		this.coin = coin;
	}

	@PostConstruct
	private void init() {
		log.info("init CoinSessionStore " + this);
	}
}
