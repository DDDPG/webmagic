package com.oil.repos;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.io.Serializable;
import java.util.List;

@TargetUrl("https://www.bibenet.com/zbggu[0-9]+.html")
//@HelpUrl("https://www.bibenet.com/zbggu[0-9]+.html")
@ExtractBy(value = "//table[@class='secondary_detail']/tbody/tr", multi = true)
public class OilRepo1 implements AfterExtractor, Serializable {
    @ExtractBy(value = "//div[@class='fl']/a/text()", notNull = true)
    private String title;
    @ExtractBy("//div[@class='fl']/a/@href")
    private String url;

    @ExtractByUrl("(.*)")
    private String sourceUrl;

    @ExtractBy(value = "/html/body/div[4]/div[2]/div[1]/div[1]/table/tbody/tr[2]/td[3]/text()", source = ExtractBy.Source.RawHtml)
    private String area;

//    @ExtractBy("//div[@class='detail_table_sign_bigbox']/span/text()")
//    private List<String> tags;
//    @Formatter("yyyy-MM-dd")
    @ExtractBy("td[@class='last']/text()")
    private String date;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

//    public List<String> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<String> tags) {
//        this.tags = tags;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OilRepo1{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", area='" + area + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public void afterProcess(Page page) {
//        this.setTags(tags.subList(2, tags.size()-1).size() == 0? null: tags.subList(2, tags.size()-1));
//        this.setArea(area.substring(0, area.length()-3));
        page.addTargetRequests(page.getHtml().links().regex("https://www.bibenet.com/zbggu[0-9]+.html").all());
    }

    public static void main(String[] args) {
        OOSpider.create(Site.me().setSleepTime(1000),
                        new ConsolePageModelPipeline(), OilRepo1.class)
                .addUrl("https://www.bibenet.com/zbggu1.html")
                .thread(5)
                .run();
    }
}
