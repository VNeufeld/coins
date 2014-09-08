/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nbb.org.nbb.coins.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.nbb.org.nbb.coins.model.Coin;

@RequestScoped
public class CoinListProducer {


    @Inject
    private CoinJPARepository coinRepository;
    
    @Inject
    private CoinRepository coinJdbcRepository;

    
    @Inject
    private Logger log;


    private List<Coin> coins = new ArrayList<Coin>();

    @Produces
    @Named
    public List<Coin> getCoins() {
        return coins;
    }

    public void onCoinsListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Coin coin) {
    	retrieveAllCoinsOrderedByName();
    }

    
  
	@PostConstruct
    public void init() {
		log.info("PostConsructor init "+ this);
		retrieveAllCoinsOrderedByName();
    	
	}

    public void retrieveAllCoinsOrderedByName() {
    	coins = coinRepository.findAllOrderedByName();
		log.info("retrieveAllCoinsOrderedByName coins "+ coins.size()+ " instance : "+this);
		
//		for ( Coin coin : coins) {
////			coinJdbcRepository.setImage(coin);
//			if (coin.getImage() != null) {
//				coin.setImageName(new String(coin.getImage()));
//			}
//		}
    	
    }
}
