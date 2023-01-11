package edu.whu.demo.service;

import edu.whu.demo.dao.PaperJPARepository;
import edu.whu.demo.entity.PaperItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 孔德昱
 * @date 2022/12/8 11:30 星期四
 */
@Service
public class PaperService {
    @Autowired
    PaperJPARepository paperRepository;

    public PaperItem addPaper(PaperItem paper) {
        return paperRepository.saveAndFlush(paper);
    }

    public PaperItem getPaper(long id) {
        return paperRepository.getById(id);
    }


    public void updatePaper(long id, PaperItem paper) {
        paperRepository.save(paper);
    }

    public void deletePaper(long id) {
        paperRepository.deleteById(id);
    }

    public List<PaperItem> findPapers(String paperTitle, Date spaperDate, Date epaperDate,String paperAuthor, Long paperUploaderId, Date suploadDate,Date euploadDate) {
        //动态构造查询条件
        Specification<PaperItem> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (paperTitle != null) {
                predicateList.add(criteriaBuilder.like(root.get("paperTitle"), "%" + paperTitle + "%"));
            }
            if (spaperDate != null&& epaperDate != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("paperDate"), spaperDate));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("paperDate"), epaperDate));
            }
            if (paperAuthor!=null){
                predicateList.add(criteriaBuilder.like(root.get("paperAuthor"),"%"+paperAuthor + "%"));
            }
            if(paperUploaderId!=null){
                predicateList.add(criteriaBuilder.equal(root.get("paperUploaderId"),paperUploaderId));
            }
            if(suploadDate!=null&&euploadDate!=null){
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("uploadDate"), suploadDate));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("uploadDate"), euploadDate));
            }
            Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
            return criteriaBuilder.and(predicates);
        };

        List<PaperItem> result = paperRepository.findAll(specification);
        return result;
    }

}
