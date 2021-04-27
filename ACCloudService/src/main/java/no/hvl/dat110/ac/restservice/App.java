package no.hvl.dat110.ac.restservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations describined in the project description

		post("/accessdevice/log", (req, res) -> {

			Gson gson = new GsonBuilder().create();

			AccessMessage accessMessage = gson.fromJson(req.body() , AccessMessage.class);
			int id = accesslog.add(accessMessage.getMessage());

			return gson.toJson(accesslog.get(id));

		});

		get("/accessdevice/log", (req, res) -> {

			return accesslog.toJson();

		});

		get("/accessdevice/log/:id", (req, res) -> {

			String temp = req.params(":id");

			int id = Integer.parseInt(temp);
			Gson gson = new Gson();

			return gson.toJson(accesslog.get(id));
		});

		put("/accessdevice/code/:one/:two", (req, res) -> {

			Gson gson = new Gson();

			String temp1 = req.params(":one");
			String temp2 = req.params(":two");

			int one = Integer.parseInt(temp1);
			int two = Integer.parseInt(temp2);

			int [] newCodes = {one, two};

			accesscode.setAccesscode(newCodes);

			return gson.toJson(accesscode);

		});

		get("/accessdevice/code", (req, res) -> {

			Gson gson = new Gson();

			return gson.toJson(accesscode);
		});

		delete("/accessdevice/log/", (req, res) -> {

			Gson gson = new Gson();
			accesslog.clear();

			return gson.toJson(accesslog);

		});
    }
    
}
