package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Guest;

import java.util.List;

public interface GuestService {

    void createGuest(Long userId, Long guestId);

    List<Guest> getGuestsByUserId(Long userId);

}
