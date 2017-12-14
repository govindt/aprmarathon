package app.restapi;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.codehaus.jettison.json.JSONObject;

public class JsonConverter {

        //Filename
        InputStream jsonInputStream;

        //constructor
        public JsonConverter(InputStream jsonIStream){
            this.jsonInputStream = jsonIStream;
        }

        //Returns a json object from an input stream
        public JSONObject getJsonObject(){

           try {
               BufferedReader streamReader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"));
               StringBuilder responseStrBuilder = new StringBuilder();

               String inputStr;
               while ((inputStr = streamReader.readLine()) != null)
                   responseStrBuilder.append(inputStr);

               JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
               //returns the json object
               return jsonObject;

           } catch (Exception e) {
               e.printStackTrace();
           }
            return null;
        }
}