package com.rest.implementations;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rest.models.Call;
import com.rest.models.PhoneData;

public class CountryCodeIdentification {
	// Parameters for getting average call duration
	private Map<String, Integer> callDurations = new LinkedHashMap();
	private int callDurFor34 = 0;
	private int counter34 = 0;
	private int callDurFor49 = 0;
	private int counter49 = 0;
	private int callDurFor44 = 0;
	private int counter44 = 0;
	private int callDurFor381 = 0;
	private int counter381 = 0;
	private int callDurFor420 = 0;
	private int counter420 = 0;
	private int callDurFor54 = 0;
	private int counter54 = 0;
	private int callDurFor61 = 0;
	private int counter61 = 0;

	// Idea was that from this site https://countrycode.org/ , I get all country codes and check them with required parameters. 

	public String findCountryCode(List<Call> listCall) {
		Map<String, Integer> map = new LinkedHashMap();
		String helpful = "Number of calls origin/destination grouped by country code is: " + "\n";

		for (Call c : listCall) {
			if (String.valueOf(c.getOrigin()).startsWith("34")) {
				Integer count = map.get("34");
				map.put("34", (count == null) ? 1 : count + 1);
				callDurFor34 += c.getDuration();
				counter34++;

			} else if (String.valueOf(c.getOrigin()).startsWith("49")) {
				Integer count = map.get("49");
				map.put("49", (count == null) ? 1 : count + 1);
				callDurFor49 += c.getDuration();
				counter49++;

			} else if (String.valueOf(c.getOrigin()).startsWith("44")) {
				Integer count = map.get("44");
				map.put("44", (count == null) ? 1 : count + 1);
				callDurFor44 += c.getDuration();
				counter44++;

			} else if (String.valueOf(c.getOrigin()).startsWith("381")) {
				Integer count = map.get("381");
				map.put("381", (count == null) ? 1 : count + 1);
				callDurFor381 += c.getDuration();
				counter381++;

			} else if (String.valueOf(c.getOrigin()).startsWith("420")) {
				Integer count = map.get("420");
				map.put("420", (count == null) ? 1 : count + 1);
				callDurFor420 += c.getDuration();
				counter420++;

			} else if (String.valueOf(c.getOrigin()).startsWith("54")) {
				Integer count = map.get("54");
				map.put("54", (count == null) ? 1 : count + 1);
				callDurFor54 += c.getDuration();
				counter54++;

			} else if (String.valueOf(c.getOrigin()).startsWith("61")) {
				Integer count = map.get("61");
				map.put("61", (count == null) ? 1 : count + 1);
				callDurFor61 += c.getDuration();
				counter61++;
			}
		}
		if (map.isEmpty()) {
			helpful = "There weren`t any calls" + "\n";
		} else {
			for (Map.Entry<String, Integer> entry : map.entrySet())
				helpful += " -total number of calls with prefix " + entry.getKey() + " is:  " + entry.getValue() + "\n";
		}
		ServicesCallsMSG.callList.clear();
		return helpful;
	}

	public String getAverageCallDuration() {
		String helpful = "Average call duration grouped by country code is: " + "\n";
		if (counter34 != 0) {
			callDurations.put("34", callDurFor34 /= counter34);
		}
		if (counter49 != 0) {
			callDurations.put("49", callDurFor49 /= counter49);
		}
		if (counter44 != 0) {
			callDurations.put("44", callDurFor44 /= counter44);
		}
		if (counter381 != 0) {
			callDurations.put("381", callDurFor381 /= counter381);
		}
		if (counter420 != 0) {
			callDurations.put("420", callDurFor420 /= counter420);
		}
		if (counter54 != 0) {
			callDurations.put("54", callDurFor54 /= counter54);
		}
		if (counter61 != 0) {
			callDurations.put("61", callDurFor61 /= counter61);
		}
		if (callDurations.isEmpty()) {
			helpful = "There weren`t any calls" + "\n";
		} else {
			for (Map.Entry<String, Integer> entry : callDurations.entrySet())
				helpful += " -average call duration for country code " + entry.getKey() + " is:  " + entry.getValue()
						+ "\n";
		}
		return helpful;
	}

	public String getTotalOrigin() {
		Set<Integer> originSet = new HashSet();
		for (PhoneData pd : ServicesCallsMSG.callsMSGTotal) {
			if (String.valueOf(pd.getOrigin()).startsWith("34")) {
				originSet.add(34);
			} else if (String.valueOf(pd.getOrigin()).startsWith("49")) {
				originSet.add(49);
			} else if (String.valueOf(pd.getOrigin()).startsWith("44")) {
				originSet.add(44);
			} else if (String.valueOf(pd.getOrigin()).startsWith("381")) {
				originSet.add(381);
			} else if (String.valueOf(pd.getDestination()).startsWith("420")) {
				originSet.add(420);
			} else if (String.valueOf(pd.getDestination()).startsWith("54")) {
				originSet.add(54);
			} else if (String.valueOf(pd.getDestination()).startsWith("61")) {
				originSet.add(61);
			}
		}
		return "Total number of different origin country codes is: " + originSet.size() + "\n";
	}

	public String getTotalDestination() {
		Set<Integer> destinationSet = new HashSet();
		for (PhoneData pd : ServicesCallsMSG.callsMSGTotal) {
			if (String.valueOf(pd.getDestination()).startsWith("34")) {
				destinationSet.add(34);
			} else if (String.valueOf(pd.getDestination()).startsWith("49")) {
				destinationSet.add(49);
			} else if (String.valueOf(pd.getDestination()).startsWith("44")) {
				destinationSet.add(44);
			} else if (String.valueOf(pd.getDestination()).startsWith("381")) {
				destinationSet.add(381);
			} else if (String.valueOf(pd.getDestination()).startsWith("420")) {
				destinationSet.add(420);
			} else if (String.valueOf(pd.getDestination()).startsWith("54")) {
				destinationSet.add(54);
			} else if (String.valueOf(pd.getDestination()).startsWith("61")) {
				destinationSet.add(61);
			}
		}
		return "Total number of different destination country codes is: " + destinationSet.size() + "\n";
	}
}
