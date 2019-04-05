package com.rabo.statementprocesser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rabo.statementprocesser.model.CustomerData;

/**
 * @author Sivaraj.P
 *
 */
public class StatementProcesser {

	static String readFilePath = "C:\\customer-statements";
	static String writeFilePath = "C:\\customer-statements\\error-report\\";

	public static void main(String[] args) {
		try {
			File f = new File(readFilePath);
			for (File file : f.listFiles()) {
				if (file.isFile()) {
					List<CustomerData> customerRecords = null;
					List<CustomerData> customerInvalidRecords = null;
					if (file.getName().toLowerCase().endsWith(".xml")) {
						customerRecords = readXmlFile(file);
					} else if (file.getName().toLowerCase().endsWith(".csv")) {
						customerRecords = readCsvFile(file);
					}
					customerInvalidRecords = validateCustomerRecords(customerRecords); 
					
					if (customerInvalidRecords!= null && customerInvalidRecords.size() > 0) {
						String path = writeFilePath + file.getName() + "_error-report.csv";
						generateReportFile(path, customerInvalidRecords);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to read the CSV file  
	 * 
	 * @param filePath
	 * @return customerRecords
	 * @throws IOException
	 */
	public static List<CustomerData> readCsvFile(File filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = "";
		List<CustomerData> customerRecords = new ArrayList<CustomerData>();
		
		while ((line = br.readLine()) != null) {
			String[] country = line.split(",");
			if (!country[0].equals("Reference")) {
				CustomerData customerData = new CustomerData();
				customerData.setReference(Integer.parseInt(country[0]));
				customerData.setAccountNumber(country[1]);
				customerData.setDescription(country[2]);
				customerData.setStartBalance(Double.parseDouble(country[3]));
				customerData.setMutation(Double.parseDouble(country[4]));
				customerData.setEndBalance(Double.parseDouble(country[5]));
				customerRecords.add(customerData);
			}
			

		}
		return customerRecords;
	}

	/**
	 * This method is used to read XML file and then return the invalid records.
	 *
	 * @param filePath
	 * @return customerRecords
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<CustomerData> readXmlFile(File filePath) throws ParserConfigurationException, SAXException, IOException {

		List<CustomerData> customerRecords = new ArrayList<CustomerData>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(filePath);
		document.getDocumentElement().normalize();
		NodeList nodeList = document.getElementsByTagName("record");

		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node node = nodeList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element) node;
				CustomerData customerData = new CustomerData();
				customerData.setReference(Integer.parseInt(childElement.getAttribute("reference")));
				customerData
						.setAccountNumber(childElement.getElementsByTagName("accountNumber").item(0).getTextContent());
				customerData.setDescription(childElement.getElementsByTagName("description").item(0).getTextContent());
				customerData.setStartBalance(
						Double.parseDouble(childElement.getElementsByTagName("startBalance").item(0).getTextContent()));
				customerData.setMutation(
						Double.parseDouble(childElement.getElementsByTagName("mutation").item(0).getTextContent()));
				customerData.setEndBalance(
						Double.parseDouble(childElement.getElementsByTagName("endBalance").item(0).getTextContent()));
				customerRecords.add(customerData);

			}
		}
		return customerRecords;
	}

	
	/**
	 *  This method is used to validate the customer data.
	 * 
	 * @param customerRecords
	 * @return List of customerInvalidRecords
	 */
	public static List<CustomerData> validateCustomerRecords(List<CustomerData> customerRecords  ){
		Set<CustomerData> customerDataList = new HashSet<CustomerData>();
		List<CustomerData> customerInvalidRecords = new ArrayList<CustomerData>();
		for(CustomerData data:customerRecords){
			if (customerDataList.add(data) == true && 
					(data.getStartBalance() + data.getMutation() == data.getEndBalance())) {
				customerDataList.add(data);
			} else {
				customerInvalidRecords.add(data);
			}
		}
		return customerInvalidRecords;
	}

	/**
	 * @param fileName
	 * @param errorData
	 */
	public static void generateReportFile(String fileName, List<CustomerData> errorData) {
		FileWriter fWriter = null;
		try {
			String[] delimiter  ={"," , "\n"};
			fWriter = new FileWriter(fileName);
			// column headers  
			fWriter.append("Transaction reference");
			fWriter.append(delimiter[0]);
			fWriter.append("Description ");
			fWriter.append(delimiter[1]);
			for (CustomerData data : errorData) {
				fWriter.append(Integer.toString(data.getReference()));
				fWriter.append(delimiter[0]);
				fWriter.append(data.getDescription());
				fWriter.append(delimiter[1]);
			}
			 System.out.println("ReportFile CSV file is created...");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fWriter.flush();
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
