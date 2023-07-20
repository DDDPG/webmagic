package com.oil.pipline;

import com.oil.mapper.OilInfoDAO;
import com.oil.repos.OilRepo1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@PropertySource("classpath:/properties/filter.properties")
@Component("oilPipline")
public class OilPipline implements PageModelPipeline<OilRepo1> {

    @Resource
    private OilInfoDAO oilInfoDAO;

    private List<String> filters;

    public List<String> getFilters() {
        return filters;
    }

    @Value("#{'${oil.Filter}'.split(',')}")
    public void setFilters(List<String> filters) {
        this.filters = filters;
    }


    @Override
    public void process(OilRepo1 oilRepo, Task task) {
        try {
            for (String filter : filters) {
                if (oilRepo.getTitle().contains(filter)) {
                    oilInfoDAO.insertOilInfo(oilRepo);
                    System.out.println("Oil Matching!");
                    return;
                }
            }
            System.out.println("Not oil info: " + oilRepo.getTitle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        OilPipline oilPipline = context.getBean(OilPipline.class);

        System.out.println(Arrays.toString(oilPipline.getFilters().toArray()));
    }
}
