package com.thukuma.livesoccer;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.thukuma.livesoccer.Modal.AwayFlagModal;
import com.thukuma.livesoccer.Modal.AwayNameModal;
import com.thukuma.livesoccer.Modal.FactoryModal;
import com.thukuma.livesoccer.Modal.HomeFlagModal;
import com.thukuma.livesoccer.Modal.HomeNameModal;
import com.thukuma.livesoccer.Modal.HrefModal;
import com.thukuma.livesoccer.Modal.LeagueModal;
import com.thukuma.livesoccer.Modal.LoadModal;
import com.thukuma.livesoccer.Modal.StatusModal;
import com.thukuma.livesoccer.Modal.StreamModal;
import com.thukuma.livesoccer.Modal.TimeModal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LiveScore {
    private Context context;
    private onComplete complete;
    private onError onError;
    private List<FactoryModal> list;
    private LoadModal modal;
    private List<StreamModal> streamModals;
    private List<TimeModal> timeModals;
    private List <LeagueModal> leagueModals;
    private List<HomeNameModal> homeNameModals;
    private List<HomeFlagModal> homeFlagModals;
    private List<AwayNameModal> awayNameModals;
    private List<AwayFlagModal> awayFlagModals;
    private List<HrefModal> hrefModals;
    private List<StatusModal> statusModals;
    private String status = "Uncoming";
    public static final String agent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.99 Safari/537.36";



    public LiveScore(Context context, onComplete complete, LiveScore.onError onError) {
        this.context = context;
        this.complete = complete;
        this.onError = onError;
        getOnline(complete,onError);
    }

    private void getOnline(onComplete complete, LiveScore.onError onError) {
        list = new ArrayList<>();
        streamModals= new ArrayList<>();
        timeModals = new ArrayList<>();
        leagueModals = new ArrayList<>();
        homeNameModals = new ArrayList<>();
        homeFlagModals = new ArrayList<>();
        awayFlagModals = new ArrayList<>();
        awayNameModals = new ArrayList<>();
        statusModals = new ArrayList<>();
        hrefModals = new ArrayList<>();
        AndroidNetworking.get("http://1.livesoccer.sx/?type=football&view=list")
                .addHeaders("User-agent", agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Document doc = Jsoup.parse(response);
                        Elements es = doc.select("div[class=item-row]");
                        Elements h= doc.select("li[class=bahamas]");

                        for (Element g:h){
                            String href = g.select("a[class=hover-this]").attr("href");
                            streamModals.add(new StreamModal(href));
                        }
                        for (Element e:es){
                            String href = e.select("div[class=matchname]").select("a").attr("href");
                            String league = e.select("span[style=color:#fbc02d;]").text();
                            if (!league.isEmpty()&!league.isEmpty()){
                                hrefModals.add(new HrefModal(href));
                                leagueModals.add(new LeagueModal(league));
                            }

                            Elements image = e.select("div[class=matchname]").select("img");
                            Elements titles = e.select("div[class=matchname]");
                            Elements time = e.select("span[class=gmt_m_time]");
                            String sta = e.select("div[class=item-col fixed pull-left item-col-stream]").text().replace("stream","").replace(" ","");
                            if (!sta.isEmpty()){
                                statusModals.add(new StatusModal(sta));
                            }


                            for(Element s:titles){
                                String home_name = s.select("td[style=width:46.5%;text-align:right;font-family: 'Titillium Web', sans-serif;;font-weight: 400;padding-right: 14px;]").text();
                                String away_name = s.select("td[style=width:46.5%;text-align:left;font-family: 'Titillium Web', sans-serif;font-weight: 400;padding-left: 14px;]").text();
                                homeNameModals.add(new HomeNameModal(home_name));
                                awayNameModals.add(new AwayNameModal(away_name));
                                String home_flag = "https://1.livesoccer.sx/"+s.select("td[style=width:46.5%;text-align:right;font-family: 'Titillium Web', sans-serif;;font-weight: 400;padding-right: 14px;] img").attr("src");
                                String away_flag = "https://1.livesoccer.sx/"+s.select("td[style=width:46.5%;text-align:left;font-family: 'Titillium Web', sans-serif;font-weight: 400;padding-left: 14px;] img").attr("src");
                                homeFlagModals.add(new HomeFlagModal(home_flag));
                                awayFlagModals.add(new AwayFlagModal(away_flag));

                            }
                            for (Element t :time){

                                String times = t.text();
                                if (!times.isEmpty()){
                                    timeModals.add(new TimeModal(times));
                                }
                            }
                            for (Element c:image){
                                String test  = "https://1.livesoccer.sx/"+c.attr("src");
                                list.add(new FactoryModal(test));
                            }

                        }

                        for (int i = 0;homeFlagModals.size()>i;i++){
                            modal = new LoadModal(
                                    leagueModals.get(i).getLeague(),
                                    timeModals.get(i).getTime(),
                                    homeNameModals.get(i).getName(),
                                    awayNameModals.get(i).getName().replace(leagueModals.get(i).getLeague(),""),
                                    homeFlagModals.get(i).getFlag(),
                                    awayFlagModals.get(i).getFlag(),
                                    hrefModals.get(i).getHref(),
                                    statusModals.get(i).getStatus(),
                                    streamModals.get(i).getLink()
                            );
                            complete.onComplete(modal);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        onError.onError(anError);
                    }
                });
    }


    public interface onComplete{
        void onComplete(LoadModal modal);
    }
    public interface onError{
        void  onError(Exception e);
    }
}
