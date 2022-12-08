package com.ap.consumer.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

/*비밀번호 검증 클래스*/

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	
	private String firstFiledName="";
	private String secondFieldName="";
	private String message="";
	
	/*비밀번호&확인 비밀번호를 받아와 initialize를 통해 각 first, second에 넣어줌
	 * message도 일단 받아온다...*/
	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFiledName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	/* FieldMatch 어노테이션이 사용된 클래스, 즉 Member 클래스가 Object로 지정된다.
	 * 이 클래스에서 입력값을 받아오려면 BeanWrapperImpl을 연결 해야한다.
	 * BeanWrapperImpl의 getPropertyValue를 이용해 각 속성의 실제 입력값을 받아온다.*/
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		
		final Object firstObject = new BeanWrapperImpl(value).getPropertyValue(firstFiledName);
		final Object secondObject = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
		
		valid = firstObject == null && secondObject == null 
				|| firstObject != null && firstObject.equals(secondObject);
		
		
		/*검증 결과가 false 인 경우, 즉 두 속성의 값이 같지 않은 경우 
		 * context 객체로 에러 메시지를 처리한다 ->
		 * context의 default 오류 문구를 제거하고
		 * annotation에서 지정한 message로 오류 구문 생성 후
		 * addPropertyNode로 message를 붙일 곳을 정한다*/
		if(!valid) {
			context.disableDefaultConstraintViolation();
			
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(secondFieldName)
					.addConstraintViolation();
		}
		
		return valid;
	}
	
}
