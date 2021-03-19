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

                    // 修改学生的状态与更新时间
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
     * 借书
     * @param id 学生id
//     * @param request 书籍id或名字
     * @return 0 成功;其它失败
     */
//    @ApiOperation("借书接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "stuId", value = "学号"),
//            @ApiImplicitParam(name = "bookId", value = "书ID"),
//            @ApiImplicitParam(name = "bookName", value = "书名")
//    }
//    )
//    @RequestMapping(path = "/borrowBooks ", method = RequestMethod.GET)
//    public ResultVO<StudentsPO> borrowBooks(@RequestParam("id") Long id
//                                         /*@RequestBody("bookid") SelectBookRequest request*/) {
//        try {
//            if (id == null || id < 0) {
//                log.info("[student] invalid param! id = {}", id);
//                return ResultVO.failed("非法的参数");
//            } /*else {*/
//                StudentsPO student = managementService.queryById(id);
//                if (student == null) {
//                    log.info("[student] studentsService.queryById empty! param! id = {}", id);
//                    return ResultVO.failed("未找到该用户");
//                }
//
//                /*// 借书状态
//                Integer stuid = student.getStustate();
//                if(stuid == 1) {
//                    return ResultVO.failed("只能借一本");
//                } else if (stuid == 2) {
//                    return ResultVO.failed("异常状态");
//                } else {
//                    log.info("可以借书");
//                }*/
//            /*}*/
//
//
////            if(request == null ||
////                    (StringUtils.isEmpty(request.getBookid()) && StringUtils.isEmpty(request.getBookname()))) {
////                log.info("[books] invalid param! request = {}", request);
////                return ResultVO.failed("非法的参数");
////            } else {
////                BooksPO record = booksConvertService.request2po(request);
////                BooksPO books = managementService.queryBookByIf(record);
////
////                // 书状态
////                Integer state = books.getState();
////                if (state == 2) {
////                    return ResultVO.failed("以被借阅");
////                } else if (state == 3) {
////                    return ResultVO.failed("已下线");
////                } else {
////                    log.info("借书成功");
////                }
////            }
//
//            // 此处为修改当前书籍状态
//
//            // 修改当前学生状态
//
//
//            // 向借书表插入信息
//
//
//
//            return ResultVO.success(student);
//
//
//        } catch (Exception e) {
//            log.error("[students] failed execute queryStuById! input param = {}", id, e);
//            return ResultVO.failed(e.getMessage());
//        }
//
//    }


    /**
     * 还书
     * @param request 书籍id或书名字
     * @return 0 成功， 其它失败
     */
//    @ApiOperation("还书接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "bookId", value = "书ID"),
//            @ApiImplicitParam(name = "bookName", value = "书名")
//    }
//    )
//    @RequestMapping(path = "/ReturnBook ", method = RequestMethod.GET)
//    public ResultVO<Integer> ReturnBook (@RequestBody SelectBookRequest request) {
//        try {
//            if(request == null ||
//                    (StringUtils.isEmpty(request.getBookid()) && StringUtils.isEmpty(request.getBookname()))) {
//                log.info("[books] invalid param! request = {}", request);
//                return ResultVO.failed("非法的参数");
//            }
//
//
//            BooksPO record = booksConvertService.request2po(request);
////            int res = managementService.insertToBor(record);
////            // 修改书状态
////            managementService.updateBooksById(record.getBookid());
////            // 修改学生状态
//
//            return ResultVO.success(0);
//
//        } catch (Exception e) {
//            log.error("[students] failed execute insertToBor! input param = {}", request, e);
//            return ResultVO.failed(e.getMessage());
//        }
//    }
}
