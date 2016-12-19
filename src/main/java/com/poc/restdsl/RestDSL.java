package com.poc.restdsl;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.fasterxml.jackson.core.JsonParseException;
import com.poc.domain.Iphone;
import com.poc.domain.StudentRecord;
import com.poc.processors.DataMapper;

public class RestDSL extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		// JacksonDataFormat iphone = new JacksonDataFormat(Iphone.class);
		// iphone.setPrettyPrint(true);
		//
		// JacksonDataFormat response=new JacksonDataFormat();
		// response.setPrettyPrint(true);

		onException(JsonParseException.class).handled(true).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/text")).setBody().constant("Invalid Json data");
		
		restConfiguration().component("restlet").host("localhost").port(1212).bindingMode(RestBindingMode.auto)
				.dataFormatProperty("prettyPrint", "true");
		rest("/travel/").produces("application/json").consumes("application/json").put().type(Iphone.class)
				.to("direct:addStudents").outType(StudentRecord.class);
		from("direct:addStudents").log("Received data ${body}").removeHeaders("*")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200)).process(new DataMapper()).end();
	}
}
