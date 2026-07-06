package org.miniproject.mocksap.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "Order")
public class OrderRequestXml {
    @JacksonXmlProperty(localName = "Customer")
    private Long customer;

    @JacksonXmlProperty(localName = "Product")
    private Long product;

    @JacksonXmlProperty(localName = "Qty")
    private Integer qty;
}
