package top.bootz.user.repository.mysql.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import top.bootz.user.entity.mysql.department.Department;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department>,
        QuerydslPredicateExecutor<Department> {

}
