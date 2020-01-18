package com.ihsan.entities.ids;

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

public class CharityBoxTransferIdGenerator implements IdentifierGenerator {

	private static final Logger logger = LoggerFactory.getLogger(CharityBoxTransferIdGenerator.class);

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {

		Connection connection = session.connection();

		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select max(TRANSFER_ID) from TM_CHARITY_BOX_TRANSFERS");

			if (rs.next()) {
				long id = rs.getLong(1);
				return new BigInteger(String.valueOf(id + 1));
			}
		} catch (SQLException e) {
			logger.error("Exception in CharityBoxTransferIdGenerator: ", e);
		}

		return null;
	}
}
