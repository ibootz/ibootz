package top.bootz.user.service.mysql;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author John 2018年6月11日 下午10:41:47
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DepartmentService {

}
