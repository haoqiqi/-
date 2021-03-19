package com.prodemo01.converter;


import com.prodemo01.dao.po.BorrowedPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class BorConverterService {
    public BorrowedPO request2po(Long stuid, Long bookid) {
        BorrowedPO borrowedPO = new BorrowedPO();
        borrowedPO.setBookid(bookid);
        borrowedPO.setStuid(stuid);
        borrowedPO.setValid(1);
        borrowedPO.setAddtime(new Date());
        borrowedPO.setUpdatetime(new Date());
        return borrowedPO;
    }

}
