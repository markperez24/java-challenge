package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;
import java.util.Optional;

/**
 * The class which provides the Implementation of the EmployeeService Interface.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Sets the EmployeeRepository used by the service.
     * 
     * @param employeeRepository The EmployeeRepository implementation.
     */
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
    * The retrieveEmployees service method implementation. 
    *
    * @return The list of all employees. 
    */
    @Cacheable("employees")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    /**
     * The searchEmployees service method implementation.
     *
     * @return The list of all employees.
     */
    @Cacheable("employees")
    public List<Employee> searchEmployees(String search, String sortBy, String sortDirection) {
        // Filtering (Search) of the data
        Specification<Employee> specification = this.getSearchResults(search);
        // Sort data
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        List<Employee> employees;
        if (specification != null) {
            // Apply the specification if it is defined using JpaSpecificationExecutor
            employees = employeeRepository.findAll(specification, sort);
        } else {
            // Return all results if no filter is specified
            employees = employeeRepository.findAll();
        }
        return employees;
    }

    /**
     * The getEmployee service method implementation.
     * 
     * @param employeeId The ID of the employee to be retrieved.
     * @return The employee information with the specified ID.
     */
    @Cacheable("employee")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.get();
    }

    /**
     * The getEmployeeOrThrow service method implementation.
     * 
     * @param employeeId The ID of the employee to be retrieved.
     * @return The employee information with the specified ID.
     */
    @Cacheable("employee")
    public Employee getEmployeeOrThrow(Long employeeId) {
		Employee employee = employeeRepository.findByIdOrThrow(employeeId);
		return employee;
	}

    /**
     * The saveEmployee service method implementation.
     * 
     * @param employee The employee to be saved.
     */
    @CacheEvict(value = { "employees", "employee" }, allEntries = true)
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    /**
     * The deleteEmployee service method implementation.
     * 
     * @param employeeId The ID of the employee to be deleted.
     */
    @CacheEvict(value = { "employees", "employee" }, allEntries = true)
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    /**
     * The updateEmployee service method implementation.
     * 
     * @param employee The updated employee information.
     */
    @CacheEvict(value = { "employees", "employee" }, allEntries = true)
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    /**
	 * Get the Specification for the Employee to filter the
	 * results according to name and department.
	 * 
	 * @param search
	 * @return filtered data.
	 */
	private Specification<Employee> getSearchResults(String search) {
		Specification<Employee> specification = null;
		if (search != null && !search.trim().isEmpty()) {
			specification = (root, query, criteriaBuilder) -> {
				String pattern = "%" + search.toLowerCase() + "%";
				return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern),
						criteriaBuilder.like(criteriaBuilder.lower(root.get("department")), pattern));
			};
		}
		return specification;
	}
}