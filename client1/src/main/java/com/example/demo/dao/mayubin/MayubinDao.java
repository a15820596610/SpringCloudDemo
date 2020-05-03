package com.example.demo.dao.mayubin;

import com.example.demo.domain.Mayubin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MayubinDao {
    List<Mayubin> queryMayubin(Mayubin dto);

    void insertMayubin(Mayubin dto);

    void updateMayubin(Mayubin dto);
}
