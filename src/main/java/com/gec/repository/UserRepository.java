package com.gec.repository;

import com.gec.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    // 自定义查询方法 Optional<User> 是Spring Data JPA用来处理可能不存在的结果的类型
    Optional<User> findByUsernameAndPassword(String username, String password);

    // 自定义查询方法，根据姓名查找用户
    List<User> findByUsername(String username);
}

