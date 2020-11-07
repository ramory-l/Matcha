package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GuestMapper {

    void createGuest(@Param("userId") Long userId, @Param("guestId") Long guestId);

    List<Long> getGuestsByUserId(Long userId);

    void deleteGuest(@Param("userId") Long userId, @Param("guestId") Long guestId);

}
