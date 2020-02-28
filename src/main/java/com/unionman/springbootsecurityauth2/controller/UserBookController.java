package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.UserBook;
import com.unionman.springbootsecurityauth2.service.UserBookService;
import com.unionman.springbootsecurityauth2.utils.UDPTest;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 通讯录管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/userbook/")
public class UserBookController {

    @Autowired
    private UserBookService userBookService;

    /**
     * @return
     * @description 获取指定通讯录信息
     */
    @GetMapping("checkId")
    public ResponseVO checkId(HttpServletRequest req) {
        String id = req.getParameter("id");
        return userBookService.findUserBookById(Integer.parseInt(id));
    }

    /**
     * @param req
     * @return
     * @description 删除
     */
    @GetMapping("deleteUserBook")
    public ResponseVO deleteUserBook(HttpServletRequest req) throws Exception {
        String id = req.getParameter("id");
        userBookService.deleteUserBookDetail(Integer.parseInt(id));
        userBookService.deleteUserBook(Integer.parseInt(id));
        return ResponseVO.success();
    }

    /**
     * @param userBook
     * @return
     * @descripiton 修改
     */
    @PostMapping("updateUserBook")
    public ResponseVO updateUserBook(@Valid @RequestBody UserBook userBook) {
        Map<String,Object> param = new HashMap<String,Object>(){{
           put("id",userBook.getId());
           put("name",userBook.getName());
        }};
        userBookService.updateUserBook(param);
        userBookService.deleteUserBookDetail(userBook.getId());
        List<Map<String,Object>> list = new ArrayList<>();
        for(String userId : userBook.getUserList()){
            Map<String,Object> map = new HashMap<String,Object>(){{
                put("id",userBook.getId());
                put("userId",userId);
            }};
            list.add(map);
        }
        param.put("list",list);
        userBookService.insertUserBookDetail(param);
        return ResponseVO.success();
    }

    /**
     * @param userBook
     * @return
     * @descripiton 添加
     */
    @PostMapping("addUserBook")
    public ResponseVO addUserBook(@Valid @RequestBody UserBook userBook) {
        Map<String,Object> params = new HashMap<String,Object>(){{
            put("id",userBook.getId());
            put("name",userBook.getName());
        }};
        userBookService.insertUserBook(params);
        Map<String,Object> param = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for(String userId : userBook.getUserList()){
            Map<String,Object> map = new HashMap<String,Object>(){{
                put("userId",userId);
                put("id",userBook.getId());
            }};
            list.add(map);
        }
        param.put("list",list);
        userBookService.insertUserBookDetail(param);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取列表
     */
    @GetMapping("userBookList")
    public ResponseVO findAllUserBook(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String name = req.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("name", "".equals(name) ? "" : name);
        }};
        return userBookService.selectUserBookList(map);
    }


}
