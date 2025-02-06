package com.biotech.sankalpleaders.retrofit

interface IFailureHandler {

    fun failureResponse(requestCode: Int, message: String)

}