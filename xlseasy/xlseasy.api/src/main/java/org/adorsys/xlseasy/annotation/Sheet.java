package org.adorsys.xlseasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adorsys.xlseasy.annotation.SheetFormatter.NoneSheetFormatter;

/**
 * The Interface Sheet.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {

    /**
     * Name.
     *
     * @return the string
     */
    String name() default "";

    /**
     * Column order.
     *
     * @return the string[]
     */
    String[] columnOrder() default {};

    /**
     * Freeze pane.
     *
     * @return the freeze pane
     */
    FreezePane freezePane() default @FreezePane;

    /**
     * Auto size columns.
     *
     * @return true, if successful
     */
    boolean autoSizeColumns() default false;

    /**
     * Formatter.
     *
     * @return the class<? extends sheet formatter>
     */
    Class<? extends SheetFormatter> formatter() default NoneSheetFormatter.class;

}
