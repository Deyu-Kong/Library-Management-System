package edu.whu.demo.service;

import edu.whu.demo.dao.BookJPARepository;
import edu.whu.demo.dao.PaperJPARepository;
import edu.whu.demo.dao.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return -1;
    }

    public Integer getUploadCount(String userName){
        return -1;
    }
}
