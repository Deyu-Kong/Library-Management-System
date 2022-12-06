package edu.whu.demo.service;

import edu.whu.demo.dao.BookJPARepository;
import edu.whu.demo.entity.BookItem;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<BookItem> findBooks(String bookName, Date publicationDate, String authorName, String publisherName) {
        //动态构造查询条件，name和complete不为null时作为条件
        Specification<BookItem> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (bookName != null) {
                predicateList.add(criteriaBuilder.like(root.get("bookName"), "%" + bookName + "%"));
            }
            if (publicationDate != null) {
                predicateList.add(criteriaBuilder.equal(root.get("publicationDate"), publicationDate));
            }
            if (authorName!=null){
                predicateList.add(criteriaBuilder.equal(root.get("authorName"),authorName));
            }
            if(publisherName!=null){
                predicateList.add(criteriaBuilder.equal(root.get("publisherName"),publisherName));
            }
            Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
            return criteriaBuilder.and(predicates);
        };

        List<BookItem> result = bookRepository.findAll(specification);
        return result;
    }

}
