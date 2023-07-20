package com.oil;

import com.oil.repos.OilRepo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import com.oil.pipline.OilPipline;
import com.oil.repos.OilRepo;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;
import javax.management.JMException;

@Component
public class oilCrawler {
    @Qualifier("oilPipline")
    @Resource
    private PageModelPipeline oilPipline;

    private void crawl() throws JMException {
        OOSpider.create(Site.me().setSleepTime(500).setTimeOut(3000).setCycleRetryTimes(5)
                        , oilPipline, OilRepo1.class)
                        .addUrl("https://www.bibenet.com/zbggu1.html")
                        .thread(5).run();

    }

    public static void main(String[] args) throws JMException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        oilCrawler oilCrawler = applicationContext.getBean(oilCrawler.class);
        oilCrawler.crawl();
    }

}
