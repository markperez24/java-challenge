package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * The Controller which handles HTTP requests on Employee operations.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * Sets the EmployeeService used by EmployeeController.
     * 
     * @param employeeService The EmployeeService implementation.
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Retrieves the list of all employees.
     * 
     * @return The list of all employees in the repository.  
     */
    @GetMapping("/retrieveAll")
    public ResponseEntity<List<Employee>> getEmployees() {
        LOGGER.info("Retrieving a list of all employees.");
        try {            
            List<Employee> employees = employeeService.retrieveEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve employee list: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves the list of all employees based on search parameters.
     * 
     * @return The list of all employees in the repository.  
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> searchEmployees( 
            @RequestParam(required = false, defaultValue = "") String searchFilter
			, @RequestParam(required = false, defaultValue = "name") String sortBy
			, @RequestParam(required = false, defaultValue = "asc") String sortDirection
			) {
        LOGGER.info("Retrieving a list of all employees.");
        try {            
            List<Employee> employees = employeeService.searchEmployees(searchFilter, sortBy, sortDirection);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve employee list: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves the specified employee by its ID.
     * 
     * @param employeeId The ID of the employee to be retrieved from the repository.
     * @return The employee details with the specified ID.
     */
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(name="employeeId") Long employeeId) {
        LOGGER.info("Retrieving information of the specified employee.");
        try {
            Employee employee = employeeService.getEmployee(employeeId);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve employee with ID {}: {}", employeeId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Saves a new employee data.
     * 
     * @param employee The employee to be saved into the repository.
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){
        LOGGER.info("Creating a new employee information.");
        try {
            employeeService.saveEmployee(employee);
            LOGGER.info("New employee information has been saved successfully.");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Failed to save employee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deletes an employee by its ID.
     * 
     * @param employeeId The ID of the employee to be deleted from the repository.
     */
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name="employeeId") Long employeeId){
        LOGGER.info("Deleting information of the specified employee.");
        try {
            employeeService.deleteEmployee(employeeId);
            LOGGER.info("Employee with ID {} has been deleted successfully.", employeeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Failed to delete employee with ID {}: {}", employeeId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates information of an existing employee.
     * 
     * @param employee The updated employee information.
     * @param employeeId The ID of the employee to be updated from the repository.
     */
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId") Long employeeId){
        try {
            Employee emp = employeeService.getEmployee(employeeId);
            if(emp != null){
                employeeService.updateEmployee(employee);
                LOGGER.info("Employee with ID {} has been updated successfully.", employeeId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOGGER.error("Failed to update employee with ID {}: {}", employeeId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
