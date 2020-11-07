package ru.school.matcha.services.interfaces;

import java.util.List;

public interface GuestService {

    void createGuest(Long userId, Long guestId);

    List<Long> getGuestsByUserId(Long userId);

    void deleteGuest(Long userId, Long guestId);

}
