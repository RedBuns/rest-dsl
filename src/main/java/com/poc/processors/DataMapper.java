package com.poc.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.poc.domain.Iphone;
import com.poc.domain.StudentRecord;

public class DataMapper implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		Iphone iphone=exchange.getIn().getBody(Iphone.class);
		StudentRecord student=new StudentRecord();
		student.setName(iphone.getMenu().getItems().get(0).getId());
		student.setAge(01);
		exchange.getIn().setBody(student);
	}

}
