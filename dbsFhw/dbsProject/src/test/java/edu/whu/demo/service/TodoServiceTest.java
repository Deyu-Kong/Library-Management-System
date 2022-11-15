package edu.whu.demo.service;

import edu.whu.demo.dao.TodoJPARepository;
import edu.whu.demo.entity.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TodoServiceTest {
    @Autowired
    TodoService todoService;

    @Autowired
    TodoJPARepository repository;

    @BeforeEach
    public void init(){
        repository.save(new TodoItem(1,"Test",false));
        repository.save(new TodoItem(2,"Test2",true));
    }

    @Test
    public void testAdd(){
        todoService.addTodo(new TodoItem(3,"Test3",true));
        Optional<TodoItem> result = repository.findById(3L);
        assertTrue(result.isPresent());
        assertEquals(3,result.get().getId());
    }

    @Test
    public void testQuery(){
        List<TodoItem> result = todoService.findTodos(null, null);
        assertNotNull(result);
        assertEquals(2,result.size());

        result = todoService.findTodos("Test", null);
        assertEquals(2,result.size());

        result = todoService.findTodos("Test", true);
        assertEquals(1,result.size());
    }

}
