package dev.sodiograaz.bot.commands.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* @author Sodiograaz
 @since 01/05/2024
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAnnotation
{
	
	String name();
	String description() default "Default Description";
	
}