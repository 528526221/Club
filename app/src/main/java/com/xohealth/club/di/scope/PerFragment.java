package com.xohealth.club.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xulc on 2018/11/16.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface PerFragment {}
