package com.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rest.implementations.CountryCodeIdentification;
import com.rest.implementations.ServicesCallsMSG;
import com.rest.models.PhoneData;

@RestController
public class MCPController {
	private List<PhoneData> listCallsMSG = new ArrayList<>();
	private static List<Double> time = new ArrayList();
	private static int totalNumberOfFiles;
	CountryCodeIdentification cci = new CountryCodeIdentification();
	ServicesCallsMSG services = new ServicesCallsMSG();

	@GetMapping("/hello")
	public String getHello() {
		return "For testing purposes";
	}

	@GetMapping("read/{date}")
	public List<PhoneData> getCalls(@PathVariable("date") String dateS) {
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			listCallsMSG.addAll(services.openFile(dateS));
		} catch (Exception e) {
			e.printStackTrace();
		}
		totalNumberOfFiles++;

		watch.stop();
		time.add(watch.getTime() / 1000.00);

		return listCallsMSG;

	}

	@GetMapping("/metrics")
	public String getMetrics() {
		return services.getRowsWithMissingFields() + services.getMSGWithBlankContent()
				+ services.getRowsWithFieldErros() + cci.findCountryCode(ServicesCallsMSG.callList)
				+ services.getOKKOCalls() + cci.getAverageCallDuration() + services.getWordOccurrence();
	}

	@GetMapping("/kpis")
	public String getKpis() {
		String helpful = "";
		int counter = 0;
		for (Double number : time) {
			counter++;
			helpful += "Duration for " + counter + " JSON process is: " + number + " seconds." + "\n";
		}
		return "Total number of processed JSON files is: " + totalNumberOfFiles + "\n" + "Total number of rows is: "
				+ ServicesCallsMSG.totalNumberOfRows + "\n" + "Total number of calls is: "
				+ ServicesCallsMSG.totalNumberOfCalls + "\n" + "Total number of messages is: "
				+ ServicesCallsMSG.totalNumberOfMSG + "\n" + cci.getTotalOrigin() + cci.getTotalDestination() + helpful;
	}

}
