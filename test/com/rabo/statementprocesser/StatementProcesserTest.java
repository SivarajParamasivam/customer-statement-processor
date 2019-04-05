package com.rabo.statementprocesser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.rabo.statementprocesser.model.CustomerData;

public class StatementProcesserTest {

	
	@Test
	public void validateCustomerRecordsTest() {
		// initializing the variables
		List<CustomerData> customerRecords = new ArrayList<CustomerData>();
		List<CustomerData> invalidData = new ArrayList<CustomerData>();
		
		// creating 4 user data
		CustomerData CustomerData= new CustomerData(123,"251746","testdata",1,3,4);
		CustomerData CustomerData1= new CustomerData(23,"343333","Record1",4.2,4.0,5.3);
		CustomerData CustomerData2= new CustomerData(45,"22212","Record12",5.2,5.0,5.3);
		CustomerData CustomerData3= new CustomerData(23,"3456","Record14",5.2,5.0,5.3);
		
		// adding the customer data into the list
		customerRecords.add(CustomerData);
		customerRecords.add(CustomerData1);
		customerRecords.add(CustomerData2);
		customerRecords.add(CustomerData3);	
		
		// validating the validateCustomerRecords method
	    invalidData = StatementProcesser.validateCustomerRecords(customerRecords);	
	    
	    // asserting the given data
		 assertEquals( invalidData.size(), 3);
		
	}

}
