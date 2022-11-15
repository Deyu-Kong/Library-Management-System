package edu.whu.demo.dao;

import edu.whu.demo.entity.TodoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author jiaxy
 * 待办事项的JpaRepository接口
 */
public interface TodoJPARepository extends JpaRepository<TodoItem, Long>,JpaSpecificationExecutor<TodoItem> {

}
