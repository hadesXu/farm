package com.hades.farm.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p> bean工具类
 */
public final class BeanUtil {

    private final static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private BeanUtil() {

    }

    /**
     * 复制集合
     *
     * @param <E>
     * @param source
     * @param destinationClass
     * @return
     */
    public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass) {
        try {
            if (source.size() == 0) return Collections.emptyList();
            List<E> res = new ArrayList<E>(source.size());
            for (Object o : source) {
                E e = destinationClass.newInstance();
                BeanUtils.copyProperties(e, o);
                res.add(e);
            }
            return res;
        } catch (InstantiationException e) {
            logger.error("BeanUtil-exception", e);
        } catch (IllegalAccessException e) {
            logger.error("BeanUtil-exception", e);
        } catch (InvocationTargetException e) {
            logger.error("BeanUtil-exception", e);
        }
        return null;
    }

    public static <S, D> D copyBean(S source, Class<D> destinationClazz) {
        if (null == source)
            return null;

        try {
            D d = destinationClazz.newInstance();
            BeanUtils.copyProperties(d, source);
            return d;
        } catch (InstantiationException e) {
            logger.error("BeanUtil-exception", e);
        } catch (IllegalAccessException e) {
            logger.error("BeanUtil-exception", e);
        } catch (InvocationTargetException e) {
            logger.error("BeanUtil-exception", e);
        }
        return null;
    }

    /**
     * 复制到已存在的bean
     *
     * @param source
     * @param dest
     * @return
     */
    public static <S, D> D copyToExistBean(S source, D dest) {
        if (null == source || null == dest)
            return null;

        try {
            BeanUtils.copyProperties(dest, source);
            return dest;
        } catch (IllegalAccessException e) {
            logger.error("BeanUtil-exception", e);
        } catch (InvocationTargetException e) {
            logger.error("BeanUtil-exception", e);
        }
        return null;
    }

}
