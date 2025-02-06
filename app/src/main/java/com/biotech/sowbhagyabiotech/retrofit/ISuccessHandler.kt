package com.biotech.sankalpleaders.retrofit
interface ISuccessHandler<T> {

    fun successResponse(requestCode: Int, mResponse: T)

}