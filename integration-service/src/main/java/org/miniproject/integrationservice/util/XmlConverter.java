package org.miniproject.integrationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class XmlConverter {

    private final XmlMapper xmlMapper = new XmlMapper();

    // json 객체 -> xml
    public String toXml(Object object){

        try{

            return xmlMapper.writeValueAsString(object);

        }catch(JsonProcessingException e){

            throw new RuntimeException("XML 변환 실패",e);

        }

    }

    // xml -> json
    public <T> T fromXml(String xml, Class<T> clazz){

        try{

            return xmlMapper.readValue(xml,clazz);

        }catch(JsonProcessingException e){

            throw new RuntimeException("XML Parsing 실패",e);

        }

    }

}
