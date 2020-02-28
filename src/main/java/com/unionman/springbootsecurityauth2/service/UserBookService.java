package com.unionman.springbootsecurityauth2.service;


import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.List;
import java.util.Map;

public interface UserBookService {
    ResponseVO<Map<String,Object>> findUserBookById(int id);

    ResponseVO<Map<String, Object>> selectUserBookList(Map<String, Object> param);

    void insertUserBook(Map<String,Object> param);

    void updateUserBook(Map<String,Object> param);

    void deleteUserBook(int id);

    ResponseVO<Map<String, Object>> selectUserBookDetail(int id);

    void deleteUserBookDetail(int id);

    void insertUserBookDetail(Map<String, Object> param);
}
