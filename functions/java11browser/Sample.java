import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openqa.selenium.chrome.ChromeDriver;
import com.catalyst.browserlogic.SeleniumHandler;

import org.json.simple.JSONObject;

public class Sample implements SeleniumHandler {
	private static final Logger LOGGER = Logger.getLogger(Sample.class.getName());

	JSONObject responseData = new JSONObject();

	@Override
	@SuppressWarnings("unchecked")
	public void runner(HttpServletRequest request, HttpServletResponse response,ChromeDriver driver) throws Exception {
		try {
			//Fetches the endpoint and method to which the call was made
			driver.get("https://catalyst.zoho.com");
			responseData.put("message", "Title of the page "+driver.getTitle());

			//Sends the response back to the Client
			response.setContentType("application/json");
			response.getWriter().write(responseData.toString());
			response.setStatus(200);
		} catch (Exception e) {
		//The actions are logged. You can check the logs from Catalyst Logs.
			LOGGER.log(Level.SEVERE, "Exception in Sample", e);
			responseData.put("error", "Internal server error occurred. Please try again in some time.");
			response.getWriter().write(responseData.toString());
			response.setStatus(500);
		}

	}
}
