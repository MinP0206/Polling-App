package com.example.polls.service.User;

import com.example.polls.model.User;
import com.example.polls.model.dto.UserDto;
import com.example.polls.payload.UserProfile;
import com.example.polls.payload.UserSummary;
import com.example.polls.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    List<User> getAllUser();
    UserSummary getCurrentUser(UserPrincipal currentUser) ;
    UserProfile getUserProfile(String username);

}
