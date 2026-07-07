package org.miniproject.integrationservice.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Order")
public class SapOrderRequest {

    @JacksonXmlProperty(localName = "Customer")
    private Long customer;

    @JacksonXmlProperty(localName = "Product")
    private Long product;

    @JacksonXmlProperty(localName = "Qty")
    private Integer qty;

}
