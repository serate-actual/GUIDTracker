import burp.api.montoya.proxy.http.*;
import com.sun.net.httpserver.Request;

import javax.swing.*;
import java.util.Arrays;
import java.util.Iterator;

public class RequestResponseHandler implements ProxyRequestHandler, ProxyResponseHandler {
    private GUIDTableModel dataModel;

    public RequestResponseHandler(GUIDTableModel dataModel){
        this.dataModel = dataModel;
    }

    @Override
    public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
        HighlightColor color = this.dataModel.checkForGUID(interceptedRequest);
        System.out.println(color);
        if (color != HighlightColor.NONE) {
            interceptedRequest.annotations().setHighlightColor(color.getBColor());
            System.out.println("HiGHLIOGHTINg");
            System.out.println(color);
        }
        return ProxyRequestReceivedAction.continueWith(interceptedRequest);
    }

    @Override
    public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest);
    }

    @Override
    public ProxyResponseReceivedAction handleResponseReceived(InterceptedResponse interceptedResponse) {
        return ProxyResponseReceivedAction.continueWith(interceptedResponse);
    }

    @Override
    public ProxyResponseToBeSentAction handleResponseToBeSent(InterceptedResponse interceptedResponse) {
        return ProxyResponseToBeSentAction.continueWith(interceptedResponse);
    }
}
