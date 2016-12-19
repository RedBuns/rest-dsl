package com.poc.restdsl;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.restlet.data.MediaType;

import com.poc.domain.Iphone;
import com.poc.domain.StudentRecord;

public class RestDSL extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		JacksonDataFormat iphone = new JacksonDataFormat(Iphone.class);
        iphone.setPrettyPrint(true);

        JacksonDataFormat response=new JacksonDataFormat();
        response.setPrettyPrint(true);
        
        restConfiguration().component("restlet").host("localhost").port(1212)
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

        rest("/travel/")
                .produces(MediaType.APPLICATION_ALL_JSON.toString())
                .consumes(MediaType.APPLICATION_ALL_JSON.toString())
                .put().type(Iphone.class).to("direct:addStudents").outType(StudentRecord.class);//.type(Iphone.class).outType(StudentRecord.class);


        from("direct:addStudents")
                //.unmarshal(iphone)
                .log("Received data ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .to("log:myLog?showAll=true")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        StudentRecord studentRecord=new StudentRecord();
                        studentRecord.setName("Reji");
                        studentRecord.setAge(13);
                        exchange.getIn().setBody(studentRecord);
                    }
                })
                .end();

    }
	}

