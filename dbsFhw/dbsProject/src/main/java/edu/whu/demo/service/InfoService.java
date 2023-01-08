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

    public Integer getUserCount(){
        return userRepository.getUserCount();
    }

    public Integer getBuyCount(String userName){
        return queryDAO.getBuyCountByUserName(userName);
    }

    public Integer getUploadCount(String userName){
        return queryDAO.getUploadCountByUserName(userName);
    }

    public Map<String, List<String>> getScoreDist(){
        Map<String, List<String>> map = new HashMap<>();
        List<Object[]> objects = queryDAO.getScoreDist();
        List<String> xAxisData = new ArrayList<>();
        List<String> seriesData = new ArrayList<>();
        for(Object[] object : objects){
            seriesData.add(object[0].toString());
            xAxisData.add(object[1].toString());
        }
        map.put("xAxisData",xAxisData);
        map.put("seriesData",seriesData);
        return map;
    }
}
