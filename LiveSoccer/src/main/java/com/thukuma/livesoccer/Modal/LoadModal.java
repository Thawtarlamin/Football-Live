package com.thukuma.livesoccer.Modal;

public class LoadModal {
    private String league,time,home_name,away_name,home_flag,away_flag,href,status,link;

    public LoadModal(String league, String time, String home_name, String away_name, String home_flag, String away_flag, String href, String status, String link) {
        this.league = league;
        this.time = time;
        this.home_name = home_name;
        this.away_name = away_name;
        this.home_flag = home_flag;
        this.away_flag = away_flag;
        this.href = href;
        this.status = status;
        this.link = link;
    }

    public String getLeague() {
        return league;
    }

    public String getTime() {
        return time;
    }

    public String getHome_name() {
        return home_name;
    }

    public String getAway_name() {
        return away_name;
    }

    public String getHome_flag() {
        return home_flag;
    }

    public String getAway_flag() {
        return away_flag;
    }

    public String getHref() {
        return href;
    }

    public String getStatus() {
        return status;
    }

    public String getLink() {
        return link;
    }
}
