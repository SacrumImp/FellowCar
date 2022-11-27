package ru.fellowcar.users.api

import ru.fellowcar.users.api.ApiException
import javax.annotation.Generated

@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2022-11-27T13:27:49.806Z[GMT]")
class NotFoundException(private val code: Int, msg: String?) : ApiException(code, msg)
