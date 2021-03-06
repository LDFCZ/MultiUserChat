package ru.nsu.ccfit.lopatkin.client.GetRequests;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lopatkin.client.exceptions.SocketSendMessageException;
import ru.nsu.ccfit.lopatkin.client.utils.SocketHandler;

import java.util.List;

@Component
public class GetLogInRequestHandler implements GetRequest{

    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String BAD_ARGS = "Bad Args";
    public static final int MAX_COUNT = 2;
    public static final int NAME_ARG = 0;
    public static final int PASSWORD_ARG = 1;
    private SocketHandler socketHandler;

    private String name;
    private String password;

    @Autowired
    public GetLogInRequestHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void handleRequest() throws SocketSendMessageException {
        this.socketHandler.sendMessage(this.convertToJsonString());
    }

    @Override
    public String convertToJsonString() {
        JSONObject obj = new JSONObject();
        obj.put(TYPE, this.getType().getType());
        obj.put(NAME, name);
        obj.put(PASSWORD, password);
        return obj.toString();
    }

    @Override
    public void setState(List<String> args) {
        // args = {"name", "password"}
        if (args.size() != MAX_COUNT) throw new RuntimeException(BAD_ARGS);
        this.name = args.get(NAME_ARG);
        this.password = args.get(PASSWORD_ARG);
    }

    @Override
    public GetRequestType getType() {
        return GetRequestType.LOG_IN;
    }
}
