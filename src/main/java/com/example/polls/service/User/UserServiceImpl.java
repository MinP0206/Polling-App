package com.example.polls.service.User;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Role;
import com.example.polls.model.User;
import com.example.polls.model.dto.UserDto;
import com.example.polls.payload.UserProfile;
import com.example.polls.payload.UserSummary;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.VoteRepository;
import com.example.polls.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;
    @Override
    public List<User> getAllUser(){
        userRepository.findAll();
        int check =0;
        List <User> notAdmin =  userRepository.findAll();
        for (int i=0;i<notAdmin.size();i++) {
            for (Role element : notAdmin.get(i).getRoles()) {
               if(element.getId()==2){
                    check =1;
               }
            }
            if(check ==1) {
                notAdmin.remove(i);
            }
        }

        return notAdmin;
    }

    @Override
    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @Override
    public UserProfile getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }
}
