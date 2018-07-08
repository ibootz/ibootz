package top.bootz.core.generator;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import top.bootz.commons.helper.SpringHelper;
import top.bootz.commons.snowflake.IdGenerator;

public class CustomIdGenerator implements IdentifierGenerator, Configurable {

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
            IdGenerator idGenerator = SpringHelper.getBean("idGenerator");
            id = idGenerator.nextId();
        } catch (Exception e) {
            throw new HibernateException("", e);
        }
        if (id != 0) {
            return id;
        }
        throw new HibernateException("Generated id is incorrect.");
    }

}
