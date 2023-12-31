import java.util.logging.Logger;

import org.json.JSONObject;

import com.catalyst.integ.CatalystIntegFunctionHandler;
import com.catalyst.integ.ZCIntegRequest;
import com.catalyst.integ.ZCIntegResponse;

public class Sample implements CatalystIntegFunctionHandler {
    Logger LOGGER = Logger.getLogger(Sample.class.getName());
    WelcomeHandler welcomeHandler = new WelcomeHandler();
    PromptHandler promptHandler = new PromptHandler();
    ExecuteHandler executeHandler = new ExecuteHandler();
    FallbackHandler fallbackHandler = new FallbackHandler();
    FailureHandler failureHandler = new FailureHandler();

    @Override
    public ZCIntegResponse runner(ZCIntegRequest req) throws Exception {
        JSONObject jsonResponse = new JSONObject();
        try {
            JSONObject reqBody = req.getRequestBody();
            String todo = (String) reqBody.get("todo");
            LOGGER.info(" TODO request " + todo);
            switch (todo) {
                case "welcome": {
                    jsonResponse = welcomeHandler.handleWelcomeRequest(reqBody);
                    break;
                }
                case "prompt": {
                    jsonResponse = promptHandler.handlePromptRequest(reqBody);
                    break;
                }
                case "execute": {
                    jsonResponse = executeHandler.handleExecuteRequest(reqBody);
                    break;
                }
                case "fallback": {
                    jsonResponse = fallbackHandler.handleFallbackRequest(reqBody);
                    break;
                }
                case "failure": {
                    jsonResponse = failureHandler.handleFailureRequest(reqBody);
                    break;
                }
                default: {
                	LOGGER.info(" default action " + reqBody.toString());
                    jsonResponse.put("message", "Error Trying to parse your details");
                }
            }
        } catch (Exception ex) {
            LOGGER.severe("Exception while executing handler. " + ex.toString());
            LOGGER.severe("REQUEST OBJECT ::: " + req);
            jsonResponse.put("message", "Error Trying to parse your details");
        }

        ZCIntegResponse response = new ZCIntegResponse();
        response.setResponseBody(jsonResponse);
        response.setContentType("application/json");
        response.setStatus(200);
        return response;
    }
}
