package com.prodemo01.dao.mapper;

import com.prodemo01.dao.po.BooksPO;
import com.prodemo01.dao.po.StudentsPO;

import java.util.List;

public interface BooksPOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BooksPO record);

    BooksPO selectByPrimaryKey(Long id);

    List<BooksPO> selectAll();

    int updateByPrimaryKey(BooksPO record);

    BooksPO selectByBookId(Long bookid);

    int updateBookState(BooksPO booksPO);
}