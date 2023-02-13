package com.markmolenmaker.dioninsanity.backend.payload.response.cluebingo;

public class BingoCardResponse {

    private String id;
    private String user;
    private String[] layout;

    public BingoCardResponse(String id, String user, String[] layout) {
        this.id = id;
        this.user = user;
        this.layout = layout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String[] getLayout() {
        return layout;
    }

    public void setLayout(String[] layout) {
        this.layout = layout;
    }

}
