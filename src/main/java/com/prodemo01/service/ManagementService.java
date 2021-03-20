package com.prodemo01.service;

import com.prodemo01.dao.mapper.BooksPOMapper;
import com.prodemo01.dao.mapper.BorrowedPOMapper;
import com.prodemo01.dao.mapper.StudentsPOMapper;
import com.prodemo01.dao.po.BooksPO;
import com.prodemo01.dao.po.BorrowedPO;
import com.prodemo01.dao.po.StudentsPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class ManagementService {


    @Resource
    private StudentsPOMapper studentsPOMapper;

    @Resource
    private BooksPOMapper booksPOMapper;

    @Resource
    private BorrowedPOMapper borrowedPOMapper;

    /**
     * 通过学生id查询学生信息
     * @param id
     * @return
     */
    public StudentsPO queryStuById(long id) {
        if (id <= 0) {
            log.info("[user] invalid param! id = {}", id);
            return null;
        }
        return studentsPOMapper.selectByStudentId(id);
    }

    /**
     * 通过书id查询书籍信息
     * @param id
     * @return
     */
    public BooksPO queryBookById(Long id) {
        if (id <= 0) {
            log.info("[user] invalid param! id = {}", id);
            return null;
        }
        return booksPOMapper.selectByBookId(id);
    }

    /**
     * 更新学生信息
     * @param studentsPO
     * @return
     */
    public boolean updateStuById(StudentsPO studentsPO) {

        studentsPOMapper.updateStuState(studentsPO);
        return true;
    }


    /**
     * 更新书籍信息
     * @param booksPO
     * @return
     */
    public boolean updateBookById(BooksPO booksPO) {
        booksPOMapper.updateBookState(booksPO);
        return true;
    }


    public boolean insertBorrowed(BorrowedPO borrowedPO) {
        borrowedPOMapper.insert(borrowedPO);
        return true;
    }

    public BorrowedPO selectBorrowedByBookId(Long bookid) {
        if (bookid <= 0) {
            log.info("[user] invalid param! id = {}", bookid);
            return null;
        }
        BorrowedPO borrowedPO = borrowedPOMapper.selectByBookId(bookid);
        return borrowedPO;
    }

    public boolean updateBorrowedByBookId(BorrowedPO borrowedPO) {
        borrowedPOMapper.updateBorrowedState(borrowedPO);
        return true;
    }
}
