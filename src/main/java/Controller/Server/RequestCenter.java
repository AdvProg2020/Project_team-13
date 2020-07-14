package Controller.Server;

import Models.*;
import Models.Product.Product;
import Models.UserAccount.Seller;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class RequestCenter {
    private static RequestCenter requestCenter;
    private ArrayList<Request> allRequests = new ArrayList<>();
    private String lastRequestID = "";

    private RequestCenter() {

    }

    public static RequestCenter getIncstance() {
        if(requestCenter == null){
            synchronized (RequestCenter.class) {
                if(requestCenter == null){
                    requestCenter = new RequestCenter();
                }
            }
        }
        return requestCenter;
    }

    public synchronized void addRequest(Request request) {
        allRequests.add(request);
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
    }

    public synchronized String makeRequestID() {
        lastRequestID = "@r" + (Integer.parseInt(lastRequestID.substring(2, 7)) + 1);
        DataBase.getInstance().replaceRequestId(lastRequestID);
        return lastRequestID;
    }

    public synchronized Request makeRequest(String type, String details) {
        switch (type) {
            case "AcceptSellerAccount":
                return new Request(RequestType.sellerRegister, RequestStatus.onReview, makeRequestID(), details);
            case "AddProduct":
                return new Request(RequestType.addProduct, RequestStatus.onReview, makeRequestID(), details);
            case "AddOffer":
                return new Request(RequestType.addOff, RequestStatus.onReview, makeRequestID(), details);
            case "AddAuction":
                return new Request(RequestType.addAuction, RequestStatus.onReview, makeRequestID(), details);
            case "EditOffer":
                return new Request(RequestType.editOff, RequestStatus.onReview, makeRequestID(), details);
            case "Commenting":
                return new Request(RequestType.commenting, RequestStatus.onReview, makeRequestID(), details);
            case "EditProduct":
                return new Request(RequestType.EditProduct, RequestStatus.onReview, makeRequestID(), details);
            case "deleteRequest":
                return new Request(RequestType.deleteProduct, RequestStatus.onReview, makeRequestID(), details);
            case "Commercial":
                return new Request(RequestType.commercial, RequestStatus.onReview, makeRequestID(), details);
            default:
                return null;
        }
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public synchronized void acceptRequest(String requestID, DataOutputStream dataOutputStream) {
        Request request = findRequestWithID(requestID);
        if (request.getType() == RequestType.sellerRegister) {
            acceptSellerRegisterRequest(request, dataOutputStream);
        } else if (request.getType() == RequestType.addProduct) {
            acceptAddProductRequest(request, dataOutputStream);
        } else if (request.getType().equals(RequestType.editOff)) {
            acceptEditOfferRequest(request, dataOutputStream);
        } else if (request.getType().equals(RequestType.addOff)) {
            acceptAddOfferRequest(request, dataOutputStream);
        } else if (request.getType().equals(RequestType.EditProduct)) {
            acceptEdiProductRequest(request, dataOutputStream);
        } else if (request.getType().equals(RequestType.commenting)) {
            acceptCommentRequest(request, dataOutputStream);
        } else if (request.getType().equals(RequestType.deleteProduct)) {
            acceptDeleteProductRequest(request, dataOutputStream);
        } else if (request.getType().equals(RequestType.commercial)) {
            acceptCommercialRequest(request, dataOutputStream);
        } else if (request.getType().equals(RequestType.addAuction)) {
            acceptAddAuctionRequest(request,dataOutputStream);
        }
    }

    public synchronized void acceptCommercialRequest(Request request, DataOutputStream dataOutputStream) {
        allRequests.remove(request);
        ProductCenter.getInstance().createProduct(new Gson().fromJson(request.getDetails(), Product.class));
        String arrayData = new Gson().toJson(allRequests);
        Product product = new Gson().fromJson(request.getDetails(), Product.class);
        DataBase.getInstance().updateAllRequests(arrayData);
        UserCenter.getIncstance().addCommercial(product, dataOutputStream);
    }

    public synchronized void acceptAddProductRequest(Request request, DataOutputStream dataOutputStream) {
        allRequests.remove(request);
        ProductCenter.getInstance().createProduct(new Gson().fromJson(request.getDetails(), Product.class));
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "request accepted successfully", dataOutputStream);
    }

    public synchronized void acceptAddAuctionRequest(Request request, DataOutputStream dataOutputStream) {
        allRequests.remove(request);
        AuctionCenter.getInstance().createAuction(new Gson().fromJson(request.getDetails(), Auction.class),dataOutputStream);
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "request accepted successfully", dataOutputStream);
    }

    public synchronized void acceptCommentRequest(Request request, DataOutputStream dataOutputStream) {
        allRequests.remove(request);
        ProductCenter.getInstance().addComment(new Gson().fromJson(request.getDetails(), Comment.class), dataOutputStream);
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "request accepted successfully", dataOutputStream);
    }

    public synchronized void acceptSellerRegisterRequest(Request request, DataOutputStream dataOutputStream) {
        if (UserCenter.getIncstance().canAcceptSellerRegister(new Gson().fromJson(request.getDetails(), Seller.class).getUsername(), dataOutputStream)) {
            allRequests.remove(request);
            String arrayData = new Gson().toJson(allRequests);
            DataBase.getInstance().updateAllRequests(arrayData);
            ServerController.getInstance().sendMessageToClient("@SuccessfulNotBack@" + "request accepted successfully", dataOutputStream);
        }
    }

    public Request findRequestWithID(String requestID) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestID)) {
                return request;
            }
        }
        return null;
    }

    public void setAllRequests(ArrayList<Request> allRequests) {
        this.allRequests = allRequests;
    }

    public void setLastRequestID(String lastRequestID) {
        this.lastRequestID = lastRequestID;
    }

    public synchronized void acceptEditOfferRequest(Request request, DataOutputStream dataOutputStream) {
        allRequests.remove(request);
        OffCenter.getInstance().editOffer(new Gson().fromJson(request.getDetails(), Offer.class));
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"), dataOutputStream);
    }

    public synchronized void acceptAddOfferRequest(Request request, DataOutputStream dataOutputStream) {
        allRequests.remove(request);
        OffCenter.getInstance().createNewOff(new Gson().fromJson(request.getDetails(), Offer.class), dataOutputStream);
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"), dataOutputStream);
    }

    public synchronized void acceptEdiProductRequest(Request request, DataOutputStream dataOutputStream) {
        Product product = new Gson().fromJson(request.getDetails(), Product.class);
        for (Product product1 : ProductCenter.getInstance().getAllProducts()) {
            if (product1.getProductId().equals(product.getProductId())) {
                allRequests.remove(request);
                ProductCenter.getInstance().editProduct(product);
                String arrayData = new Gson().toJson(allRequests);
                DataBase.getInstance().updateAllRequests(arrayData);
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"), dataOutputStream);
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no product with this Id."), dataOutputStream);
    }

    public synchronized void acceptDeleteProductRequest(Request request, DataOutputStream dataOutputStream) {
        Product product = new Gson().fromJson(request.getDetails(), Product.class);
        for (Product product1 : ProductCenter.getInstance().getAllProducts()) {
            if (product1.getProductId().equals(product.getProductId())) {
                allRequests.remove(request);
                ProductCenter.getInstance().deleteProduct(product.getProductId(), dataOutputStream);
                String arrayData = new Gson().toJson(allRequests);
                DataBase.getInstance().updateAllRequests(arrayData);
                ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request accept successfully"), dataOutputStream);
                return;
            }
        }
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("Error", "There is no product with this Id."), dataOutputStream);
    }

    public synchronized void declineRequest(String requestId, DataOutputStream dataOutputStream) {
        Request request = findRequestWithID(requestId);
        allRequests.remove(request);
        if (request.getType().equals(RequestType.addOff) || request.getType().equals(RequestType.editOff)) {
            OffCenter.getInstance().setProductStatusForOffer(new Gson().fromJson(request.getDetails(), Offer.class));
        }else if(request.getType().equals(RequestType.commercial)) {
            UserCenter.getIncstance().increaseSellerCreditForAnAdd(new Gson().fromJson(request.getDetails(),Product.class));
        }else if(request.getType().equals(RequestType.sellerRegister)) {
            Seller seller = new Gson().fromJson(request.getDetails(),Seller.class);
            UserCenter.getIncstance().removeSellerWithNoAlert(seller.getUsername(),dataOutputStream);
        }
        String arrayData = new Gson().toJson(allRequests);
        DataBase.getInstance().updateAllRequests(arrayData);
        ServerController.getInstance().sendMessageToClient(ServerMessageController.getInstance().makeMessage("SuccessfulNotBack", "Request declined successfully"), dataOutputStream);
    }
}
