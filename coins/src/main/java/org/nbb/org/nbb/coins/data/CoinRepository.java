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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.NamingException;

import org.nbb.org.nbb.coins.model.Coin;

@ApplicationScoped
public class CoinRepository {

	@Inject
	private Logger log;

	@Resource(mappedName = "java:jboss/datasources/PGX_CoinDS")
	//@Resource(name="PGX_Coins" )  if use the name, a h2 jndi = TEST will be used ?? 
	private javax.sql.DataSource coinDB;

	public List<Coin> findAllOrderedByName() {

		log.info("findAllOrderedByName");
		Connection conn = null;
		PreparedStatement st = null;
		Statement statement = null;
		List<Coin> coins = new ArrayList<Coin>();

		try {

			conn = getDbConnection();

			String sqlSelect = "Select id, name, land, year From  Coins where id > ? order by name ";
			st = conn.prepareStatement(sqlSelect);
			st.setInt(1, 0);
			String ss = st.toString();
			log.info("ss = " + ss);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Coin coin = new Coin();
				coin.setId(rs.getLong("id"));
				coin.setName(rs.getString("name"));
				coin.setLand(rs.getString("land"));
				coin.setYear(rs.getInt("year"));
				coins.add(coin);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return coins;
	}
	
	public void saveImage(String image, long id) {
		log.info("saveImage");
		Connection conn = null;
		PreparedStatement st = null;

		try {
			
			String bs = image;
			byte[] bbs = bs.getBytes();

			conn = getDbConnection();

			String sqlSelect = "Update Coins Set image = ? where id =  ? ";
			st = conn.prepareStatement(sqlSelect);
			st.setLong(2, id);
			st.setBytes(1, bbs);
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
	}

	@PostConstruct
	private void init() {
		try {
			Connection conn = coinDB.getConnection();
			log.info("conn = " + conn.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Connection getDbConnection() throws NamingException, SQLException {
		try {
			Connection conn = coinDB.getConnection();
			log.info("conn = " + conn.getCatalog());
			return conn;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// private Connection getDbConnection() throws NamingException, SQLException
	// {
	// Context ic = new InitialContext();
	// DataSource ds = (DataSource) ic
	// .lookup("java:jboss/datasources/PG_CoinDS");
	// return ds.getConnection();
	// }

	public void add(Coin coin) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		Statement statement = null;
		try {

			conn = getDbConnection();

			String insert = "Insert into Coins ( name, land, year ) Values ( ?, ?, ? ) ";
			st = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, coin.getName());
			st.setString(2, coin.getLand());
			st.setInt(3, coin.getYear());
			st.execute();

			String sqlnextkey = "SELECT currval('" + "Coins" + "_id_seq')";
			// Connection connection = getConnection("assignIds");
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sqlnextkey);

			if (rs.next()) {
				int lastId = rs.getInt(1);
				coin.setId(Long.valueOf(lastId));

			}

			log.info("conn = " + conn.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setImage(Coin coin) {
		
		log.info("saveImage");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			
			conn = getDbConnection();

			String sqlSelect = "Select image From Coins where id =  ? ";
			st = conn.prepareStatement(sqlSelect);
			st.setLong(1, coin.getId());
			rs =  st.executeQuery();
			if ( rs.next()) {
				 byte[] imgBytes = rs.getBytes(1);
				 if (imgBytes != null && imgBytes.length > 0 )
					 coin.setImageName(new String(imgBytes));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
