package org.adorsys.xlseasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creates a split (freezepane).
 * <p/>
 * Michal Hajek, <a href="mailto:michalhajek@centrum.cz">michalhajek@centrum.cz</a>
 *
 * @since 22.5.11
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FreezePane {

    /**
     * Horizonatal position of split.
     */
    int colSplit() default 0;

    /**
     * Vertical position of split.
     */
    int rowSplit() default 1;

    /**
     * Top row visible in bottom pane
     */
    int leftmostColumn() default 0;

    /**
     * Left column visible in right pane.
     */
    int topRow() default 1;

}
