package top.bootz.security.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.security.web.entity.Authority;
import top.bootz.security.web.repository.AuthorityRepository;

@Service
@Transactional(readOnly = true)
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Transactional(readOnly = false)
    public void save(Authority entity) {
        this.authorityRepository.save(entity);
    }

    public Optional<Authority> find(Long id) {
        return this.authorityRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public void delete(Authority entity) {
        this.authorityRepository.delete(entity);
    }

    public long count() {
        return this.authorityRepository.count();
    }

    public Authority findByName(String authName) {
        return authorityRepository.findByName(authName);
    }

}
