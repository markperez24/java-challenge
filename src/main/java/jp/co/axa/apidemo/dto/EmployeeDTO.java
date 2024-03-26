package jp.co.axa.apidemo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jp.co.axa.apidemo.annotations.EmployeeFields;

@Data
@EmployeeFields(message="Either one of the fields (name, department or salary) must be provided and must have a value")
public class EmployeeDTO {

	@Getter
    @Setter	
	private String name;
    
	@Getter
    @Setter
	private String department;
    
	@Getter
    @Setter
	private Integer salary;
}
