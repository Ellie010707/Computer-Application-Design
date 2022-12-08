package com.ap.user.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/* 비밀번호 검증을 위한 annotation
 * 실시간 실행 - Retention(RUNTIME)
 * 설정 타겟 - Member 클래스 -> Type 
 * 비교할 속성 - first() , second() 
 * 나머지 코드는 보일러플레이트 코드(재사용코드)라고함 !
 * interface List > 2쌍 이상 체크 시 필요 
*/

@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {
	String message() default "{constraints.field-match}";
	
	String first();
	String second();
	
	Class < ? > [] groups() default {};
	Class < ? extends Payload > [] payload() default {};
	
	@Documented
	@Retention(RUNTIME)
	@Target({ TYPE, ANNOTATION_TYPE })
	@interface List{
		FieldMatch[] value();
	}
	
}
