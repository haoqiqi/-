package com.prodemo01.dao.mapper;

import com.prodemo01.dao.po.BorrowedPO;
import java.util.List;

public interface BorrowedPOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BorrowedPO record);

    BorrowedPO selectByPrimaryKey(Long id);

    List<BorrowedPO> selectAll();

    int updateByPrimaryKey(BorrowedPO record);
}