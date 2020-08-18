package com.api.contracts.annotations;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", defaultValue = "0", paramType = "query", value = "Numero da pagina"),
        @ApiImplicitParam(name = "size", dataType = "integer", defaultValue = "20", paramType = "query", value = "Numero de elementos da pagina")})
public @interface ApiPageable {
}