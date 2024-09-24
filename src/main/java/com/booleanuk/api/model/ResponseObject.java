package com.booleanuk.api.model;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Data
public class ResponseObject <T>{
	public String status;
	public T data;

	public ResponseObject(String status){
		this.status = status;
		this.data = null;
	}
}
