package com.prodemo01.dao.mapper;

import com.prodemo01.dao.po.StudentsPO;
import java.util.List;


public interface StudentsPOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StudentsPO record);

    StudentsPO selectByPrimaryKey(Long id);

    List<StudentsPO> selectAll();

    int updateByPrimaryKey(StudentsPO record);

    StudentsPO selectByStudentId(Long stuId);


    int updateStuState(StudentsPO studentsPO);
}