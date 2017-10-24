package com.example.allan.github;

/**
 * Created by allan on 22-Oct-17.
 */

class SearchResult {
    private String name;
    //private int items_id;
    private String login;
    //private int ownersId;
    private String avatarURL;

    public SearchResult(String name, String login, String avatarURL) {
        this.name=name;
        //this.items_id = itemsId;
        this.login = login;
        //this.ownersId = ownersId;
        this.avatarURL = avatarURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public int getItems_id() {
        return items_id;
    }

    public void setItems_id(int items_id) {
        this.items_id = items_id;
    }
*/
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
/*
    public int getOwnersId() {
        return ownersId;
    }

    public void setOwnersId(int ownersId) {
        this.ownersId = ownersId;
    }
*/
    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
