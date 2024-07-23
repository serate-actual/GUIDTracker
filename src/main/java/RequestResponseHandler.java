import burp.api.montoya.proxy.http.*;

public class RequestResponseHandler implements ProxyRequestHandler, ProxyResponseHandler {
    private GUIDTableModel dataModel;

    public RequestResponseHandler(GUIDTableModel dataModel){
        this.dataModel = dataModel;
    }

    @Override
    public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
        HighlightColor color = this.dataModel.searchAndGetGUIDcolor(interceptedRequest.toString());
        if (color != HighlightColor.NONE) {
            interceptedRequest.annotations().setHighlightColor(color.getBColor());
        }
        return ProxyRequestReceivedAction.continueWith(interceptedRequest);
    }

    @Override
    public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest);
    }

    @Override
    public ProxyResponseReceivedAction handleResponseReceived(InterceptedResponse interceptedResponse) {
        HighlightColor color = this.dataModel.searchAndGetGUIDcolor(interceptedResponse.toString());
        if (color != HighlightColor.NONE) {
            interceptedResponse.annotations().setHighlightColor(color.getBColor());
        }
        return ProxyResponseReceivedAction.continueWith(interceptedResponse);
    }

    @Override
    public ProxyResponseToBeSentAction handleResponseToBeSent(InterceptedResponse interceptedResponse) {
        return ProxyResponseToBeSentAction.continueWith(interceptedResponse);
    }
}
