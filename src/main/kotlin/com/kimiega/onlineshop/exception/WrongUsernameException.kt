package com.kimiega.onlineshop.exception

import io.jsonwebtoken.JwtException

class WrongUsernameException(message: String) : JwtException(message)