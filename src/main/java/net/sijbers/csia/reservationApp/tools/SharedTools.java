package net.sijbers.csia.reservationApp.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SharedTools {
	private static final Logger logger = LoggerFactory.getLogger(SharedTools.class);

	public static String deserialiseObject(Object logObject) {
		ObjectMapper mapper = new ObjectMapper();
		String returnValue = "";
		try {
			returnValue = mapper.writeValueAsString(logObject);
			logger.debug(returnValue);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		return returnValue;
	}

	public static JSONObject getSimpleJson(String message) {
		JSONObject returnJSON = new JSONObject();
		try {
			returnJSON.put("status", message);
		} catch (JSONException e) {
			logger.error("JSON conversion failed");
		}
		return returnJSON;
	}

}
