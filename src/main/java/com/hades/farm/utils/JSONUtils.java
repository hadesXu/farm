package com.hades.farm.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>JSON工具类，提供对象和字符串互相转换等功能
 * <p>
 */
public final class JSONUtils {

    private final static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * 禁止实例化
     */
    private JSONUtils() {
    }

    /**
     * 将对象转换成String
     *
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    public static String objectToString(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(o);
    }

    /**
     * 将对象转换成String
     *
     * @param o
     * @return
     */
    public static String objectToStringNonEx(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(o);
        }catch (JsonProcessingException e){
            logger.error("objectToStringNonEx exception", e);
        }
        return null;
    }

    /**
     * 从文件读取json对象
     *
     * @param file
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fileToJSON(String file, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(file), clazz);
    }

    public static <T> List<T> fileToJSONObjectLists(String file, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        return mapper.readValue(new File(file), typeFactory.constructCollectionType(List.class, clazz));
    }

    /**
     * 从流读取json
     *
     * @param inputStream
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T streamToJSON(InputStream inputStream, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, clazz);
    }

    /**
     * 从流读取json
     *
     * @param inputStream
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> streamToJSONList(InputStream inputStream, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        return mapper.readValue(inputStream, typeFactory.constructCollectionType(List.class, clazz));
    }


    /**
     * 将JSON string转换成JSON对象
     *
     * @param target
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> T stringToObject(String target, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(target, clazz);
    }

    /**
     * 将JSON string转换成JSON对象
     *
     * @param target
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> T stringToObjectNonEx(String target, Class<T> clazz){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(target, clazz);
        }catch (IOException e){
            logger.error("stringToObjectNonEx exception", e);
        }
        return null;
    }

    /**
     * 将JSON string转换成JSON对象
     *
     * @param target
     * @return
     * @throws IOException
     */
    public static <T> T stringToObjectForGeneric(String target, TypeReference<T> reference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(target, reference);
    }


    public static <T> List<T> stringToObjectLists(String target, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        return mapper.readValue(target, typeFactory.constructCollectionType(List.class, clazz));
    }

    public static <T> List<T> stringToObjectListsNoEx(String target, Class<T> clazz)  {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        try {
            return mapper.readValue(target, typeFactory.constructCollectionType(List.class, clazz));
        }catch (Exception e){
            logger.error("stringToObjectListsNoEx exception", e);
        }
        return null;

    }

}
