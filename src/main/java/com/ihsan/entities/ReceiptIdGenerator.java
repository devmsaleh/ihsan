package com.ihsan.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiptIdGenerator implements IdentifierGenerator {

	private static final Logger logger = LoggerFactory.getLogger(ReceiptIdGenerator.class);

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {

		Connection connection = session.connection();

		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select max(RECEIPT_CODE) from TM_RECEIPTS");

			if (rs.next()) {
				long id = rs.getLong(1);
				logger.info("########## CUSTOM ID GENERATOR,id: " + id + " ###############");
				return new BigInteger(String.valueOf(id + 1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}