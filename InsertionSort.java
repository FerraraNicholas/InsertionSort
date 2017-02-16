package csi403;


// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.json.*;
import java.util.List;
import java.util.Arrays;
import java.util.*;




// Extend HttpServlet class
public class ReverseList extends HttpServlet {

  // Standard servlet method 
  public void init() throws ServletException
  {
      // Do any required initialization here - likely none
  }

  // Standard servlet method - we will handle a POST operation
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      doService(request, response); 
  }

  // Standard servlet method - we will not respond to GET
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type and return an error message
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.println("{ 'message' : 'Use POST!'}");
  }

  // Our main worker method
  // Parses messages e.g. {"inList" : [5, 32, 3, 12]}
  // Returns the list reversed.   
  private void doService(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Get received JSON data from HTTP request
      BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
      String jsonStr = "";
      if(br != null){
          jsonStr = br.readLine();
      }
      
      // Create JsonReader object
      StringReader strReader = new StringReader(jsonStr);
      JsonReader reader = Json.createReader(strReader);

      // Get the singular JSON object (name:value pair) in this message.    
      JsonObject obj = reader.readObject();
       //Json object that holds the output of the sort
      JsonArrayBuilder outArrayBuilder = Json.createArrayBuilder();
      //From the object get the array named "inList"
      JsonArray inArray = obj.getJsonArray("inList");
      
      int arrLen = inArray.size();
      int[] arr = new int[arrLen];
      int temp;
      long sortBegin = 0;
      long sortEnd = 0;
      long timeSorted = 0;

      String errorCheckString = inArray.toString();
      char c;
      boolean validJson = false;
      int strLen = errorCheckString.length();

      for (int t = 0; t < errorCheckString.length(); t++){
      	c = errorCheckString.charAt(t);

      	if (c >= '0' && c <=9 ) {
      		validJson = true;
      	}

     	if (c == '[' || c == ',' || c == ']' || c == '-') {
      		validJson = true;
      			
      }


      	}

 


/*
      if (errorCheckString.endsWith(",")) {
      	validJson = false;	
      }*/



      if (validJson == true) {

      //Begin time on insertion sort
      sortBegin = System.currentTimeMillis();
      //Take json arry and change to java array
      for (int j = 0; j < inArray.size(); j++) {
          arr[j] = inArray.getInt(j); 
      }

      //Insertion sort alogorithm
      for (int k = 0; k < arr.length; k++) {
      	for (int l = 0; l < arr.length; l++) {
      		if (arr[k] < arr[l]) {
      			temp = arr[l];
      			arr[l] = arr[k];
      			arr[k] = temp;
      		}	
      	}
      }
      // Java array back to JSON array
      for (int i = 0; i < arr.length; i++) {
          outArrayBuilder.add(arr[i]); 
      }
      //End time on sort
      sortEnd = System.currentTimeMillis();
      // Total time that it took to sort the JSON
      timeSorted = sortEnd - sortBegin;
      //Set response content type to be JSON
      response.setContentType("application/json");
      //Send back the response JSON message
      PrintWriter out = response.getWriter();
	  out.print("{ " + "\"outList\" : " + outArrayBuilder.build().toString() + ",");
	  out.print("\"Algorithm\" : " + "\"Insertion Sort\"" + ",");
	  out.print("\"timeMS\" : " + "\""+ timeSorted + "\"" + "}");
      	
      }
      
      if(validJson == false){
      int testTime = 4;
      response.setContentType("application/json");
      //Send back the response JSON message
      PrintWriter out = response.getWriter();
	  out.print("{ " + "\"outList\" : " + outArrayBuilder.build().toString() + ",");
	  out.print("\"Algorithm\" : " + "\"Insertion Sort\"" + ",");
	  out.print("\"timeMS\" : " + "\""+ testTime + "\"" + "}");

      }

  }


    
  // Standard Servlet method
  public void destroy()
  {
      // Do any required tear-down here, likely nothing.
  }
}

