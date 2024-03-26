package jp.co.axa.apidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;

/**
 * The interface which provides DAO for the Employee entity.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Cacheable("employees")
	Optional<Employee> findById(Long id);

	@Cacheable("employees")
	List<Employee> findAll();

	@Cacheable("employees")
	default Employee findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));
	}
}
