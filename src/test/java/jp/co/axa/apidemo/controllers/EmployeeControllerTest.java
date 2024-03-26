package jp.co.axa.apidemo.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

/**
 * Unit Test Class for EmployeeController.
 */
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;
    private EmployeeController employeeController;
    
    private List<Employee> employees;

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController();
        employeeController.setEmployeeService(employeeService);

        // 1.Mock data
        employees = new ArrayList<>();
        employees.add(new Employee("Mark Perez", 10000000, "IT"));
        employees.add(new Employee("Joaquin Matteo", 5000000, "Sales"));
    }

    /**
     * Tests getEmployees() method of EmployeeController.
     */
    @Test
    public void testGetEmployees() {
        // 2.Mock the service method
        when(employeeService.retrieveEmployees()).thenReturn(employees);

        // 3.Invoke the controller method
        ResponseEntity<List<Employee>> response = employeeController.getEmployees();

        // 4.Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());

        // 5.Verify the service method call
        verify(employeeService, times(1)).retrieveEmployees();
    }

    /**
     * Tests getEmployee method (SUCCESS Case).
     */
    @Test
    public void testGetEmployee_ExistingEmployee() {
        // 1.Mock data
        Employee employee = new Employee("Mark Perez", 10000000, "IT");

        // 2.Mock the service method
        when(employeeService.getEmployee(1L)).thenReturn(employee);

        // 3.Invoke the controller method
        ResponseEntity<Employee> response = employeeController.getEmployee(1L);

        // 4.Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());

        // 5.Verify the service method call
        verify(employeeService, times(1)).getEmployee(1L);
    }

    /**
     * Tests getEmployee method (NOT FOUND Case).
     */
    @Test
    public void testGetEmployee_NonExistingEmployee() {
        // 1.Mock the service method
        when(employeeService.getEmployee(1L)).thenReturn(null);

        // 2.Invoke the controller method
        ResponseEntity<Employee> response = employeeController.getEmployee(1L);

        // 3.Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // 4.Verify the service method call
        verify(employeeService, times(1)).getEmployee(1L);
    }

    /**
     * Tests saveEmployee method (SUCCESS Case).
     */
    @Test
    public void saveEmployee_Successful() {
        // 1.Mock data
        Employee employee = new Employee("Mark Perez", 10000000, "IT");

        // 2.Mock the service method
        doNothing().when(employeeService).saveEmployee(employee);

        // 3.Invoke the controller method
        ResponseEntity<?> response = employeeController.saveEmployee(employee);

        // 4.Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 5.Verify the service method call
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    /**
     * Tests saveEmployee method (ERROR Case).
     */
    @Test
    public void saveEmployee_Exception() {
        // 1.Mock data
        Employee employee = new Employee("Mark Perez", 10000000, "IT");

        // 2.Mock the service method to throw an exception
        doThrow(new RuntimeException("Failed to save employee")).when(employeeService).saveEmployee(employee);

        // 3.Invoke the controller method
        ResponseEntity<?> response = employeeController.saveEmployee(employee);

        // 4.Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // 5.Verify the service method call
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    /**
     * Tests deleteEmployee method (SUCCESS Case).
     */
    @Test
    public void deleteEmployee_Successful() {
        // 1.Mock data
        Long employeeId = 1L;

        // 2.Mock the service method
        doNothing().when(employeeService).deleteEmployee(employeeId);

        // 3.Invoke the controller method
        ResponseEntity<?> response = employeeController.deleteEmployee(employeeId);

        // 4.Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 5.Verify the service method call
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    /**
     * Tests deleteEmployee method (ERROR Case).
     */
    @Test
    public void deleteEmployee_Exception() {
        // 1.Mock data
        Long employeeId = 1L;

        // 2.Mock the service method to throw an exception
        doThrow(new RuntimeException("Failed to delete employee")).when(employeeService).deleteEmployee(employeeId);

        // 3.Invoke the controller method
        ResponseEntity<?> response = employeeController.deleteEmployee(employeeId);

        // 4.Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // 5.Verify the service method call
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    /**
     * Tests updateEmployee method (SUCCESS Case).
     */
    @Test
    public void updateEmployee_Successful() {
        // 1.Mock data
        Long employeeId = 1L;
        Employee employee = new Employee("Mark Perez", 10000000, "IT");

        // 2.Mock the service method
        when(employeeService.getEmployee(employeeId)).thenReturn(employee);
        doNothing().when(employeeService).updateEmployee(employee);

        // 3.Invoke the controller method
        ResponseEntity<?> response = employeeController.updateEmployee(employee, employeeId);

        // 4.Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 5.Verify the service method calls
        verify(employeeService, times(1)).getEmployee(employeeId);
        verify(employeeService, times(1)).updateEmployee(employee);
    }

    /**
     * Tests updateEmployee method (NOT FOUND Case).
     */
    @Test
    public void updateEmployee_NotFound() {
        // 1.Mock data
        Long employeeId = 1L;
        Employee employee = new Employee("Mark Perez", 10000000, "IT");

        // 2.Mock the service method to return null
        when(employeeService.getEmployee(employeeId)).thenReturn(null);

        // 3.Invoke the controller method
        ResponseEntity<?> response = employeeController.updateEmployee(employee, employeeId);

        // 4.Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // 5.Verify the service method calls
        verify(employeeService, times(1)).getEmployee(employeeId);
        verify(employeeService, never()).updateEmployee(employee);
    }

    /**
     * Tests updateEmployee method (ERROR Case).
     */
    @Test
    public void updateEmployee_Exception() {
        // 1.Mock data
        Long employeeId = 1L;
        Employee employee = new Employee("Mark Perez", 10000000, "IT");

        // 2.Mock the service method to throw an exception
        when(employeeService.getEmployee(employeeId)).
            thenThrow(new RuntimeException("Failed to get employee"));

        // 3.Invoke the controller method
        ResponseEntity<?> response = employeeController.updateEmployee(employee, employeeId);

        // 4.Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // 5.Verify the service method calls
        verify(employeeService, times(1)).getEmployee(employeeId);
        verify(employeeService, never()).updateEmployee(employee);
    }

}