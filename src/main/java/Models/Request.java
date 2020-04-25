package Models;

import java.util.ArrayList;

public class Request {
    private RequestType type;
    private RequestStatus status;
    private String requestId;
    private String details;

    public Request(RequestType type, RequestStatus status, String requestId, String details) {
        this.type = type;
        this.status = status;
        this.requestId = requestId;
        this.details = details;
    }

    public RequestType getType() {
        return type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getDetails() {
        return details;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
