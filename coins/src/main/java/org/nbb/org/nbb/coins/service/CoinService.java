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
package org.nbb.org.nbb.coins.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.nbb.org.nbb.coins.data.CoinJPARepository;
import org.nbb.org.nbb.coins.model.Coin;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class CoinService {

    @Inject
    private Logger log;
    
    @Inject
    private CoinJPARepository coinRepository;


    @Inject
    private EntityManager em;

    @Inject
    private Event<Coin> coinEventSrc;
    
    public void save(Coin coin) throws Exception {
    	if ( coin.getId() != null ) {
	        log.info("Save Coin id = "+coin.getId()+ " name " + coin.getName());
	        em.merge(coin);
    	}
    	else {
            log.info("Add Coin " + coin.getName());
            em.persist(coin);
    	}
        coinEventSrc.fire(coin);
    }

    public void delete(Coin coin) throws Exception {
        log.info("Delete Coin " + coin.getName());
        Coin coinf = em.find(Coin.class, coin.getId());
        if ( coinf != null)
        	em.remove(coinf);
        coinEventSrc.fire(coin);
    }
    
    public void saveImage(Coin coin) throws Exception {
        log.info("saveImage Coin " + coin.getImage());
        //coinRepository.saveImage(coin.getImageName(), coin.getId());
        coinEventSrc.fire(coin);
    }

	public byte[] readImage(Long id) {
		Coin coin = coinRepository.findById(id);
		if ( coin != null)
			return coin.getImage();
		return null;
		
	}
    
    
}
