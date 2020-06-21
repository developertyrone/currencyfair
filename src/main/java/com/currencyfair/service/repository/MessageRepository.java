package com.currencyfair.service.repository;


import com.currencyfair.service.dao.Message;
import com.currencyfair.service.dto.MessageCountryStatistics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByUserId(int UserId);
    @Query(nativeQuery = true, value =
            "SELECT " +
            "    v.ORIGINATING_COUNTRY AS country, COUNT(*) AS count " +
            "FROM " +
            "    MESSAGE v " +
            "GROUP BY " +
            "    v.ORIGINATING_COUNTRY")
    List<MessageCountryStatistics> getAllMessageCountByCountry();

}


