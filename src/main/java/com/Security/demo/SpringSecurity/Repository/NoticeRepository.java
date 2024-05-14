package com.Security.demo.SpringSecurity.Repository;

import com.Security.demo.SpringSecurity.Entity.Notice;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {
    @Query(value = "from Notice n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
    List<Notice> findAllActiveNotices();

}
