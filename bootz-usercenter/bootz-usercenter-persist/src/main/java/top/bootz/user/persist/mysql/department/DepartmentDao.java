package top.bootz.user.persist.mysql.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.bootz.user.entity.mysql.department.Department;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface DepartmentDao extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {

}
