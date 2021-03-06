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

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.nbb.org.nbb.coins.model.Coin;

//http://en.wikibooks.org/wiki/Java_Persistence/Criteria

@ApplicationScoped
public class CoinJPARepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Coin findById(Long id) {
		return em.find(Coin.class, id);
	}

	public List<Coin> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Coin> criteria = cb.createQuery(Coin.class);
		Root<Coin> coin = criteria.from(Coin.class);
		criteria.select(cb.construct(Coin.class, coin.get("id"), coin.get("name"), coin.get("land"),coin.get("year"), coin.get("imageName")));
		criteria.orderBy(cb.asc(coin.get("name")));
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		//criteria.select(coin).orderBy(cb.asc(coin.get("name")));
		return em.createQuery(criteria).getResultList();
	}

}
