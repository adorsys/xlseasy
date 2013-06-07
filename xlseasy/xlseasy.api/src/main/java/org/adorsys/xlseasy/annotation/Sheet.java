package org.adorsys.xlseasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adorsys.xlseasy.annotation.SheetFormatter.NoneSheetFormatter;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {

    String name() default "";

    String[] columnOrder() default {};

    FreezePane freezePane() default @FreezePane;

    /**
     * I changed the default value from FALSE to TRUE.
     * 
     * @author Marius Guede
     * */
    boolean autoSizeColumns() default true;
    
    /**
     * Sets sheet's margin
     * 
     * @author Marius Guede
     * */
    boolean marged() default true;
    double margin() default 0.25;
    
    Class<? extends SheetFormatter> formatter() default NoneSheetFormatter.class;
}