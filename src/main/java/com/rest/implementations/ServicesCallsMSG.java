package com.rest.implementations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.rest.models.Call;
import com.rest.models.MSG;
import com.rest.models.PhoneData;


public class ServicesCallsMSG {
	private List<PhoneData> callsMSG = new ArrayList<>();
	private String messageType;
	private long timestamp;
	private long origin;
	private long destination;
	// Only for Call
	private long callDuration;
	private String callStatusCode;
	private String callStatusDescription;
	// Only for MSG
	private String msgMessageContent;
	private String msgMessageStatus;
	// Parameters for metrics
	private int missingFields;
	private int msgWithBlankContent;
	private int rowsWithErros;
	private int okCalls;
	private int koCalls;
	private List<String> listMessageContent = new ArrayList();
	// For sending Call and MSG parameters
	public static List<Call> callList = new ArrayList();
	// Parameters for /kpis
	public static int totalNumberOfRows;
	public static int totalNumberOfCalls;
	public static int totalNumberOfMSG;
	public static List<PhoneData> callsMSGTotal = new ArrayList<>();
//String dateS
	public List<PhoneData> openFile(String dateS) throws Exception {
		List<JSONObject> jsonObject = new ArrayList<>();
		JSONObject obj;
		// The name of the file to open. I couldn`t read from file on GitHub(need a help
		// with that) so I put those files on my pc.
		String fileName = "C:/" + dateS + ".txt";
		//I`m getting this error ,,Unable to open file`` when I try to open file this way.
		//Network network = new Network();
		//String fileName=network.request("https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_20180131.json");
		String line = null;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				obj = (JSONObject) new JSONParser().parse(line);
				jsonObject.add(obj);
				Call call = new Call();
				MSG msg = new MSG();
				if (obj.get("message_type").equals("CALL")) {
					messageType = String.valueOf(obj.get("message_type"));
					if (obj.containsKey("timestamp")) {
						if (NumberUtils.isParsable(obj.get("timestamp").toString())) {
							timestamp = Long.parseLong(obj.get("timestamp").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("origin")) {
						if (NumberUtils.isParsable(obj.get("origin").toString())) {
							origin = Long.parseLong(obj.get("origin").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("destination")) {
						if (NumberUtils.isParsable(obj.get("destination").toString())) {
							destination = Long.parseLong(obj.get("destination").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}

					if (obj.containsKey("duration")) {
						if (NumberUtils.isParsable(obj.get("duration").toString())) {
							callDuration = Long.parseLong(obj.get("duration").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("status_code")) {
						callStatusCode = String.valueOf(obj.get("status_code"));
						if (obj.get("status_code").equals("OK")) {
							okCalls++;
						} else if (obj.get("status_code").equals("KO")) {
							koCalls++;
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("status_description")) {
						callStatusDescription = String.valueOf(obj.get("status_description"));
						if ((callStatusDescription.isEmpty()) || Objects.isNull(callStatusDescription)) {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}

					call.setMessage_type(messageType);
					call.setTimestamp(timestamp);
					call.setOrigin(origin);
					call.setDestination(destination);
					call.setDuration(callDuration);
					call.setStatus_code(callStatusCode);
					call.setStatus_description(callStatusDescription);

					totalNumberOfRows++;
					totalNumberOfCalls++;
					callsMSG.add(call);
					callList.add(call);
				} else if (obj.get("message_type").equals("MSG")) {
					messageType = String.valueOf(obj.get("message_type"));
					msg.setMessage_type((String) obj.get("message_type"));
					if (obj.containsKey("timestamp")) {
						if (NumberUtils.isParsable(obj.get("timestamp").toString())) {
							timestamp = Long.parseLong(obj.get("timestamp").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("origin")) {
						if (NumberUtils.isParsable(obj.get("origin").toString())) {
							origin = Long.parseLong(obj.get("origin").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("destination")) {
						if (NumberUtils.isParsable(obj.get("destination").toString())) {
							destination = Long.parseLong(obj.get("destination").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("message_content")) {
						if (obj.get("message_content").toString().isEmpty()) {
							msgWithBlankContent++;
							rowsWithErros++;
						} else {
							msgMessageContent = String.valueOf(obj.get("message_content"));
							listMessageContent.add((String) obj.get("message_content"));
							if (msgMessageContent == null) {
								rowsWithErros++;
							}
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("message_status")) {
						msgMessageStatus = String.valueOf(obj.get("message_status"));
						if ((msgMessageStatus.equals("DELIVERED")) || (msgMessageStatus.equals("SEEN"))) {
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}

					msg.setMessage_type(messageType);
					msg.setTimestamp(timestamp);
					msg.setOrigin(origin);
					msg.setDestination(destination);
					msg.setMessage_content(msgMessageContent);
					msg.setMessage_status(msgMessageStatus);

					totalNumberOfRows++;
					totalNumberOfMSG++;
					callsMSG.add(msg);
				} else if ((obj.containsKey("message_content") == true)
						|| (obj.containsKey("message_status") == true)) {
					if (obj.containsKey("message_type")) {
						messageType = String.valueOf(obj.get("message_type"));
						rowsWithErros++;
					} else {
						missingFields++;
					}
					if (obj.containsKey("timestamp")) {
						if (NumberUtils.isParsable(obj.get("timestamp").toString())) {
							timestamp = Long.parseLong(obj.get("timestamp").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("origin")) {
						if (NumberUtils.isParsable(obj.get("origin").toString())) {
							origin = Long.parseLong(obj.get("origin").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("destination")) {
						if (NumberUtils.isParsable(obj.get("destination").toString())) {
							destination = Long.parseLong(obj.get("destination").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("message_content")) {
						if (obj.get("message_content").toString().isEmpty()) {
							msgWithBlankContent++;
							rowsWithErros++;
						} else {
							msgMessageContent = String.valueOf(obj.get("message_content"));
							listMessageContent.add((String) obj.get("message_content"));
							if (msgMessageContent == null) {
								rowsWithErros++;
							}
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("message_status")) {
						msgMessageStatus = String.valueOf(obj.get("message_status"));
						if ((msgMessageStatus.equals("DELIVERED")) || (msgMessageStatus.equals("SEEN"))) {
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}

					msg.setMessage_type(messageType);
					msg.setTimestamp(timestamp);
					msg.setOrigin(origin);
					msg.setDestination(destination);
					msg.setMessage_content(msgMessageContent);
					msg.setMessage_status(msgMessageStatus);

					totalNumberOfRows++;
					totalNumberOfMSG++;
					callsMSG.add(msg);
				} else {
					if (obj.containsKey("message_type")) {
						rowsWithErros++;
						messageType = String.valueOf(obj.get("message_type"));
					} else {
						missingFields++;
					}
					if (obj.containsKey("timestamp")) {
						if (NumberUtils.isParsable(obj.get("timestamp").toString())) {
							timestamp = Long.parseLong(obj.get("timestamp").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("origin")) {
						if (NumberUtils.isParsable(obj.get("origin").toString())) {
							origin = Long.parseLong(obj.get("origin").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("destination")) {
						if (NumberUtils.isParsable(obj.get("destination").toString())) {
							destination = Long.parseLong(obj.get("destination").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}

					if (obj.containsKey("duration")) {
						if (NumberUtils.isParsable(obj.get("duration").toString())) {
							callDuration = Long.parseLong(obj.get("duration").toString());
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("status_code")) {
						callStatusCode = String.valueOf(obj.get("status_code"));
						if (obj.get("status_code").equals("OK")) {
							okCalls++;
						} else if (obj.get("status_code").equals("KO")) {
							koCalls++;
						} else {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}
					if (obj.containsKey("status_description")) {
						callStatusDescription = String.valueOf(obj.get("status_description"));
						if ((callStatusDescription.isEmpty()) || Objects.isNull(callStatusDescription)) {
							rowsWithErros++;
						}
					} else {
						missingFields++;
					}

					call.setMessage_type(messageType);
					call.setTimestamp(timestamp);
					call.setOrigin(origin);
					call.setDestination(destination);
					call.setDuration(callDuration);
					call.setStatus_code(callStatusCode);
					call.setStatus_description(callStatusDescription);

					totalNumberOfRows++;
					totalNumberOfCalls++;
					callsMSG.add(call);
					callList.add(call);
				}
			}

			// Closing stream.
			bufferedReader.close();

		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		callsMSGTotal.addAll(callsMSG);
		return callsMSG;
	}

	public String getRowsWithMissingFields() {
		return "Number of rows with missing fields is: " + missingFields + "\n";
	}

	public String getMSGWithBlankContent() {
		return "Number of messages with blank content is: " + msgWithBlankContent + "\n";
	}

	public String getRowsWithFieldErros() {
		return "Number of rows with field errors is: " + rowsWithErros + "\n";
	}

	public String getOKKOCalls() {
		return "Relationship between OK/KO calls, number of OK calls is: " + okCalls + " and number of KO calls is: "
				+ koCalls + "\n";
	}

	public String getWordOccurrence() {
		String helpful = "Word occurrence ranking for the given words in message_content field is: " + "\n";
		Map<String, Integer> mapWords = new LinkedHashMap();

		for (String content : listMessageContent) {
			Integer count = mapWords.get(content);
			mapWords.put(content, (count == null) ? 1 : count + 1);
		}

		if (mapWords.isEmpty()) {
			helpful = "There wasn`t any word in the message_content field." + "\n";
		} else {
			for (Map.Entry<String, Integer> entry : mapWords.entrySet())
				helpful += " -for word:  " + "\"" + entry.getKey() + "\"" + ", number of word occurrence is: "
						+ entry.getValue() + "\n";
		}
		return helpful;
	}
}
