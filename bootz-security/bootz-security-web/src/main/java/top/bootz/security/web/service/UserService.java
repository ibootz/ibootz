package top.bootz.security.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.security.web.entity.User;
import top.bootz.security.web.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUserName(String userName) {
        return this.userRepository.findByUsername(userName);
    }

    public Optional<User> findByMobile(String mobile) {
        return this.userRepository.findByMobile(mobile);
    }

    @Transactional(readOnly = false)
    public void save(User entity) {
        this.userRepository.save(entity);
    }

    public Optional<User> find(Long id) {
        return this.userRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public void delete(User entity) {
        this.userRepository.delete(entity);
    }

    public long count() {
        return this.userRepository.count();
    }

}
