package com.muhaammaad.metarpolite.di.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @Qualifier is Dagger annotation which helps you to make custom annotation to distinguish method’s return type and to understand what they actually are.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseInfo {
}
