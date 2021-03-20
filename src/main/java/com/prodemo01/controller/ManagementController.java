package com.prodemo01.controller;

import com.prodemo01.converter.BorConverterService;
import com.prodemo01.dao.po.BooksPO;
import com.prodemo01.dao.po.BorrowedPO;
import com.prodemo01.dao.po.StudentsPO;
import com.prodemo01.datatype.base.ResultVO;
import com.prodemo01.service.ManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Slf4j
//@Api(tags = "借还书的接口")
@RequestMapping("/management")
public class ManagementController {



    @Autowired
    private ManagementService managementService;

    @Autowired
    private BorConverterService borConverterService;


    /**
     *
     * @param stuid
     * @param bookid
     * @return
     */
    @RequestMapping(path = "/queryDetail", method = RequestMethod.GET)
    public ResultVO<Integer> queryDetail(@RequestParam("stuid") Long stuid,
                                         @RequestParam("bookid") Long bookid) {
        try {
            // 查询学生
            if(stuid == null || stuid <= 0) {
                log.info("[user] invalid param! id = {}", stuid);
                return ResultVO.failed("非法的参数!");
            }

            StudentsPO studentsPO = managementService.queryStuById(stuid);
            if(studentsPO == null) {
                log.info("[user] studentService.queryById empty! id = {}", stuid);
                return ResultVO.failed("未找到该用户");
            }

            // 判断学生的借书状态
            Integer stustate = studentsPO.getStustate();
            if(stustate == 2) {
                return ResultVO.failed("只能借一本");
            } else if (stustate == 3) {
                return ResultVO.failed("异常状态");
            } else {
                log.info("可以借书");


                // 查询书籍
                if(bookid == null || bookid <= 0) {
                    log.info("[user] invalid param! id = {}", bookid);
                    return ResultVO.failed("非法的参数!");
                }

                BooksPO booksPO = managementService.queryBookById(bookid);
                if(booksPO == null) {
                    log.info("[user] studentService.queryById empty! id = {}", bookid);
                    return ResultVO.failed("未找到该书籍");
                }
                Integer state = booksPO.getState();
                if(state == 2) {
                    return ResultVO.failed("书已被借走");
                } else if (state == 3) {
                    return ResultVO.failed("异常状态");
                } else {
                    log.info("借书成功");

//                    // 判断是否有记录，若有就覆盖
//                    BorrowedPO borrowedPO1 = managementService.selectBorrowedByBookId(bookid);
//                    if(borrowedPO1 != null
//                            && bookid.equals(borrowedPO1.getBookid())) {
//                        log.info("覆盖原纪录");
//
//                        borrowedPO1.setStuid(stuid);
//                        borrowedPO1.setValid(1);
//                        borrowedPO1.setAddtime(new Date());
//                        borrowedPO1.setUpdatetime(new Date());
//                        if (managementService.updateBorrowedByBookId(borrowedPO1)){
//                            log.info("覆盖成功");
//                        }
//                        Long stuid1 = borrowedPO1.getStuid();
//
//                    } else {// 若没有，则创建新的
//                        // 修改学生的状态与更新时间
//                        log.info("新建纪录");
//                        studentsPO.setStustate(2);
//                        studentsPO.setUpdatetime(new Date());
//                        if(managementService.updateStuById(studentsPO)){
//                            log.info("学生状态更新成功");
//                        } else {
//                            return ResultVO.failed("学生状态更新失败");
//                        }
//
//                        // 修改书籍的状态、被借次数与更新时间
//                        booksPO.setState(2);
//                        Integer count = booksPO.getCount();
//                        booksPO.setCount(count+1);
//                        booksPO.setUpdatetime(new Date());
//                        if(managementService.updateBookById(booksPO)){
//                            log.info("书籍状态更新成功");
//                        } else {
//                            return ResultVO.failed("书籍状态更新失败");
//                        }
//
//                        // 将借书信息插入借书表
//
//                        // 创建借书对象
//                        BorrowedPO borrowedPO = borConverterService.request2po(stuid, bookid);
//                        // 插入
//                        if(managementService.insertBorrowed(borrowedPO)) {
//                            log.info("插入借书表成功");
//                        } else {
//                            return ResultVO.failed("插入借书表失败");
//                        }
//                    }


// 修改学生的状态与更新时间
                    log.info("新建纪录");
                    studentsPO.setStustate(2);
                    studentsPO.setUpdatetime(new Date());
                    if(managementService.updateStuById(studentsPO)){
                        log.info("学生状态更新成功");
                    } else {
                        return ResultVO.failed("学生状态更新失败");
                    }

                    // 修改书籍的状态、被借次数与更新时间
                    booksPO.setState(2);
                    Integer count = booksPO.getCount();
                    booksPO.setCount(count+1);
                    booksPO.setUpdatetime(new Date());
                    if(managementService.updateBookById(booksPO)){
                        log.info("书籍状态更新成功");
                    } else {
                        return ResultVO.failed("书籍状态更新失败");
                    }

                    // 将借书信息插入借书表

                    // 创建借书对象
                    BorrowedPO borrowedPO = borConverterService.request2po(stuid, bookid);
                    // 插入
                    if(managementService.insertBorrowed(borrowedPO)) {
                        log.info("插入借书表成功");
                    } else {
                        return ResultVO.failed("插入借书表失败");
                    }


                }
                return ResultVO.success(0);
            }
        } catch (Exception e) {
            log.error("[user] failed execute queryDetail! input param = {}", stuid,bookid, e);
            return ResultVO.failed(e.getMessage());
        }
    }


    /**
     *
     * @param bookid
     * @return
     */
//    @ApiOperation("还书接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "bookId", value = "书ID"),
//            @ApiImplicitParam(name = "bookName", value = "书名")
//    }
//    )
    @RequestMapping(path = "/returnBook", method = RequestMethod.GET)
    public ResultVO<Integer> returnBook(@RequestParam("bookid") Long bookid) {
        try {

            if(bookid == null || bookid <= 0) {
                log.info("[user] invalid param! id = {}", bookid);
                return ResultVO.failed("非法的参数!");
            }

            // 更新借书表状态
            BorrowedPO borrowedPO = managementService.selectBorrowedByBookId(bookid);
            if(borrowedPO == null ) {

                log.info("[user] invalid param! bookid = {}", bookid);
                return ResultVO.failed("书籍不存在!");
            }

            borrowedPO.setValid(2);
            borrowedPO.setUpdatetime(new Date());
            managementService.updateBorrowedByBookId(borrowedPO);

            // 修改书籍状态
            BooksPO booksPO = managementService.queryBookById(bookid);
            booksPO.setState(1);
            booksPO.setUpdatetime(new Date());
            managementService.updateBookById(booksPO);

            // 修改学生状态
            Long stuid = borrowedPO.getStuid();
            StudentsPO studentsPO = managementService.queryStuById(stuid);
            studentsPO.setStustate(1);
            studentsPO.setUpdatetime(new Date());
            managementService.updateStuById(studentsPO);



            return ResultVO.success(0);

        } catch (Exception e) {
            log.error("[students] failed execute insertToBor! input param = {}", bookid, e);
            return ResultVO.failed(e.getMessage());
        }
    }
}
