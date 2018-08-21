package com.sun.web.service.impl;

import com.fasterxml.jackson.databind.util.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.ValidationException;

/**
 * 抽象服务类,提供通用模板方法、类,如验证器,实体工厂等
 *
 * @author
 */
public abstract class AbstractService<E extends Po, PK> implements CreateInstanceService<E>, Service {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Validator validator;

    protected EntityFactory entityFactory;

    @Autowired(required = false)
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Autowired(required = false)
    public void setEntityFactory(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    protected Class<E> entityType;

    protected Class<PK> primaryKeyType;

    @SuppressWarnings("unchecked")
    public AbstractService() {
        primaryKeyType = (Class<PK>) ClassUtil.getGenericType(this.getClass(), 1);
        entityType = (Class<E>) ClassUtil.getGenericType(this.getClass(), 0);
    }

    protected boolean entityFactoryIsEnabled() {
        if (entityFactory == null) {
            logger.warn("entityFactory is null!");
        }
        return null != entityFactory;
    }

    public Class<E> getEntityType() {
        return entityType;
    }

    protected Class<PK> getPrimaryKeyType() {
        return primaryKeyType;
    }

    @Override
    public <T> T createInstance(Class<T> clazz) {
        if (!entityFactoryIsEnabled()) {
            throw new UnsupportedOperationException("{unsupported_operation}");
        }
        return entityFactory.newInstance(clazz);
    }

    @Override
    public <E> Class<E> getInstanceType(Class<E> clazz) {
        return entityFactory.getInstanceType(clazz);
    }

    @Override
    public E createEntity() {
        return createInstance(getEntityType());
    }

    @Override
    public Class<E> getEntityInstanceType() {
        return getInstanceType(getEntityType());
    }

    protected <T> void tryValidateProperty(com.kedacom.ctsp.web.service.Validator<T> validator, String property, T value) {
        if (validator != null) {
            if (!validator.validate(value)) {
                throw new ValidationException(validator.getErrorMessage(), property);
            }
        }
    }

    protected <T> void tryValidateProperty(com.kedacom.ctsp.web.service.Validator<T> validator, String property, T value, String message) {
        if (validator != null) {
            if (!validator.validate(value)) {
                throw new ValidationException(message, property);
            }
        }
    }

    protected void tryValidateProperty(boolean success, String property, String message) {
        if (!success) {
            throw new ValidationException(message, property);
        }
    }

    protected void tryValidate(Object data, String property, Class... groups) {
        validate(() -> validator.validateProperty(data, property, groups));
    }

    protected void tryValidate(Class type, String property, Object value, Class... groups) {
        validate(() -> validator.validateValue(type, property, value, groups));
    }

    protected void tryValidate(Object data, Class... groups) {
        validate(() -> validator.validate(data, groups));
    }

    private <T> void validate(Supplier<Set<ConstraintViolation<T>>> validatorSetFunction) {
        if (validator == null) {
            logger.warn("validator is null!");
            return;
        }
        SimpleValidateResults results = new SimpleValidateResults();
        validatorSetFunction.get()
                .forEach(violation -> results.addResult(violation.getPropertyPath().toString(), violation.getMessage()));
        if (!results.isSuccess())
            throw new ValidationException(results);
    }

    public static void assertNotNull(Object data) {
        assertNotNull(data, "{data_not_found}");
    }

    public static void assertNotNull(Object data, String message) {
        if (null == data) throw new NotFoundException(message);
    }


}
