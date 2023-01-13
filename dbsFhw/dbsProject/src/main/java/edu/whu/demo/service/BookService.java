package edu.whu.demo.service;

import edu.whu.demo.dao.BookJPARepository;
import edu.whu.demo.entity.BookItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiaxy
 * 待办事项业务服务类。
 */
@Service
public class BookService {
    @Autowired
    BookJPARepository bookRepository;

    public BookItem addBook(BookItem book) {
        return bookRepository.saveAndFlush(book);
    }

    public BookItem getBook(long id) {
        return bookRepository.getById(id);
    }


    public void updateBook(long id, BookItem book) {
        bookRepository.save(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public Page<BookItem> findBooks(String bookName, Date startDate, Date endDate, String authorName, String publisherName, Double ratingLow, Double ratingHigh, String imgUrl,
                                    Integer pNum, Integer pSize) {
        //动态构造查询条件，name和complete不为null时作为条件
        Specification<BookItem> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (bookName != null) {
                predicateList.add(criteriaBuilder.like(root.get("bookName"), "%" + bookName + "%"));
            }
            if (startDate != null && endDate != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publicationDate"), startDate));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("publicationDate"), endDate));
            }
            if (authorName!=null){
                predicateList.add(criteriaBuilder.like(root.get("authorName"),"%" + authorName + "%"));
            }
            if(publisherName!=null){
                predicateList.add(criteriaBuilder.like(root.get("publisherName"),"%" + publisherName + "%"));
            }
            if(ratingLow != null && ratingHigh !=null){
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"),ratingLow));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"),ratingHigh));
            }
            if(imgUrl != null){
                predicateList.add(criteriaBuilder.equal(root.get(imgUrl),imgUrl));
            }
            Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
            return criteriaBuilder.and(predicates);
        };
        if(pNum == null){
            pNum = 1;
        }
        if(pSize == null){
            pSize = 10;
        }
        PageRequest pageRequest = PageRequest.of(pNum - 1, pSize);
        Pageable pageable = (Pageable) pageRequest;
        Page<BookItem> result = bookRepository.findAll(specification, pageable);
        return result;
    }

}
