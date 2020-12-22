package org.kate.dctnumber.dao;

import org.kate.dctnumber.model.Employee;
import javax.ejb.Stateless;

@Stateless
public class EmployeeDAOImpl extends GenericDAOImpl<Employee, Long>
    implements EmployeeDAO {

    public EmployeeDAOImpl() {
        super(Employee.class);
    }
}
