package top.bootz.core.idgenerator;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.bootz.commons.snowflake.Snowflake;

@Component
public class CustomIdGenerator implements IdentifierGenerator, Configurable {

	@Autowired
	private Snowflake snowflake;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {

	}

	/**
	 * 如果id生成出错，降级为uuid
	 */
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Long id;
		try {
			id = snowflake.nextId();
		} catch (Exception e) {
			throw new HibernateException("An error occurred while generating a snowflake id.", e);
		}
		if (id != 0) {
			return id;
		}
		throw new HibernateException("Generated id is incorrect.");
	}

}
