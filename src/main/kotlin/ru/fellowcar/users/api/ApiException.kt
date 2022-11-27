package ru.fellowcar.users.api

import java.lang.Exception
import javax.annotation.Generated

@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2022-11-27T13:27:49.806Z[GMT]")
open class ApiException(private val code: Int, msg: String?) : Exception(msg)
