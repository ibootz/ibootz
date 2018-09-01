package top.bootz.security.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.security.web.entity.Role;
import top.bootz.security.web.repository.RoleRepository;

@Service
@Transactional(readOnly = true)
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = false)
    public void save(Role entity) {
        this.roleRepository.save(entity);
    }

    public Optional<Role> find(Long id) {
        return this.roleRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public void delete(Role entity) {
        this.roleRepository.delete(entity);
    }

    public long count() {
        return this.roleRepository.count();
    }

    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

}
