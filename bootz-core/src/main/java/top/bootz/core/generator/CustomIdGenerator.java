package top.bootz.core.generator;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import top.bootz.commons.helper.SpringHelper;
import top.bootz.commons.snowflake.IdGenerator;

/**
 * 基于Hibernate的Id生成器，用于为实体bean生成分布式的基本有序的主键
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月20日 下午11:37:25
 */
public class CustomIdGenerator implements IdentifierGenerator, Configurable {

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
        // do nothing
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
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
