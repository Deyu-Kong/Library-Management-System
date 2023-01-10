package edu.whu.demo.service;

import edu.whu.demo.dao.BookJPARepository;
import edu.whu.demo.dao.PaperJPARepository;
import edu.whu.demo.dao.QueryDAO;
import edu.whu.demo.dao.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孔德昱
 * @date 2023/1/7 20:01 星期六
 */
@Service
public class InfoService {
    @Autowired
    BookJPARepository bookRepository;

    @Autowired
    PaperJPARepository paperRepository;

    @Autowired
    UserJPARepository userRepository;

    @Autowired
    QueryDAO queryDAO;

    public Integer getBookCount() {
        return bookRepository.getBookCount();
    }

    public Integer getPaperCount() {
        return paperRepository.getPaperCount();
    }

    public Integer getUserCount() {
        return userRepository.getUserCount();
    }

    public Integer getBuyCount(String userName) {
        return queryDAO.getBuyCountByUserName(userName);
    }

    public Integer getUploadCount(String userName) {
        return queryDAO.getUploadCountByUserName(userName);
    }

    public Map<String, List<String>> getScoreDist() {
        List<Object[]> objects = queryDAO.getScoreDist();
        return handleBarData(objects);
    }

    public Map<String, String> getMaxBook() {
        Map<String, String> map = new HashMap<>();
        List<Object[]> objects = queryDAO.getMaxBook();
        List<String> name1 = new ArrayList<>();
        List<String> id1 = new ArrayList<>();
        List<String> mcnt1 = new ArrayList<>();
        for (Object[] object : objects) {
            name1.add(object[0].toString());
            id1.add(object[1].toString());
            mcnt1.add(object[2].toString());
        }

        map.put("mname", name1.get(0));
        map.put("mid", id1.get(0));
        map.put("mcnt", mcnt1.get(0));
        return map;
    }

    public List<Map<String, String>> getIdentityPie() {
        List<Object[]> objects = queryDAO.getIdentityPie();
        List<Map<String, String>> list = new ArrayList<>();
        for (Object[] object : objects) {
            Map<String, String> temp = new HashMap<>();
            temp.put("value", object[0].toString());
            temp.put("name", object[1].toString());
            list.add(temp);
        }
        return list;
    }

    public Map<String, List<String>> getUploaderRank() {
        List<Object[]> objects = queryDAO.getUploaderRank();
        return handleBarData(objects);
    }

    public Map<String, List<String>> getBuyBar(){
        List<Object[]> objects = queryDAO.getUserBuyBar();
        return handleBarData(objects);
    }

    private Map<String, List<String>> handleBarData(List<Object[]> objects){
        Map<String, List<String>> map = new HashMap<>();
        List<String> xAxisData = new ArrayList<>();
        List<String> seriesData = new ArrayList<>();
        for (Object[] object : objects) {
            seriesData.add(object[0].toString());
            xAxisData.add(object[1].toString());
        }
        map.put("xAxisData", xAxisData);
        map.put("seriesData", seriesData);
        return map;
    }


}
