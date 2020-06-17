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

public class SubLocationIdGenerator implements IdentifierGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SubLocationIdGenerator.class);

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {

		Connection connection = session.connection();
		ResultSet rs = null;
		Statement statement = null;

		try {
			statement = connection.createStatement();

			rs = statement.executeQuery("select max(SUB_LOCATION_ID) from TM_SUB_LOCATIONS");

			if (rs.next()) {
				long id = rs.getLong(1);
				return new BigInteger(String.valueOf(id + 1));
			}
		} catch (SQLException e) {
			logger.error("Exception in SubLocationIdGenerator: ", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				logger.error("Exception in close resources >> SubLocationIdGenerator: ", e);
			}
		}

		return null;
	}
}
