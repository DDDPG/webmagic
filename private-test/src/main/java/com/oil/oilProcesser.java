package com.oil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class oilProcesser implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0);

//    private List<HashMap<String, String>> info;
    private static List<HashMap<String, String>> infoList;

    @Override
    public void process(Page page) {
        Selectable items = page.getHtml().xpath("//table[@class='secondary_detail']/tbody/tr");

        List<String> otherpages = page.getHtml().xpath("//div[@id='pages']").links().all();
        List<String> dates = items.xpath("td[@class='last']/text()").all();
        List<String> titles = items.xpath("//div[@class='fl']/a/text()").all();
        List<String> companyInfo = items.xpath("td/text()").all();

        seperateItems(companyInfo, titles, dates);

        page.putField("WholeInfo", infoList);
//        page.putField("links", otherpages);
//        page.putField("dates", dates);
//        page.putField("titles", titles);
//        page.putField("companyInfo", companyInfo);
    }

    @Override
    public Site getSite() {
        return site;
    }

    private static void seperateItems(List<String> companyInfos, List<String> titles, List<String> dates) {

        if (!(dates.size() == titles.size() && titles.size() == companyInfos.size()))
        {
            throw new NumberFormatException("data length not matching.");
        }

            List<HashMap<String, String>> infos = oilProcesser.infoList;

            List<List<String>> separateInfos = new ArrayList<>();

        for (int i = 0; i < companyInfos.size(); i += 4) {
            int endIndex = Math.min(i + 4, companyInfos.size());
            List<String> separateInfo = companyInfos.subList(i, endIndex);
            separateInfos.add(separateInfo);
        }

        for (int index = 0; index < separateInfos.size(); index++) {
            HashMap<String, String> info = new HashMap<>();
            if (separateInfos.get(index).get(3).equals(dates.get(index)))
            {
                throw new NumberFormatException("data not matching");
            }

            info.put("title", titles.get(index));
            info.put("category", separateInfos.get(index).get(0));
            info.put("area", separateInfos.get(index).get(2));
            info.put("issueDate", separateInfos.get(index).get(3));
            infos.add(info);
        }


//        System.out.println(Arrays.toString(separateInfos.toArray()));
//        System.out.println(separateInfos.size());
//        System.out.println(titles.size());

    }

    public static void main(String[] args) {
        Spider.create(new oilProcesser())
                .addUrl("https://www.bibenet.com/zbggu999999u408u1.html")
                .thread(5)
                .addPipeline(new JsonFilePipeline("/Users/kangdiwang/Desktop"))
                .run();
    }
}
